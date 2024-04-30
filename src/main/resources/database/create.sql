create table rabaty(
    id_rabatu integer primary key,
    nazwa varchar(50) not null,
    znizka numeric(2) not null,
    data_waznosci date
);

create table ulgi(
    id_ulgi integer primary key,
    nazwa varchar(50) not null,
    znizka numeric(2)
);

create table uzytkownicy(
    id_uzytkownika integer primary key,
    imie varchar(20) not null,
    nazwisko varchar(40) not null,
    data_urodzenia date not null,
    email varchar(50) not null,
    nr_telefonu char(9)
);

create table konto(
    id_konta integer primary key ,
    login varchar(30) not null,
    haslo integer not null,
    id_uzytkownika integer references uzytkownicy
);

create table zamowienia(
    id_zamowienia integer primary key,
    id_uzytkownika integer not null references uzytkownicy,
    data_kupna date not null,
    data_zwrotu date check ( data_kupna<data_zwrotu ),
    id_ulgi integer references ulgi,
    id_rabatu integer references rabaty
);

create table przewoznicy(
    id_przewoznika integer primary key,
    nazwa_skrocona varchar(5) not null,
    nazwa varchar(50) not null
);

create table stacje(
    id_stacji integer primary key,
    nazwa varchar(30) not null
);

create table trasy(
    id_trasy integer primary key,
    ile_stacji numeric(2) not null,
    id_przewoznika integer references przewoznicy
);

create table stacje_posrednie(
    id_trasy integer not null references trasy,
    numer_stacji numeric(2) not null,
    id_stacji integer not null references stacje,
    czas_postoju interval,
    czas_przejazdu interval
);

create table cennik_biletow_okresowych(
    id_typ_biletu integer primary key,
    cena_bazowa numeric(5,2) not null ,
    okres_waznosci interval not null ,
    id_przewoznika integer not null references przewoznicy,
    nazwa varchar(30)
);

create table bilety_okresowe_zamowienia(
    id_zamowienia integer not null references zamowienia,
    data_od date not null,
    id_typ_biletu integer not null references cennik_biletow_okresowych
);

create table przejazdy(
    id_przejazdu integer primary key,
    id_trasy integer not null references trasy,
    koszt_bazowy numeric(3,2) not null,
    czy_rezerwacja_miejsc boolean not null,
    nazwa varchar(30)
);

create table wagony(
    id_wagonu integer primary key,
    typ_wagonu varchar(20) not null,
    klasa numeric(1) not null,
    liczba_miejsc numeric(3) not null
);

create table wagony_typy_miejsc(
    id_wagonu integer references wagony,
    miejsce_mod integer not null,
    typ_miejsca varchar(20) not null
);

create table szczegoly_biletu(
    id_szczegolow integer primary key,
    rower boolean not null,
    dodatkowy_bagaz boolean not null,
    zwierze boolean not null
);

create table przejazdy_wagony(
    id_przejazdu_wagonu integer primary key,
    id_przejazdu integer not null references przejazdy,
    odcinek_trasy integer not null,
    id_wagonu integer not null references wagony,
    nr_wagonu numeric(2) not null,
    nr_miejsca numeric(3) not null,
    unique (id_przejazdu,nr_wagonu,nr_miejsca)
);

create table bilety_jednorazowe_zamowienia(
    id_zamowienia integer not null references zamowienia,
    id_przejazdu integer not null references przejazdy_wagony,
    od_stacji integer not null,
    do_stacji integer not null check ( od_stacji<do_stacji ),
    nr_wagonu integer not null references przejazdy_wagony,
    nr_miejsca integer not null references przejazdy_wagony,
    id_szczegolow integer not null references szczegoly_biletu
);