package dao;

import model.*;
import pojo.UserAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MySqlDao implements DAO {

    private static MySqlDao instance;
    private String sqlDriver = "com.mysql.cj.jdbc.Driver";
    private String sqlServer     = "127.0.0.1";
    private String sqlServerPort = "3306";
    private String sqlDatabase   = "lerntool";
    private Connection sqlConnection = null;

    private MySqlDao() {
        UserAccess access = new UserAccess();
        checkDriver();
        openConnection(access.getAuthUser(), access.getAuthPwd());
    }

    public void changeUserLogin(String username, String pwd) {
        openConnection(username, pwd);
    }

    private void checkDriver() {
        try {
            Class.forName(sqlDriver);
        } catch (ClassNotFoundException e) {
            System.err.println(sqlDriver + " Treiber konnte nicht gefunden werden");
            System.exit(-1);
        }
    }

    private void closeConnection() {
        try {
            if (sqlConnection != null) {
                sqlConnection.close();
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Schließen der Verbindung");
            e.printStackTrace();
        }

    }

    private void commitStatement(PreparedStatement statement) throws SQLException {
        statement.close();
        sqlConnection.commit();

    }

    private void commitStatement(Statement statement) throws SQLException {
        statement.close();
        sqlConnection.commit();
    }

    private ResultSet executeQuery(PreparedStatement statement) {

        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    private boolean frageExists(Frage frage) {

        String sqlQuery = "SELECT * FROM fragen WHERE frage_id=?";

        try {
            PreparedStatement statement = sqlConnection.prepareStatement(sqlQuery);
            statement.setInt(1, frage.getFrageID());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Fach> getAlleFächer() {

        List<Fach> fachList = new ArrayList<>();
        String sqlQuery = """
                SELECT
                    fa.fach_id,
                    fa.anzeigename AS fach,
                    GROUP_CONCAT(th.thema_id SEPARATOR '&&') AS themaNr
                FROM
                    faecher fa
                        LEFT JOIN
                    themen th ON th.fach_id = fa.fach_id
                GROUP BY fach;
                """;

        try {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Fach fach = loadFach(resultSet);
                    fachList.add(fach);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fachList;
    }

    @Override
    public List<Fach> getAlleFächerMitZugewiesenenThemen() {

        List<Fach> fachList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM faecher";

        try {
            Statement statement = sqlConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {

                    int fachNr = resultSet.getInt("fach_id");
                    String anzeigeName = resultSet.getString("anzeigename");
                    Fach fach = new Fach(fachNr, anzeigeName);

                    HashMap<Integer, String> zugewieseneThemenNr = new HashMap<>();
                    zugewieseneThemenNr = getZugewieseneThemenNrFromFach(fachNr);
                    fach.setZugewieseneThemen(zugewieseneThemenNr);
                    fachList.add(fach);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fachList;
    }

    @Override
    public List<Gruppe> getAlleGruppenMitZugewiesenenThemen() {

        List<Gruppe> gruppenList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM gruppe";

        try {
            Statement statement = sqlConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {

                    String gruppeNr = resultSet.getString("gruppeNr");
                    if (!gruppeNr.equals("admin")) {

                        String ausbildungName = resultSet.getString("ausbildungName");
                        Gruppe gruppe = new Gruppe(gruppeNr, ausbildungName);

                        // zugewiesene Themen
                        HashMap<Integer, String> zugewieseneThemenNr = new HashMap<>();
                        zugewieseneThemenNr = getZugewieseneThemenNrFromGruppe(gruppeNr);
                        gruppe.setZugewieseneThemen(zugewieseneThemenNr);
                        gruppenList.add(gruppe);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gruppenList;
    }

    @Override
    public String getAnzeigenName(String username) {

        String sqlString = "SELECT anzeigename FROM benutzer WHERE uid=?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlString);
            preparedStatement.setString(1, username);

            resultSet = executeQuery(preparedStatement);

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                return resultSet.getString("anzeigename");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Fach> getBenutzerFächer(String benutzername) {

        List<Fach> fachList = new ArrayList<>();
        ResultSet resultSet;
        String sqlQuery = """
                SELECT
                    fa.fach_id,
                    fa.anzeigename AS fach,
                    GROUP_CONCAT(th.thema_id SEPARATOR '&&') AS themaNr
                FROM
                    faecher fa
                        JOIN
                    themen th ON th.fach_id = fa.fach_id
                        JOIN
                    benutzer_themen bt ON th.thema_id = bt.benutzerthemen_thema_id
                        LEFT JOIN
                    benutzer b ON b.gruppe = bt.benutzerthemen_gruppe
                WHERE
                    b.uid = ?
                GROUP BY fach""";
        try {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, benutzername);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Fach fach = loadFach(resultSet);
                    fachList.add(fach);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fachList;

    }

    @Override
    public String getHashedPasswordFromUser(String username) {

        String sqlString = "SELECT passwort FROM benutzer WHERE uid=?";
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlString);
            preparedStatement.setString(1, username);

            resultSet = executeQuery(preparedStatement);

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                return resultSet.getString("passwort");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getInsertIntoBenutzerThemenQuery() {
        String sqlQuery = "INSERT INTO  benutzer_themen VALUES (?,?)";
        return sqlQuery;
    }

    private String getInsertNewAntwortQuery() {

        return "INSERT INTO antworten (antwortText,fragen_id, istRichtig) VALUES (?,?,?)";
    }

    private String getInsertNewFrageQuery() {

        return """
                INSERT INTO
                    fragen (fragenText, fragenTyp, thema_id)
                VALUES (?,?,?)""";
    }

    public static MySqlDao getInstance() {

        if (instance == null) {
            instance = new MySqlDao();
        }

        return instance;
    }

    private String getLastInsertIDQuery() {

        return "SELECT LAST_INSERT_ID() AS ID";

    }

    private String getLoadThemaStatistikQuery() {
        return """
                SELECT
                    bf.frage_id, bf.abgefragt, bf.richtig, bf.falsch
                FROM
                    benutzer_fragen bf
                        JOIN
                    fragen f ON bf.frage_id = f.frage_id
                        JOIN
                    themen t ON t.thema_id = f.thema_id
                WHERE
                    bf.benutzer_uid = ?
                        AND t.thema_id = ?;
                        """;
    }

    private int getNextFrageNr() throws SQLException {

        String sqlQuery = """
                            SELECT MAX(frage_id) FROM fragen
                            """;

        Savepoint savepoint = null;
        PreparedStatement statement = null;

        statement = sqlConnection.prepareStatement(sqlQuery);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1) + 1;
        }

        return 0;

    }

    @Override
    public int getNextThemaNr() {

        Statement statement = null;

        String sqlQuery = """
                SELECT
                    MAX(thema_id)
                FROM
                    themen;
        """;

        try {
            statement = sqlConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    private String getRemoveAntwortenQuery() {

        return """
                DELETE FROM antworten WHERE fragen_id=?
                """;
    }

    private String getRemoveFrageQuery() {

        return """
                DELETE FROM fragen WHERE frage_id=?
                """;
    }

    private String getSaveAntwortQuery() {

        return """
                INSERT INTO antworten (antwort_id, antwortText, fragen_id, istRichtig) VALUES (?, ?, ?, ?)
                    ON DUPLICATE KEY UPDATE antwortText=?, istRichtig=?;
                    """;
    }

    private String getSaveFrageQuery() {

        return """
                INSERT INTO fragen (frage_id, fragenText, fragenTyp, thema_id) VALUES (?, ?, ?, ?)
                    ON DUPLICATE KEY UPDATE fragenText=?, fragenTyp=?;
                    """;
    }

    private String getSaveThemaQuery() {
        return """
                INSERT INTO
                    themen (thema_id, anzeigename, fach_id) VALUES (?,?,?)
                ON DUPLICATE KEY UPDATE anzeigename=?;
                """;
    }

    private String getThemaName(Integer thema_id) throws SQLException {

        String themaName = null;

        String sqlQuery = "SELECT " +
                          "     anzeigename " +
                          "FROM " +
                          "     themen t " +
                          "WHERE t.thema_id=?";
        ResultSet resultSet;

            PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, thema_id);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            themaName = resultSet.getString("anzeigename");

        return themaName;
    }

    private HashMap<Integer, String> getZugewieseneThemenNrFromFach(int fachNr) {

        HashMap<Integer, String> zugewieseneThemenNr = null;
        String sqlQuery = """
                SELECT
                    th.thema_id, th.anzeigename
                FROM
                    themen th
                        JOIN
                    faecher f ON f.fach_id = th.fach_id
                WHERE f.fach_id=?
                            """;

        try {
            PreparedStatement statement = sqlConnection.prepareStatement(sqlQuery);
            statement.setInt(1, fachNr);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                zugewieseneThemenNr = new HashMap<>();

                while (resultSet.next()) {
                    Integer themaNr = resultSet.getInt("thema_id");
                    String anzeigename = resultSet.getString("anzeigename");

                    zugewieseneThemenNr.put(themaNr, anzeigename);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zugewieseneThemenNr;
    }

    private HashMap<Integer, String> getZugewieseneThemenNrFromGruppe(String gruppenNr) {

        HashMap<Integer, String> zugewieseneThemenNr = null;
        String sqlQuery = """
                SELECT
                    th.thema_id, th.anzeigename
                FROM
                    themen th
                        JOIN
                    benutzer_themen bt ON bt.benutzerthemen_thema_id = th.thema_id
                WHERE
                    bt.benutzerthemen_gruppe = ?;
                        """;

        try {
            PreparedStatement statement = sqlConnection.prepareStatement(sqlQuery);
            statement.setString(1, gruppenNr);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {

                zugewieseneThemenNr = new HashMap<>();

                while (resultSet.next()) {
                    Integer themaNr = resultSet.getInt("thema_id");
                    String anzeigeName = resultSet.getString("anzeigename");

                    zugewieseneThemenNr.put(themaNr, anzeigeName);

                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zugewieseneThemenNr;
    }

    private void insertAntwort(Antwort antwort, int fragenID, PreparedStatement statement) throws SQLException {

        String antwortText = antwort.getAntwortText();
        boolean istRichtig = antwort.istWahr();

        statement.setString(1, antwortText);
        statement.setInt(2, fragenID);
        statement.setBoolean(3, istRichtig);
        statement.executeUpdate();

        // GIB NEUE ANTWORT ID ZURÜCK
        statement = sqlConnection.prepareStatement(getLastInsertIDQuery());
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.isBeforeFirst()) {
            resultSet.next();
            int antwortID = resultSet.getInt("ID");
            antwort.setAntwortID(antwortID);
        } else {
            System.err.println("FEHLER");
        }

    }

    private void insertIntoBenutzerThemen(String gruppeNr, Integer themaNr, PreparedStatement statement) throws SQLException {

        statement.setString(1, gruppeNr);
        statement.setInt(2, themaNr);
        statement.executeUpdate();
        commitStatement(statement);

    }

    @Override
    public void insertNewFach(String fachName) {

        // TODO: Testen !

        String sqlQuery = "INSERT INTO faecher (anzeigename) VALUES (?)";
        Savepoint savepoint = null;

        try {
            savepoint = sqlConnection.setSavepoint();
            PreparedStatement statement = sqlConnection.prepareStatement(sqlQuery);
            statement.setString(1, fachName);
            statement.executeUpdate();
            commitStatement(statement);

        } catch (SQLException e) {
            rollbackQuery(savepoint);
            e.printStackTrace();
        }
    }

    private void insertNewFrage(Frage frage, PreparedStatement statement) {

        String fragenText = frage.getFragenText();
        String fragenTyp = frage.getFragenTyp().toString();
        int fragenID = 0;

        int themaID = frage.getThemaID();

        try {
            // INSERT FRAGE
            statement.setString(1, fragenText);
            statement.setString(2, fragenTyp);
            statement.setInt(3, themaID);
            statement.executeUpdate();

            // GIBT LETZTE ID DES INSERT QUERY ZURÜCK
            statement = sqlConnection.prepareStatement(getLastInsertIDQuery());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();

                fragenID = resultSet.getInt("ID");
                frage.setFrageID(fragenID);
                List<Antwort> antwortList = frage.getAntwortmöglichkeiten();

                for (Antwort antwort : antwortList) {

                    // EINE NEUE ANTWORT KANN NOCH KEINE DB-ID HABEN
                    if (antwort.getAntwortID() == 0) {
                        statement = sqlConnection.prepareStatement(getInsertNewAntwortQuery());
                        insertAntwort(antwort, fragenID, statement);
                    } else {
                        statement = sqlConnection.prepareStatement(getSaveAntwortQuery());
                        saveAntwort(antwort, fragenID, statement);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }




    }

    public void insertThema(Thema thema, PreparedStatement statement) {

        try {
            statement.setInt(1, thema.getThemaNr());
            statement.setString(2, thema.getAnzeigeName());
            statement.setInt(3, thema.getFachNr());
            statement.setString(4, thema.getAnzeigeName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean istAdmin(String username) {

        String sqlQuery = "SELECT type FROM benutzer WHERE uid=?";

        try {
            PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                if (resultSet.getString("type").equals("admin")) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private List<Antwort> loadAntwortenVonFrage(int frageNr) throws SQLException {

        List<Antwort> antworten = new ArrayList<>();
        ResultSet resultSet;

        String sqlQuery = """
                SELECT
                	a.antwort_id, a.antwortText, a.istRichtig
                FROM
                	antworten a
                WHERE
                	a.fragen_id=?;
                           """;

        PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, frageNr);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String antwortText = resultSet.getString("antwortText");
            boolean istRichtig = resultSet.getBoolean("istRichtig");
            int antwortID = resultSet.getInt("antwort_id");
            Antwort antwort = new Antwort(antwortID,antwortText, istRichtig);

            antworten.add(antwort);
        }
        return antworten;
    }

    private Fach loadFach(ResultSet resultSet) throws SQLException {

        Fach fach;
        List<String> themenListe;

        int fachNr = resultSet.getInt("fach_id");
        String fachName = resultSet.getString("fach");

        fach = new Fach(fachNr, fachName);

        // Wenn Thema vorhanden
        if (resultSet.getString("themaNr") != null) {
            // Nummern der Themen die dem Fach zugeordnet sind.
            themenListe = Arrays.asList(resultSet.getString("themaNr").split("&{2}"));
            for (String thema_id: themenListe) {
                Thema thema = loadThema(fachNr, Integer.parseInt(thema_id));
                if (thema != null) {
                    fach.addThema(thema);
                }
            }
        }
        return fach;
    }

    private Frage loadFrage(int frage_id) throws SQLException {

        Frage frage = null;
        String sqlQuery = """
                SELECT
                    f.frage_id, f.fragenText, f.fragenTyp, f.thema_id
                FROM
                    fragen f
                WHERE f.frage_id=?;
                           """;
        ResultSet resultSet;

        PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, frage_id);

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            int frageNr = resultSet.getInt("frage_id");
            String fragenText = resultSet.getString("fragenText");
            String fragenTyp = resultSet.getString("fragenTyp");
            int thema_id = resultSet.getInt("thema_id");

            switch (fragenTyp) {
                case "SingleChoice" -> {
                    frage = new SingleChoiceFrage(frageNr, fragenText, thema_id);
                }
                case "MultipleChoiceCheck" -> {
                    frage = new MultipleChoiceCheckFrage(frageNr, fragenText, thema_id);
                }
                case "MultipleChoiceSelect" -> {
                    frage = new MultipleChoiceSelectFrage(frageNr, fragenText, thema_id);
                }
                case "TextEingabe" -> {
                    frage = new TextEingabeFrage(frageNr, fragenText, thema_id);
                }
                default -> throw new IllegalStateException("Unexpected value: " + fragenTyp);
            }

            List<Antwort> antworten = loadAntwortenVonFrage(frageNr);
            frage.setAntwortmöglichkeiten(antworten);
        }

        return frage;
    }

    private Thema loadThema(int fachNr, int thema_id) {

        Thema thema = null;
        String sqlQuery = "SELECT " +
                          "     * " +
                          "FROM " +
                          "     fragen f " +
                          "WHERE f.thema_id=?";
        ResultSet resultSet;

        try {
            String themenName = getThemaName(thema_id);
            thema = new Thema(fachNr, themenName, thema_id);

            PreparedStatement preparedStatement = sqlConnection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, thema_id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Frage frage;
                int frage_id = resultSet.getInt("frage_id");
                frage = loadFrage(frage_id);
                thema.addFrage(frage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thema;
    }

    @Override
    public Thema loadThemaStatistikFromUser(String username, Thema thema) {

        String sqlQuery = getLoadThemaStatistikQuery();
        int thema_id = thema.getThemaNr();

        try {
            PreparedStatement statement = sqlConnection.prepareStatement(sqlQuery);
            statement.setString(1, username);
            statement.setInt(2, thema_id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    int frage_id = resultSet.getInt("frage_id");

                    for (Frage frage : thema.getFrageList()) {

                        if (frage.getFrageID() == frage_id) {

                            int anzahlAbgefragt = resultSet.getInt("abgefragt");
                            int anzahlDavonRichtig = resultSet.getInt("richtig");
                            int anzahlDavonFalsch = resultSet.getInt("falsch");

                            frage.setAbgefragt(anzahlAbgefragt);
                            frage.setDavonRichtig(anzahlDavonRichtig);
                            frage.setDavonFalsch(anzahlDavonFalsch);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return thema;
    }

    private void openConnection(String username, String pwd) {

        try {
            closeConnection();
            sqlConnection = DriverManager.getConnection(
                    "jdbc:mysql://" + sqlServer + ":" + sqlServerPort + "/" + sqlDatabase, username, pwd
            );
            sqlConnection.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Problem beim Aufbau der Datenbankverbindung");
            e.printStackTrace();
        }
    }

    private void removeAllAntwortenFromFragen(int frageNr) throws SQLException {

        PreparedStatement statement = sqlConnection.prepareStatement(getRemoveAntwortenQuery());
        statement.setInt(1, frageNr);
        statement.executeUpdate();

    }

    @Override
    public void removeAntwort(int antwort_id) {

        String sqlQuery = "DELETE FROM antworten WHERE antwort_id=?";
        PreparedStatement statement;
        Savepoint savepoint = null;

        try {
            savepoint = sqlConnection.setSavepoint();
            statement = sqlConnection.prepareStatement(sqlQuery);
            statement.setInt(1, antwort_id);
            statement.executeUpdate();
            commitStatement(statement);

        } catch (SQLException e) {
            rollbackQuery(savepoint);
            e.printStackTrace();
        }
    }

    private void removeBenutzerStatistikFromFrage(int fragenNr, PreparedStatement statement) throws SQLException {

        String sqlQuery = """
                DELETE FROM
                    benutzer_fragen
                WHERE
                    frage_id=?
                """;

        statement = sqlConnection.prepareStatement(sqlQuery);
        statement.setInt(1, fragenNr);

    }

    @Override
    public void removeFrage(int fragenNr) {

        Savepoint savepoint = null;
        PreparedStatement statement = null;

        try {
            savepoint = sqlConnection.setSavepoint();
            // Antworten der Frage löschen
            removeAllAntwortenFromFragen(fragenNr);

            statement = sqlConnection.prepareStatement(getRemoveFrageQuery());
            statement.setInt(1, fragenNr);
            statement.executeUpdate();

            // Benutzerstatistik der Frage löschen
            removeBenutzerStatistikFromFrage(fragenNr, statement);
            commitStatement(statement);

        } catch (SQLException e) {
            rollbackQuery(savepoint);
            e.printStackTrace();
        }

    }

    @Override
    public void removeThema(Thema thema) {

        String sqlQuery = "DELETE FROM themen WHERE thema_id=?";
        PreparedStatement statement;
        Savepoint savepoint = null;
        int themaNr = thema.getThemaNr();

        try {
            savepoint = sqlConnection.setSavepoint();

            for (Frage frage : thema.getFrageList()) {
                removeFrage(frage.getFrageID());
            }
            statement = sqlConnection.prepareStatement(sqlQuery);
            statement.setInt(1, themaNr);

            commitStatement(statement);
        } catch (SQLException e) {
            rollbackQuery(savepoint);
            e.printStackTrace();
        }

    }

    private void rollbackQuery(Savepoint savepoint) {

        try {
            sqlConnection.rollback(savepoint);
            System.err.println("Rollback durchgeführt");
        } catch (SQLException e) {
            System.err.println("Fehler beim Rollback");
            e.printStackTrace();
        }
    }

    private void saveAntwort(Antwort antwort, int fragenID, PreparedStatement statement) throws SQLException {

        boolean istRichtig = antwort.istWahr();
        String antwortText = antwort.getAntwortText();

        if (antwort.getAntwortID() == 0) {
            statement.setNull(1, 0);
        } else {
            statement.setInt(1, antwort.getAntwortID());
        }

        statement.setString(2, antwortText);
        statement.setInt(3, fragenID);
        statement.setBoolean(4, istRichtig);
        statement.setString(5, antwortText);
        statement.setBoolean(6, istRichtig);
        statement.executeUpdate();

    }

    private void saveFrage(Frage frage, PreparedStatement statement) throws SQLException {

        int fragenID = frage.getFrageID();
        String fragenTyp = frage.getFragenTyp().toString();
        String fragenText = frage.getFragenText();

        statement.setInt(1, fragenID);
        statement.setString(2, fragenText);
        statement.setString(3, fragenTyp);
        statement.setInt(4, frage.getThemaID());
        statement.setString(5, fragenText);
        statement.setString(6, fragenTyp);
        statement.executeUpdate();

        // Antworten entfernen wenn vorhanden
        removeAllAntwortenFromFragen(fragenID);

        List<Antwort> antwortList = frage.getAntwortmöglichkeiten();

        for (Antwort antwort: antwortList) {
            statement = sqlConnection.prepareStatement(getSaveAntwortQuery());
            saveAntwort(antwort,fragenID, statement);
        }
    }

    @Override
    public void saveThema(Thema thema) {

        Savepoint savepoint = null;
        PreparedStatement statement = null;
        try {
            savepoint = sqlConnection.setSavepoint();
            statement = sqlConnection.prepareStatement(getSaveThemaQuery());

            insertThema(thema, statement);
            List<Frage> frageList = thema.getFrageList();

            for (Frage frage : frageList) {

                // eine neue Frage kann noch keine DB-ID zugewiesen bekommen haben.
                if (frage.getFrageID() == 0) {
                    statement = sqlConnection.prepareStatement(getInsertNewFrageQuery());
                    insertNewFrage(frage, statement);
                } else {
                    statement = sqlConnection.prepareStatement(getSaveFrageQuery());
                    saveFrage(frage, statement);
                }
            }
            commitStatement(statement);

        } catch (SQLException e) {
            e.printStackTrace();
            if (savepoint != null) {
                rollbackQuery(savepoint);
            }
        }

    }

    @Override
    public boolean themaExists(int themaNr) {

        String sqlQuery = "SELECT * FROM themen WHERE thema_id=?";

        try {
            PreparedStatement statement = sqlConnection.prepareStatement(sqlQuery);
            statement.setInt(1, themaNr);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean truncateBenutzerThemen() {

        String sqlQuery = "TRUNCATE TABLE benutzer_themen";

        try {
            PreparedStatement statement = sqlConnection.prepareStatement(sqlQuery);
            statement.executeUpdate();
            commitStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void updateAntwort(Antwort antwort) {

        // TODO: Iterate in DAO Methode mit Batch !
        String sqlQuery = getSaveAntwortQuery();

        try {

            int antwort_id = antwort.getAntwortID();
            String antwortText = antwort.getAntwortText();
            
            PreparedStatement statement = sqlConnection.prepareStatement(sqlQuery);
            statement.executeUpdate();
            commitStatement(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBenutzerThemen(List<Gruppe> gruppeList) {

        Savepoint savepoint = null;
        PreparedStatement statement = null;

        try {
            savepoint = sqlConnection.setSavepoint();

            if (truncateBenutzerThemen()) {
                for (Gruppe gruppe : gruppeList) {
                    String gruppeNr = gruppe.getGruppenNr();
                    HashMap<Integer, String> zugewieseneThemen = gruppe.getZugewieseneThemen();

                    if (zugewieseneThemen != null) {
                        for (Integer themaNr : zugewieseneThemen.keySet()) {
                            statement = sqlConnection.prepareStatement(getInsertIntoBenutzerThemenQuery());
                            insertIntoBenutzerThemen(gruppeNr, themaNr, statement);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            rollbackQuery(savepoint);
            e.printStackTrace();
        }
    }

    public void updateFrage(Frage frage) {

        Savepoint savepoint = null;
        PreparedStatement statement;

        try {

            savepoint = sqlConnection.setSavepoint();

            if (frageExists(frage)) {
                statement = sqlConnection.prepareStatement(getSaveFrageQuery());
                saveFrage(frage, statement);
            } else {
                statement = sqlConnection.prepareStatement(getInsertNewFrageQuery());
                insertNewFrage(frage, statement);
            }
            commitStatement(statement);

        } catch (SQLException e) {
            rollbackQuery(savepoint);
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatistik(String username, int frageID, boolean antwortRichtig) {

        Savepoint savepoint = null;
        // Einfügen, wenn noch nicht vorhanden.
        String sqlQuery = "INSERT IGNORE INTO benutzer_fragen (benutzer_uid, frage_id) VALUES (?,?)";

        try {
            savepoint = sqlConnection.setSavepoint();

            PreparedStatement statement = sqlConnection.prepareStatement(sqlQuery);
            statement.setString(1, username);
            statement.setInt(2, frageID);
            statement.executeUpdate();
            commitStatement(statement);

            updateUserStatistik(username, frageID, antwortRichtig);

        } catch (SQLException e) {
            rollbackQuery(savepoint);
            e.printStackTrace();
        }
    }

    public void updateThemaName(Thema thema) {

        String sqlQuery = "UPDATE themen SET anzeigename=? WHERE thema_id=?";

        PreparedStatement statement;
        Savepoint savepoint = null;

        try {
            savepoint = sqlConnection.setSavepoint();
            String anzeigename = thema.getAnzeigeName();
            int themaNr = thema.getThemaNr();

            statement = sqlConnection.prepareStatement(sqlQuery);
            statement.setString(1, anzeigename);
            statement.setInt(2, themaNr);
            statement.executeUpdate();
            commitStatement(statement);

        } catch (SQLException e) {
            rollbackQuery(savepoint);
            e.printStackTrace();
        }


    }

    private void updateUserStatistik(String username, int frageID, boolean antwortRichtig) throws SQLException {

        PreparedStatement statement = null;
        String sqlQuery;

        // Statistik updaten
        sqlQuery = """
                      UPDATE benutzer_fragen
                      SET
                        abgefragt=abgefragt+1
                        """ +
                   (antwortRichtig ? ",richtig=richtig+1" : ",falsch=falsch+1") +
                   """
                      WHERE
                        benutzer_uid=? AND
                        frage_id=?;
                   """;

        statement = sqlConnection.prepareStatement(sqlQuery);
        statement.setString(1, username);
        statement.setInt(2, frageID);
        statement.executeUpdate();
        commitStatement(statement);
    }
}
