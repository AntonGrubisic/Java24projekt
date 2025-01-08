use demo;

-- DROP
-- --------------------------------------------------------------------------------
DROP TABLE Answer;
DROP TABLE Question;
DROP TABLE Country;
DROP TABLE Continent;
DROP TABLE User;


-- CREATE TABLE
-- --------------------------------------------------------------------------------
CREATE TABLE User(
    userId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userName VARCHAR(255) NOT NULL UNIQUE,
    userCountry VARCHAR(255) NOT NULL
);

CREATE TABLE Continent (
    continentId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    continentName VARCHAR (255) NOT NULL UNIQUE,
    continentCountries INTEGER,
    continentSize INTEGER
);

CREATE TABLE Country (
    countryId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    countryName VARCHAR(255) UNIQUE,
    countryPopulation INTEGER,
    countryCapital VARCHAR(255),
    countryLandmark VARCHAR(255),
    continentId INTEGER NOT NULL,
    FOREIGN KEY (continentId) REFERENCES Continent(continentId)
);

CREATE TABLE Question(
    questionId INTEGER PRIMARY KEY AUTO_INCREMENT,
    questionText VARCHAR(255),
    countryId INTEGER,
    FOREIGN KEY (countryId) REFERENCES Country (countryId)
);

CREATE TABLE Answer (
    answerId INTEGER PRIMARY KEY AUTO_INCREMENT,
    answerOptionText VARCHAR(255),
    answerIsCorrect boolean NOT NULL,
    questionId INTEGER,
    FOREIGN KEY(questionId) REFERENCES Question(questionId)
);

-- INSERT INTO
-- --------------------------------------------------------------------------------
INSERT INTO User (userId, userName, userCountry)
VALUES (1, 'Geoffry', 'Sweden'),
       (2, 'Amanda', 'Denmark'),
       (3, 'Rikard', 'Sweden');

INSERT INTO Continent (continentName, continentCountries, continentSize)
VALUES
        ('Europa', 44, 10600000),
        ('Antarktis', 12, 13660000),
        ('Asien', 48, 17226200),
        ('Oceanien', 14, 8525989),
        ('Afrika', 54, 30370000),
        ('Nordamerika',23, 24500000),
        ('Sydamerika',12, 17840000);

INSERT INTO Country (countryname, countryCapital, countryPopulation, countryLandmark, continentId)
VALUES
    ('Albanien', 'Tirana', 2800000, 'Berat', 1),
    ('Andorra', 'Andorra la Vella', 77000, 'Caldea Spa', 1),
    ('Belgien', 'Bryssel', 11500000, 'Grand Place', 1),
    ('Bosnien och Hercegovina', 'Sarajevo', 3300000, 'Stari Most-bron', 1),
    ('Bulgarien', 'Sofia', 6900000, 'Rilaklostret', 1),
    ('Danmark', 'Köpenhamn', 5800000, 'Tivoli', 1),
    ('Estland', 'Tallinn', 1300000, 'Gamla stan', 1),
    ('Finland', 'Helsingfors', 5500000, 'Sveaborg', 1),
    ('Frankrike', 'Paris', 65000000, 'Eiffeltornet', 1),
    ('Grekland', 'Aten', 10400000, 'Akropolis', 1),
    ('Irland', 'Dublin', 4900000, 'Cliffs of Moher', 1),
    ('Island', 'Reykjavik', 341000, 'Blå lagunen', 1),
    ('Italien', 'Rom', 60400000, 'Colosseum', 1),
    ('Kosovo', 'Pristina', 1800000, 'Visoki Decani-klostret', 1),
    ('Kroatien', 'Zagreb', 4100000, 'Plitvicesjöarna', 1),
    ('Lettland', 'Riga', 1900000, 'Gamla stan', 1),
    ('Liechtenstein', 'Vaduz', 38000, 'Vaduz slott', 1),
    ('Litauen', 'Vilnius', 2800000, 'Korskullen', 1),
    ('Luxemburg', 'Luxemburg', 625000, 'Casemates du Bock', 1),
    ('Malta', 'Valletta', 514000, 'Grand Harbour', 1),
    ('Moldavien', 'Chisinau', 2600000, 'Orheiul Vechi', 1),
    ('Monaco', 'Monaco', 39000, 'Monte Carlo Casino', 1),
    ('Montenegro', 'Podgorica', 622000, 'Kotorbukten', 1),
    ('Nederländerna', 'Amsterdam', 17400000, 'Anne Franks hus', 1),
    ('Nordmakedonien', 'Skopje', 2100000, 'Ohridsjön', 1),
    ('Norge', 'Oslo', 5400000, 'Geirangerfjorden', 1),
    ('Polen', 'Warszawa', 37800000, 'Gamla stan i Kraków', 1),
    ('Portugal', 'Lissabon', 10300000, 'Torre de Belém', 1),
    ('Rumänien', 'Bukarest', 19100000, 'Bran Castle', 1),
    ('San Marino', 'San Marino', 34000, 'Guaita-tornet', 1),
    ('Schweiz', 'Bern', 8700000, 'Matterhorn', 1),
    ('Serbien', 'Belgrad', 6800000, 'Kalemegdan-fästningen', 1),
    ('Slovakien', 'Bratislava', 5400000, 'Bratislava slott', 1),
    ('Slovenien', 'Ljubljana', 2100000, 'Bledsjön', 1),
    ('Spanien', 'Madrid', 47300000, 'Alhambra', 1),
    ('Storbritannien', 'London', 67200000, 'Big Ben', 1),
    ('Sverige', 'Stockholm', 10500000, 'Kungliga slottet', 1),
    ('Tjeckien', 'Prag', 10700000, 'Karlsbron', 1),
    ('Tyskland', 'Berlin', 83200000, 'Brandenburger Tor', 1),
    ('Ukraina', 'Kiev', 43300000, 'Sofiakatedralen', 1),
    ('Ungern', 'Budapest', 9700000, 'Parlamentet', 1),
    ('Vatikanstaten', 'Vatikanstaten', 800, 'Peterskyrkan', 1),
    ('Vitryssland', 'Minsk', 9300000, 'Nesvizh slott', 1),
    ('Österrike', 'Wien', 8900000, 'Schönbrunn slott', 1),
    ('Ryssland', 'Moskva', 143400000, 'Röda torget', 1);

-- Europa frågor
-- fråga 1
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Albanien?', 1);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (1, 'Sarajevo', 0),
       (1, 'Tirana', 1),
       (1, 'Paris', 0),
       (1, 'Aten', 0);

-- fråga 2
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Belgien?', 2);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (2, 'Berlin', 0),
       (2, 'Bryssel', 1),
       (2, 'Paris', 0),
       (2, 'Amsterdam', 0);

-- fråga 3
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Frankrike?', 3);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (3, 'Madrid', 0),
       (3, 'Paris', 1),
       (3, 'Rom', 0),
       (3, 'Berlin', 0);

-- fråga 4
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är befolkningen i Grekland?', 4);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (4, '10400000', 1),
       (4, '7000000', 0),
       (4, '5000000', 0),
       (4, '25000000', 0);

-- fråga 5
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken landmärke finns i Rom?', 5);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (5, 'Frihetsgudinnan', 0),
       (5, 'Big Ben', 0),
       (5, 'Colosseum', 1),
       (5, 'Eiffeltornet', 0);

-- fråga 6
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Spanien?', 6);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (6, 'Paris', 0),
       (6, 'Barcelona', 0),
       (6, 'Madrid', 1),
       (6, 'Lissabon', 0);

-- fråga 7
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Sverige?', 7);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (7, 'Helsingfors', 0),
       (7, 'Oslo', 0),
       (7, 'Stockholm', 1),
       (7, 'Köpenhamn', 0);

-- fråga 8
INSERT INTO Question (questionText, countryId)
VALUES ('Vad är huvudstaden i Ryssland?', 8);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (8, 'Moskva', 1),
       (8, 'Kiev', 0),
       (8, 'Belgrad', 0),
       (8, 'Sankt Petersburg', 0);

-- fråga 9
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken landmärke finns i Storbritannien?', 9);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (9, 'Big Ben', 1),
       (9, 'Frihetsgudinnan', 0),
       (9, 'Colosseum', 0),
       (9, 'Eiffeltornet', 0);

-- fråga 10
INSERT INTO Question (questionText, countryId)
VALUES ('Vilket land är känt för Eiffeltornet?', 10);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (10, 'Spanien', 0),
       (10, 'Frankrike', 1),
       (10, 'Italien', 0),
       (10, 'Tyskland', 0);


-- kontinent frågor
-- fråga 1
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Sverige i?', 1);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (1, 'Afrika', 0),
       (1, 'Europa', 1),
       (1, 'Nordamerika', 0),
       (1, 'Asien', 0);

-- fråga 2
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Brasilien i?', 2);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (2, 'Europa', 0),
       (2, 'Asien', 0),
       (2, 'Sydamerika', 1),
       (2, 'Afrika', 0);

-- fråga 3
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Kina i?', 3);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (3, 'Nordamerika', 0),
       (3, 'Asien', 1),
       (3, 'Europa', 0),
       (3, 'Sydamerika', 0);

-- fråga 4
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Egypten i?', 4);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (4, 'Afrika', 1),
       (4, 'Asien', 0),
       (4, 'Sydamerika', 0),
       (4, 'Europa', 0);

-- fråga 5
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Kanada i?', 5);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (5, 'Nordamerika', 1),
       (5, 'Afrika', 0),
       (5, 'Asien', 0),
       (5, 'Europa', 0);

-- fråga 6
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Australien i?', 6);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (6, 'Oceanien', 1),
       (6, 'Afrika', 0),
       (6, 'Sydamerika', 0),
       (6, 'Asien', 0);

-- fråga 7
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Indien i?', 7);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (7, 'Afrika', 0),
       (7, 'Sydamerika', 0),
       (7, 'Asien', 1),
       (7, 'Europa', 0);

-- fråga 8
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Argentina i?', 8);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (8, 'Sydamerika', 1),
       (8, 'Europa', 0),
       (8, 'Nordamerika', 0),
       (8, 'Asien', 0);

-- fråga 9
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Japan i?', 9);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
VALUES (9, 'Asien', 1),
       (9, 'Afrika', 0),
       (9, 'Sydamerika', 0),
       (9, 'Europa', 0);

-- fråga 10
INSERT INTO Question (questionText, countryId)
VALUES ('Vilken kontinent ligger Tyskland i?', 10);

INSERT INTO Answer (questionId, answerOptionText, answerIsCorrect)
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
