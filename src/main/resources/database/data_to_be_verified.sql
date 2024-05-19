insert into ulgi
values (0, 'Studenci do 26 lat / Doktoranci do 35 lat', 51),
       (1, 'Emeryci i renciści', 51),
       (2, 'Dzieci/Młodzież', 37),
       (3, 'Kombatanci', 51),
       (4, 'Dzieci/Młodzież/Studenci niepełnowsprawni', 87),
       (5, 'Niepełnosprawni', 37),
       (6, 'Opiekun osoby niepełnosprawnej', 30);

insert into rabaty
values (0, 'Zniżka dla uczniów szkoł w czasie ferii', 75, '2024-01-15', '2024-02-03'),
       (1, 'Zniżka dla maturzystów na czas matur', 90, '2024-05-04', '2024-05-24'),
       (2, 'Zniżka dla osób do 18 roku życia na dzień dziecka', 50, '2024-05-31', '2024-06-01');

insert into klienci
values (0, 'Jan', 'Kowalski', '1974-02-21', 'jan.kowalski@wp.pl', '123456789'),
       (1, 'Justyna', 'Brzezińska', '2004-12-23', 'justyna2004br@gmail.com', '602312082'),
       (2, 'Aleksander', 'Domański', '1959-06-07', 'adamdomanski1959@o2.pl', '885902847');

insert into konto
values (0, 'jan_kowalski1974', 52902934, 0),
       (1, 'ad1959oldman', 7207029, 2),
       (2, 'justynab1337', 2409802, 1);

insert into stacje
values (0, 'Warszawa Centralna'),
       (1, 'Warszawa Zachodnia'),
       (2, 'Warszawa Gdańska'),
       (3, 'Kraków Główny'),
       (4, 'Kraków Płaszów'),
       (5, 'Kraków Bieżanów'),
       (6, 'Kraków Prokocim'),
       (7, 'Wrocław Główny'),
       (8, 'Brzesko'),
       (9, 'Bobowa'),
       (10, 'Stróże'),
       (11, 'Grybów'),
       (12, 'Ptaszkowa'),
       (13, 'Miechów'),
       (14, 'Włoszczowa'),
       (15, 'Opoczno'),
       (16, 'Grodzisk Mazowiecki'),
       (17, 'Nowy Targ'),
       (18, 'Zakopane'),
       (19, 'Tarnów'),
       (20, 'Nowy Sącz');

insert into trasy
values (0, 7), -- Kraków Główny --> Warszawa Centralna
       (1, 2), -- Kraków Główny --> Wrocław Główny
       (2, 3), -- Zakopane --> Kraków Główny
       (3, 9); -- Kraków Główny --> Nowy Sącz

insert into stacje_posrednie
values (0, 1, 3, null, '1 hour 6 minutes'),
       (0, 2, 13, '1 minute', '22 minutes'),
       (0, 3, 14, '2 minutes', '15 minutes'),
       (0, 4, 15, '1 minute', '26 minutes'),
       (0, 5, 16, '1 minute', '34 minutes'),
       (0, 6, 1, '5 minutes', '8 minutes'),
       (0, 7, 0, null, null),
       (1, 1, 3, null, '3 hours 13 minutes'),
       (1, 2, 7, null, null),
       (2, 1, 18, null, '25 minutes'),
       (2, 2, 17, '10 minutes', '1 hours 45 minutes'),
       (2, 3, 3, null, null),
       (3, 1, 3, null, '5 minutes'),
       (3, 2, 4, '1 minute', '3 minutes'),
       (3, 3, 6, '2 minutes', '4 minutes'),
       (3, 4, 5, '1 minute', '47 minutes'),
       (3, 5, 19, '10 minutes', '34 minutes'),
       (3, 6, 10, '2 minutes', '24 minutes'),
       (3, 7, 11, '3 minutes', '7 minutes'),
       (3, 8, 12, '2 minutes', '9 minutes'),
       (3, 9, 20, null, null);



insert into trasy_przewoznicy
values (0, 0, 0),
       (1, 1, 0),
       (2, 2, 0),
       (3, 3, 0),
       (4, 3, 1),
       (5, 3, 2);

insert into przejazdy
values (0, 0, '2024-05-01', 10, 169, true, 'KRAKOWIAK'),
       (1, 1, '2024-05-01', 12, 149, true, 'WROCŁAWIAK'),
       (2, 2, '2024-05-01', 8, 50, true, 'ZAKOPIAŃCZYK'),
       (3, 3, '2024-05-01', 3, 34, true, 'NIKIFOR'),
       (4, 4, '2024-05-01', 6, 28, false, 'DUNAJEC'),
       (5, 5, '2024-05-01', 8, 24, false, 'REGIO1337');
