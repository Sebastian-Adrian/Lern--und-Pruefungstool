use lerntool;

UPDATE benutzer 
SET 
    passwort = '$2a$10$NSokH8WUke86qSUXiX8Hee5.proUFeJenMJopzTa6bCzCT.znDO/G'
WHERE
    uid = 'basti';

-- getAlleFächer von Benutzer
SELECT 
    fa.fach_id,
    fa.anzeigename AS fach,
    GROUP_CONCAT(th.thema_id
        SEPARATOR '&&') AS thema
FROM
    faecher fa
        JOIN
    themen th ON th.fach_id = fa.fach_id
        JOIN
    benutzer_themen bt ON th.thema_id = bt.benutzerthemen_thema_id
        LEFT JOIN
    benutzer b ON b.gruppe = bt.benutzerthemen_gruppe
WHERE
    b.uid = 'basti'
GROUP BY fach;

-- get AlleFächer
SELECT
	fa.fach_id,
    fa.anzeigename AS fach,
    GROUP_CONCAT(th.thema_id SEPARATOR '&&') AS themaNr
FROM
	faecher fa
		LEFT JOIN
	themen th ON th.fach_id = fa.fach_id
GROUP BY fach;

SELECT 
    *
FROM
    fragen f
WHERE
    f.thema_id = 1;

-- LOAD Frage
SELECT 
    f.frage_id, f.fragenText, f.fragenTyp
FROM
    fragen f
WHERE
    f.frage_id = 1;

-- Load Antworten von Fragen
SELECT 
    a.antwort_id, a.antwortText, a.istRichtig
FROM
    antworten a
WHERE
    a.fragen_id = 1;

SELECT 
    *
FROM
    themen;

-- Update thema-anzeigename
UPDATE themen 
SET 
    anzeigename = 'Variablen'
WHERE
    thema_id = 1;

-- getMaxThemaNr
SELECT 
    MAX(thema_id)
FROM
    themen;
    
-- SaveFrage (Insert or Update Frage)
INSERT INTO fragen VALUES (1, 'Welcher Datentyp wird für das Speichern von Wahrheitswerten genutzt ?', 'SingleChoice', 1) ON DUPLICATE KEY UPDATE fragenText='Welcher Datentyp wird für das Speichern von Wahrheitswerten genutzt?';

-- SaveAntwort (Insert or Update Antwort)
	INSERT INTO antworten VALUES ();

-- Spalte Ändern    
ALTER TABLE fragen MODIFY COLUMN frage_id INT AUTO_INCREMENT;

delete from antworten WHERE fragen_id=3;
delete from fragen WHERE frage_id=3;

INSERT INTO benutzer_fragen VALUES (1, 2, 1, 3, 'basti', 1);
INSERT INTO benutzer_fragen VALUES (2, 2, 1, 3, 'basti', 2);

INSERT INTO fragen VALUES (1, 'Welcher Datentyp wird für das Speichern von Wahrheitswerten genutzt?', 'SingleChoice', 1);

SELECT * FROM faecher;

INSERT INTO faecher (anzeigename) VALUE ('ABWL');

DELETE FROM benutzer_fragen WHERE benutzerfrage_frage_id=1;

SELECT * from benutzer_themen;

-- Alles Gruppen mit zugewiesenen ThemenNummern und Namen
select g.gruppeNr, g.ausbildungName, bt.benutzerthemen_thema_id, th.anzeigename as Themen from gruppe g
	JOIN benutzer_themen bt ON g.gruppeNr = bt.benutzerthemen_gruppe
    RIGHT JOIN themen th ON bt.benutzerthemen_thema_id = th.thema_id;

SELECT 
    th.thema_id, th.anzeigename
FROM
    themen th
        JOIN
    benutzer_themen bt ON bt.benutzerthemen_thema_id = th.thema_id
WHERE
    bt.benutzerthemen_gruppe = '2502';

SELECT
	th.thema_id, th.anzeigename
FROM
	themen th
		JOIN
	faecher f ON f.fach_id = th.fach_id
WHERE f.fach_id=1;



-- Statistik
select * from benutzer_fragen;
INSERT INTO benutzer_fragen (benutzer_uid, frage_id, richtig, falsch, abgefragt) VALUES ('user',2,0,0,1) ON DUPLICATE KEY UPDATE richtig=richtig+1, abgefragt=abgefragt+1;
INSERT IGNORE INTO benutzer_fragen (benutzer_uid, frage_id) VALUES ('user',1);

select * from antworten WHERE fragen_id=2;

DELETE FROM antworten WHERE antwort_id=16;

UPDATE antworten set istRichtig=0 WHERE antwort_id=2;


SELECT 
    bf.frage_id, bf.abgefragt, bf.richtig, bf.falsch
FROM
    benutzer_fragen bf
        JOIN
    fragen f ON bf.frage_id = f.frage_id
        JOIN
    themen t ON t.thema_id = f.thema_id
WHERE
    bf.benutzer_uid = 'user'
        AND t.thema_id = 1;



