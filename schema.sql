use demo;

CREATE TABLE User
(
    userId INTEGER PRIMARY KEY,
    userName VARCHAR(255)
);

CREATE TABLE Country (
    countryId INTEGER PRIMARY KEY,
    countryName VARCHAR(255),
    countryPopulation INTEGER,
    countryCapital VARCHAR(255),
    landmark VARCHAR(255)
);

CREATE TABLE QuizSession (
    quizSessionId INTEGER PRIMARY KEY,
    userId INTEGER,
    questionId INTEGER,
    answerId INTEGER,
    FOREIGN KEY(userId)  REFERENCES User(userId),
    FOREIGN KEY(questionId)  REFERENCES Question(questionId),
    FOREIGN KEY(answerId)  REFERENCES Answer(answerId)
);

CREATE TABLE Question
(
    questionId INTEGER PRIMARY KEY,
    questionText VARCHAR(255),
    countryId INTEGER,
    FOREIGN KEY (countryId) REFERENCES Country (countryId)
);

CREATE TABLE Answer (
    answerId INTEGER PRIMARY KEY,
    questionId INTEGER,
    optionText VARCHAR(255),
    isCorrect TINYINT(1) NOT NULL,
    FOREIGN KEY(questionId) REFERENCES Question(questionId)
);

insert into Country (countryname, countryCapital, countryPopulation, landmark)
values
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
