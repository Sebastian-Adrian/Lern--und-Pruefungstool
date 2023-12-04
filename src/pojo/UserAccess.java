package pojo;

public class UserAccess implements Access {

    private final String key = "g=EfP+u%4fsAn53ADgQ6";
    private final String salt = "+r5q%uEjjMY0hKDwCV8#";
    private final String dbDozentUser = "dozent";
    private final String dbDozentPwd = "MDq0?qA#xq7$TGapAv6W";
    private final String dbUser = "dbUser";
    private final String dbPasswd = "$TNpUrrf9t5@A19ZBFuV";
    private final String authUser = "auth";
    private final String authPwd = "30<_H+CBaiPXk(5[eh7s";

    public String getKey() {
        return key;
    }

    public String getSalt() {
        return salt;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPasswd() {
        return dbPasswd;
    }

    public String getAuthUser() {
        return authUser;
    }

    public String getDbDozentUser() {
        return dbDozentUser;
    }

    public String getDbDozentPwd() {
        return dbDozentPwd;
    }

    public String getAuthPwd() {
        return authPwd;
    }
}
