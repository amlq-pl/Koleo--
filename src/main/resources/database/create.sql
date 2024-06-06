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
    znizka  integer     not null
);

create table klienci
(
    id_klienta     serial primary key,
    imie           varchar(20) not null,
    nazwisko       varchar(40) not null,
    data_urodzenia date        not null,
    email          varchar(50) not null unique,
    nr_telefonu    varchar(18) unique
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
    id_zamowienia   serial primary key,
    id_klienta      integer   not null references klienci,
    timestamp_kupna timestamp not null
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
    id_trasy integer primary key
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
    cena_bazowa    numeric(6, 2) not null,
    okres_waznosci interval      not null,
    id_przewoznika integer       not null references przewoznicy,
    nazwa          varchar(30)   not null
);

create table bilety_okresowe_zamowienia
(
    id_bilety_okresowe_zamowienia serial primary key,
    id_zamowienia                 integer not null references zamowienia,
    timestamp_zwrotu              timestamp,
    id_ulgi                       integer not null references ulgi,
    id_rabatu                     integer references rabaty
);

create table bilety_okresowe
(
    id_biletu_okresowego          serial primary key,
    id_bilety_okresowe_zamowienia integer references bilety_okresowe_zamowienia,
    timestamp_od                  timestamp not null,
    id_typ_biletu                 integer   not null references cennik_biletow_okresowych
);

create table przejazdy
(
    id_przejazdu          serial primary key,
    id_trasy_przewoznika  integer       not null references trasy_przewoznicy,
    timestamp_przejazdu   timestamp     not null,
    koszt_bazowy          numeric(7, 2) not null,
    czy_rezerwacja_miejsc boolean       not null,
    nazwa                 varchar(30)   not null
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
    timestamp_zwrotu                 timestamp,
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

create table koszty_udogodnien
(
    id_kosztu_udogodnien serial primary key,
    nazwa                varchar(20) not null,
    koszt_procentowy     int         not null,
    data_poczatkowa      date        not null,
    data_koncowa         date        not null check ( data_poczatkowa <= data_koncowa )
);


create index on stacje_posrednie using hash (id_trasy);

create index on stacje_posrednie using btree (numer_stacji);

create index on przejazdy using hash (id_przejazdu);


create or replace function getIDTrasy(id_przejazduu int) returns int as
$$
begin
    return (select t.id_trasy
            from przejazdy p
                     join trasy_przewoznicy tp on p.id_trasy_przewoznika = tp.id_trasy_przewoznika
                     join trasy t on tp.id_trasy = t.id_trasy
            where p.id_przejazdu = id_przejazduu);
end;
$$ language plpgsql;

create or replace function getNumOfStation(id_przejazduu int, nazwa_stacji varchar) returns int as
$$
begin
    return (select sp.numer_stacji
            from stacje_posrednie sp
                     join stacje s on sp.id_stacji = s.id_stacji
                     join trasy t on sp.id_trasy = t.id_trasy
                     join trasy_przewoznicy on t.id_trasy = trasy_przewoznicy.id_trasy
                     join przejazdy p on trasy_przewoznicy.id_trasy_przewoznika = p.id_trasy_przewoznika
            where p.id_przejazdu = id_przejazduu
              and s.nazwa = nazwa_stacji);
end;
$$ language plpgsql;

-- create or replace function correctNullsStacjePosrednie() returns trigger as
-- $$
-- declare
--     lastStation int;
-- begin
--     select count(*) into lastStation from trasy t join stacje_posrednie sp on t.id_trasy = sp.id_trasy where t.id_trasy = new.id_trasy;
--     if new.numer_stacji = 1 then
--         if new.czas_postoju is not null or new.czas_przejazdu is not null then
--             raise exception 'błędny czas przy imporcie danych do tabeli stacje_posrednie';
--         end if;
--     elsif new.numer_stacji = lastStation then
--         if new.czas_postoju is not null or new.czas_przejazdu is null then
--             raise exception 'błędny czas przy imporcie danych do tabeli stacje_posrednie';
--         end if;
--     else
--         if new.czas_postoju is null or new.czas_przejazdu is null then
--             raise exception 'błędny czas przy imporcie danych do tabeli stacje_posrednie';
--         end if;
--     end if;
--     return new;
-- end;
-- $$ language plpgsql;
--
-- create trigger correctNullsInStacjePosrednie
--     before insert
--     on stacje_posrednie
--     for each row
-- execute procedure correctNullsStacjePosrednie();

create or replace function correctNullsBiletyJednorazowe() returns trigger as
$$
begin
    if tg_op = 'INSERT' and new.timestamp_zwrotu is null then
        raise exception 'timestamp zwrotu nie jest nullem podczas próby utworzenia biletu';
    end if;
    if tg_op = 'UPDATE' and new.timestamp_zwrotu is not null then
        raise exception 'timestamp zwrotu nie może być null podczas próby zwrotu biletu';
    end if;
    return new;
end;
$$ language plpgsql;

create trigger correctNullsInBiletyJednorazowe
    before insert or update
    on bilety_jednorazowe
    for each row
execute procedure correctNullsBiletyJednorazowe();

create or replace function discardNullAcc() returns trigger as
$$
begin
    if new.login is null or new.haslo is null then
        return null;
    end if;
    return new;
end;
$$ language plpgsql;

create trigger discardNullAccounts
    before insert
    on konto
    for each row
execute procedure discardNullAcc();

create view uzytkownicy as
select kl.imie, kl.nazwisko, kl.data_urodzenia, kl.email, kl.nr_telefonu, ko.login, ko.haslo
from klienci kl
         join konto ko on kl.id_klienta = ko.id_klienta;

create rule nowyUzytkownik as on insert to uzytkownicy do instead (
    insert into klienci(imie, nazwisko, data_urodzenia, email, nr_telefonu)
    values (new.imie, new.nazwisko, new.data_urodzenia, new.email, new.nr_telefonu);
    insert into konto(login, haslo, id_klienta)
    values (new.login, new.haslo, (select max(id_klienta) from klienci));
    );

create or replace function correctTimestamps() returns trigger as
$$
begin
    if (select z.timestamp_kupna from zamowienia z where z.id_zamowienia = new.id_zamowienia) >=
       new.timestamp_zwrotu then
        raise exception 'błędny czas zwrotu';
    end if;
    return new;
end;
$$ language plpgsql;

create trigger correctTimestampOnZamowieniaJednorazowe
    before update
    on bilety_jednorazowe_zamowienia
    for each row
execute procedure correctTimestamps();

create trigger correctTimestampOnZamowieniaOkresowe
    before update
    on bilety_okresowe_zamowienia
    for each row
execute procedure correctTimestamps();