use demo;

-- DROP
-- --------------------------------------------------------------------------------
DROP TABLE QuizSession;
DROP TABLE Answer;
DROP TABLE Question;
DROP TABLE Country;
DROP TABLE Continent;
DROP TABLE User;

-- CREATE TABLE
-- --------------------------------------------------------------------------------
CREATE TABLE User(
    userId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userName VARCHAR(255) UNIQUE,
    userScore INTEGER NOT NULL

);

CREATE TABLE Continent (
    continentId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    continentName VARCHAR (255),
    continentCountries INTEGER,
    continentSize INTEGER
);

CREATE TABLE Country (
    countryId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    countryName VARCHAR(255) UNIQUE,
    countryPopulation INTEGER,
    countryCapital VARCHAR(255),
    landmark VARCHAR(255)
);

CREATE TABLE Question(
    questionId INTEGER PRIMARY KEY AUTO_INCREMENT,
    questionText VARCHAR(255),
    countryId INTEGER,
    FOREIGN KEY (countryId) REFERENCES Country (countryId)
);

CREATE TABLE Answer (
    answerId INTEGER PRIMARY KEY AUTO_INCREMENT,
    questionId INTEGER,
    optionText VARCHAR(255),
    isCorrect TINYINT(1) NOT NULL DEFAULT 0,
    FOREIGN KEY(questionId) REFERENCES Question(questionId)
);
ALTER TABLE Answer ADD COLUMN correctOption INTEGER;

CREATE TABLE QuizSession (
    quizSessionId INTEGER PRIMARY KEY AUTO_INCREMENT,
    userId INTEGER,
    questionId INTEGER,
    answerId INTEGER,
    FOREIGN KEY(userId)  REFERENCES User(userId),
    FOREIGN KEY(questionId)  REFERENCES Question(questionId),
    FOREIGN KEY(answerId)  REFERENCES Answer(answerId)
);

-- INSERT INTO
-- --------------------------------------------------------------------------------
INSERT INTO User (userId, userName)
VALUES (1, 'Anna'),
       (2, 'Björn'),
       (3, 'Carla');

INSERT INTO User (userId, userName, userScore)
VALUES (4, 'Geoffry', 3),
       (5, 'Amanda', 2),
       (6, 'Rikard', 4);

INSERT INTO Country (countryname, countryCapital, countryPopulation, landmark)
VALUES
    ('Albanien', 'Tirana', 2800000, 'Berat'),
    ('Andorra', 'Andorra la Vella', 77000, 'Caldea Spa'),
    ('Belgien', 'Bryssel', 11500000, 'Grand Place'),
    ('Bosnien och Hercegovina', 'Sarajevo', 3300000, 'Stari Most-bron'),
    ('Bulgarien', 'Sofia', 6900000, 'Rilaklostret'),
    ('Danmark', 'Köpenhamn', 5800000, 'Tivoli'),
    ('Estland', 'Tallinn', 1300000, 'Gamla stan'),
    ('Finland', 'Helsingfors', 5500000, 'Sveaborg'),
    ('Frankrike', 'Paris', 65000000, 'Eiffeltornet'),
    ('Grekland', 'Aten', 10400000, 'Akropolis'),
    ('Irland', 'Dublin', 4900000, 'Cliffs of Moher'),
    ('Island', 'Reykjavik', 341000, 'Blå lagunen'),
    ('Italien', 'Rom', 60400000, 'Colosseum'),
    ('Kosovo', 'Pristina', 1800000, 'Visoki Decani-klostret'),
    ('Kroatien', 'Zagreb', 4100000, 'Plitvicesjöarna'),
    ('Lettland', 'Riga', 1900000, 'Gamla stan'),
    ('Liechtenstein', 'Vaduz', 38000, 'Vaduz slott'),
    ('Litauen', 'Vilnius', 2800000, 'Korskullen'),
    ('Luxemburg', 'Luxemburg', 625000, 'Casemates du Bock'),
    ('Malta', 'Valletta', 514000, 'Grand Harbour'),
    ('Moldavien', 'Chisinau', 2600000, 'Orheiul Vechi'),
    ('Monaco', 'Monaco', 39000, 'Monte Carlo Casino'),
    ('Montenegro', 'Podgorica', 622000, 'Kotorbukten'),
    ('Nederländerna', 'Amsterdam', 17400000, 'Anne Franks hus'),
    ('Nordmakedonien', 'Skopje', 2100000, 'Ohridsjön'),
    ('Norge', 'Oslo', 5400000, 'Geirangerfjorden'),
    ('Polen', 'Warszawa', 37800000, 'Gamla stan i Kraków'),
    ('Portugal', 'Lissabon', 10300000, 'Torre de Belém'),
    ('Rumänien', 'Bukarest', 19100000, 'Bran Castle'),
    ('San Marino', 'San Marino', 34000, 'Guaita-tornet'),
    ('Schweiz', 'Bern', 8700000, 'Matterhorn'),
    ('Serbien', 'Belgrad', 6800000, 'Kalemegdan-fästningen'),
    ('Slovakien', 'Bratislava', 5400000, 'Bratislava slott'),
    ('Slovenien', 'Ljubljana', 2100000, 'Bledsjön'),
    ('Spanien', 'Madrid', 47300000, 'Alhambra'),
    ('Storbritannien', 'London', 67200000, 'Big Ben'),
    ('Sverige', 'Stockholm', 10500000, 'Kungliga slottet'),
    ('Tjeckien', 'Prag', 10700000, 'Karlsbron'),
    ('Tyskland', 'Berlin', 83200000, 'Brandenburger Tor'),
    ('Ukraina', 'Kiev', 43300000, 'Sofiakatedralen'),
    ('Ungern', 'Budapest', 9700000, 'Parlamentet'),
    ('Vatikanstaten', 'Vatikanstaten', 800, 'Peterskyrkan'),
    ('Vitryssland', 'Minsk', 9300000, 'Nesvizh slott'),
    ('Österrike', 'Wien', 8900000, 'Schönbrunn slott'),
    ('Ryssland', 'Moskva', 143400000, 'Röda torget');

-- Europa frågor
-- fråga 1
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Albanien?', 1);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (1, 'Sarajevo', 0),
       (1, 'Tirana', 1),
       (1, 'Paris', 0),
       (1, 'Aten', 0);

-- fråga 2
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Belgien?', 2);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (2, 'Berlin', 0),
       (2, 'Bryssel', 1),
       (2, 'Paris', 0),
       (2, 'Amsterdam', 0);

-- fråga 3
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Frankrike?', 3);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (3, 'Madrid', 0),
       (3, 'Paris', 1),
       (3, 'Rom', 0),
       (3, 'Berlin', 0);

-- fråga 4
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är befolkningen i Grekland?', 4);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (4, '10400000', 1),
       (4, '7000000', 0),
       (4, '5000000', 0),
       (4, '25000000', 0);

-- fråga 5
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken landmärke finns i Rom?', 5);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (5, 'Frihetsgudinnan', 0),
       (5, 'Big Ben', 0),
       (5, 'Colosseum', 1),
       (5, 'Eiffeltornet', 0);

-- fråga 6
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Spanien?', 6);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (6, 'Paris', 0),
       (6, 'Barcelona', 0),
       (6, 'Madrid', 1),
       (6, 'Lissabon', 0);

-- fråga 7
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Sverige?', 7);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (7, 'Helsingfors', 0),
       (7, 'Oslo', 0),
       (7, 'Stockholm', 1),
       (7, 'Köpenhamn', 0);

-- fråga 8
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Ryssland?', 8);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (8, 'Moskva', 1),
       (8, 'Kiev', 0),
       (8, 'Belgrad', 0),
       (8, 'Sankt Petersburg', 0);

-- fråga 9
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken landmärke finns i Storbritannien?', 9);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (9, 'Big Ben', 1),
       (9, 'Frihetsgudinnan', 0),
       (9, 'Colosseum', 0),
       (9, 'Eiffeltornet', 0);

-- fråga 10
INSERT INTO Question (questionText, countryId)
VALUES ('Vilket land är känt för Eiffeltornet?', 10);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (10, 'Spanien', 0),
       (10, 'Frankrike', 1),
       (10, 'Italien', 0),
       (10, 'Tyskland', 0);


-- kontinent frågor
-- fråga 1
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Sverige i?', 1);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (1, 'Afrika', 0),
       (1, 'Europa', 1),
       (1, 'Nordamerika', 0),
       (1, 'Asien', 0);

-- fråga 2
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Brasilien i?', 2);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (2, 'Europa', 0),
       (2, 'Asien', 0),
       (2, 'Sydamerika', 1),
       (2, 'Afrika', 0);

-- fråga 3
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Kina i?', 3);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (3, 'Nordamerika', 0),
       (3, 'Asien', 1),
       (3, 'Europa', 0),
       (3, 'Sydamerika', 0);

-- fråga 4
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Egypten i?', 4);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (4, 'Afrika', 1),
       (4, 'Asien', 0),
       (4, 'Sydamerika', 0),
       (4, 'Europa', 0);

-- fråga 5
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Kanada i?', 5);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (5, 'Nordamerika', 1),
       (5, 'Afrika', 0),
       (5, 'Asien', 0),
       (5, 'Europa', 0);

-- fråga 6
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Australien i?', 6);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (6, 'Oceanien', 1),
       (6, 'Afrika', 0),
       (6, 'Sydamerika', 0),
       (6, 'Asien', 0);

-- fråga 7
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Indien i?', 7);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (7, 'Afrika', 0),
       (7, 'Sydamerika', 0),
       (7, 'Asien', 1),
       (7, 'Europa', 0);

-- fråga 8
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Argentina i?', 8);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (8, 'Sydamerika', 1),
       (8, 'Europa', 0),
       (8, 'Nordamerika', 0),
       (8, 'Asien', 0);

-- fråga 9
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Japan i?', 9);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (9, 'Asien', 1),
       (9, 'Afrika', 0),
       (9, 'Sydamerika', 0),
       (9, 'Europa', 0);

-- fråga 10
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Tyskland i?', 10);

INSERT INTO Answer (questionId, optionText, isCorrect)
VALUES (10, 'Europa', 1),
       (10, 'Sydamerika', 0),
       (10, 'Afrika', 0),
       (10, 'Asien', 0);

-- SELECT
-- --------------------------------------------------------------------------------
SELECT * FROM User;
SELECT * FROM Country;
SELECT * FROM Continent;
SELECT * FROM Question;
SELECT * FROM Answer;

TRUNCATE TABLE Question;
TRUNCATE TABLE Answer;

SET FOREIGN_KEY_CHECKS = 1;
