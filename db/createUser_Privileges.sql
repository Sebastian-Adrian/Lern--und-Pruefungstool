use lerntool;

create user dozent IDENTIFIED by 'MDq0?qA#xq7$TGapAv6W';
GRANT select, insert, update, delete on lerntool.fragen to 'dozent';
GRANT select, insert, update, delete on lerntool.antworten to 'dozent';
GRANT select, insert, update, delete on lerntool.faecher to 'dozent';
GRANT select, insert, update, delete on lerntool.themen to 'dozent';
GRANT select, insert, update, delete on lerntool.antworten to 'dozent';
GRANT select, insert, update, delete, DROP on lerntool.benutzer_themen to 'dozent';
GRANT select, insert, update, delete on lerntool.benutzer_fragen to 'dozent';
GRANT select, insert, update, delete on lerntool.gruppe to 'dozent';

create user auth IDENTIFIED by '30<_H+CBaiPXk(5[eh7s';

GRANT SELECT on lerntool.benutzer to 'auth';

create user dbUser IDENTIFIED by '$TNpUrrf9t5@A19ZBFuV';
GRANT SELECT on lerntool.fragen to 'dbUser';
GRANT SELECT on lerntool.benutzer to 'dbUser';
GRANT SELECT on lerntool.antworten to 'dbUser';
GRANT SELECT on lerntool.faecher to 'dbUser';
GRANT SELECT on lerntool.benutzer_themen to 'dbUser';
GRANT SELECT on lerntool.themen to 'dbUser';

INSERT INTO gruppe VALUES ('2502','WI');
INSERT INTO gruppe VALUES ('2501','WI');
INSERT INTO gruppe VALUES ('admin','admin');

INSERT INTO benutzer VALUES ('basti', 'Sebastian Adrian', '$2a$10$NSokH8WUke86qSUXiX8Hee5.proUFeJenMJopzTa6bCzCT.znDO/G', '2502', 'user');
INSERT INTO benutzer VALUES ('admin', 'Sebastian Adrian', '$2a$10$IR4gMUv1TOUsWtu8belXzui5q316qnfdL3GMegE7usbvey00BK9Aa', 'admin', 'admin');

UPDATE benutzer 
SET 
    passwort = '$2a$10$NSokH8WUke86qSUXiX8Hee5.proUFeJenMJopzTa6bCzCT.znDO/G'
WHERE
    uid = 'basti';

INSERT INTO faecher VALUES (1, 'JAVA');
INSERT INTO themen VALUES (1, 'Variablen', 1);
INSERT INTO themen VALUES (2, 'Methoden', 1);
INSERT INTO fragen VALUES (1, 'Welcher Datentyp wird für das Speichern von Wahrheitswerten genutzt?', 'SingleChoice', 1);
INSERT INTO antworten VALUES (1, 'Integer', 1, 0);
INSERT INTO antworten VALUES (2, 'String', 1, 0);
INSERT INTO antworten VALUES (3, 'Boolean', 1, 1);
INSERT INTO antworten VALUES (4, 'Float', 1, 0);

INSERT INTO fragen VALUES (2, 'Bei welchen der folgenden Datentypen handelt es sich um Primitive Datentypen?', 'MultipleChoiceCheck', 1);
INSERT INTO antworten VALUES (5, 'Integer', 2, 0);
INSERT INTO antworten VALUES (6, 'String', 2, 0);
INSERT INTO antworten VALUES (7, 'boolean', 2, 1);
INSERT INTO antworten VALUES (8, 'float', 2, 1);
INSERT INTO antworten VALUES (9, 'char', 2, 1);


INSERT INTO lerntool.benutzer_themen VALUES ('2502', 1);
INSERT INTO lerntool.benutzer_themen VALUES ('2502', 2);


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

select * from benutzer_themen;

