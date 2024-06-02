create table rabaty
(
    id_rabatu         serial primary key,
    nazwa             varchar(50) not null,
    znizka            integer     not null,
    data_wprowadzenia date        not null,
    data_waznosci     date check ( data_wprowadzenia <= data_waznosci )
);

create table ulgi
(
    id_ulgi serial primary key,
    nazwa   varchar(50) not null,
    znizka  integer
);

create table klienci
(
    id_klienta     serial primary key,
    imie           varchar(20) not null,
    nazwisko       varchar(40) not null,
    data_urodzenia date        not null,
    email          varchar(50) not null,
    nr_telefonu    varchar(18)
);

create table konto
(
    id_konta   serial primary key,
    login      varchar(30) not null unique,
    haslo      integer     not null,
    id_klienta integer references klienci
);

create table zamowienia
(
    id_zamowienia serial primary key,
    id_klienta    integer not null references klienci
);

create table przewoznicy
(
    id_przewoznika serial primary key,
    nazwa_skrocona varchar(5)  not null,
    nazwa          varchar(50) not null
);

create table stacje
(
    id_stacji serial primary key,
    nazwa     varchar(30) not null
);

create table trasy
(
    id_trasy   serial primary key,
    ile_stacji smallint not null
);

create table stacje_posrednie
(
    id_stacji_posrednich serial primary key,
    id_trasy             integer  not null references trasy,
    numer_stacji         smallint not null,
    id_stacji            integer  not null references stacje,
    czas_postoju         interval,
    czas_przejazdu       interval
);

create table trasy_przewoznicy
(
    id_trasy_przewoznika serial primary key,
    id_trasy             integer references trasy,
    id_przewoznika       smallint references przewoznicy
);

create table cennik_biletow_okresowych
(
    id_typ_biletu  serial primary key,
    cena_bazowa    numeric(6    , 2) not null,
    okres_waznosci interval      not null,
    id_przewoznika integer       not null references przewoznicy,
    nazwa          varchar(30)
);

create table bilety_okresowe_zamowienia
(
    id_bilety_okresowe_zamowienia serial primary key,
    id_zamowienia                 integer   not null references zamowienia,
    timestamp_kupna               timestamp not null,
    timestamp_zwrotu              timestamp check ( timestamp_kupna < timestamp_zwrotu ),
    id_ulgi                       integer   not null references ulgi,
    id_rabatu                     integer references rabaty
);

create table bilety_okresowe
(
    id_biletu_okresowego          serial primary key,
    id_bilety_okresowe_zamowienia integer references bilety_okresowe_zamowienia,
    data_od                       date    not null,
    id_typ_biletu                 integer not null references cennik_biletow_okresowych
);

create table przejazdy
(
    id_przejazdu          serial primary key,
    id_trasy_przewoznika  integer       not null references trasy_przewoznicy,
    timestamp_przejazdu   timestamp     not null,
    koszt_bazowy          numeric(5, 2) not null,
    czy_rezerwacja_miejsc boolean       not null,
    nazwa                 varchar(30)
);

create table wagony
(
    id_wagonu     serial primary key,
    typ_wagonu    varchar(30) not null,
    klasa         smallint    not null,
    liczba_miejsc smallint    not null
);

create table wagony_typy_miejsc
(
    id_wagony_typy_miejsc serial primary key,
    id_wagonu             integer references wagony,
    miejsce_mod           smallint    not null,
    typ_miejsca           varchar(20) not null
);

create table szczegoly_biletu
(
    id_szczegolow   smallint primary key,
    rower           boolean not null,
    dodatkowy_bagaz boolean not null,
    zwierze         boolean not null
);

create table przejazdy_sklad
(
    id_przejazdu_skladu serial primary key,
    id_przejazdu        integer  not null references przejazdy,
    od_stacji           smallint not null,
    do_stacji           smallint not null
);

create table przejazdy_sklad_czesci
(
    id_przejazdy_sklad_czesci serial primary key,
    id_przejazdu_skladu       integer  not null references przejazdy_sklad,
    id_wagonu                 integer  not null references wagony,
    nr_wagonu                 smallint not null
);

create table bilety_jednorazowe_zamowienia
(
    id_bilety_jednorazowe_zamowienia serial primary key,
    id_zamowienia                    integer references zamowienia,
    timestamp_kupna                  timestamp    not null,
    timestamp_zwrotu                 timestamp check ( timestamp_kupna < timestamp_zwrotu ),
    id_ulgi                          integer not null references ulgi,
    id_rabatu                        integer references rabaty
);

create table bilety_jednorazowe
(
    id_biletu_jednorazowego          serial primary key,
    id_bilety_jednorazowe_zamowienia integer  not null references bilety_jednorazowe_zamowienia,
    id_przejazdu                     integer  not null references przejazdy,
    od_stacji                        smallint not null,
    do_stacji                        smallint not null check ( od_stacji < do_stacji ),
    nr_wagonu                        smallint not null,
    nr_miejsca                       smallint not null,
    id_szczegolow                    smallint not null references szczegoly_biletu
);
create index on stacje_posrednie using hash(id_trasy);

create index on stacje_posrednie using btree(numer_stacji);