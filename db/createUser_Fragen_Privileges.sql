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
GRANT SELECT, INSERT, UPDATE on lerntool.benutzer_fragen to 'dbUser';

INSERT INTO gruppe VALUES ('2502','WI');
INSERT INTO gruppe VALUES ('2501','WI');
INSERT INTO gruppe VALUES ('admin','admin');

-- PW: 1234
INSERT INTO benutzer VALUES ('user', 'Sebastian Adrian', '$2a$10$NSokH8WUke86qSUXiX8Hee5.proUFeJenMJopzTa6bCzCT.znDO/G', '2502', 'user'); 
UPDATE benutzer SET passwort="$2a$10$yyuzdFrH0h5r7bYNA6ZLHurzL5LkrgvsozfUJZvM6S8iKSKAWUKZO" WHERE uid='admin';


-- PW: 123
INSERT INTO benutzer VALUES ('admin', 'Sebastian Adrian', '$2a$10$IR4gMUv1TOUsWtu8belXzui5q316qnfdL3GMegE7usbvey00BK9Aa', 'admin', 'admin');


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