create materialized view stations as
select c.id_przejazdu, c.nazwa_stacji, c.czas_przyjazdu, c.czas_odjazdu
from (select p.id_przejazdu,
             s.nazwa                                                                as nazwa_stacji,
             p.timestamp_przejazdu + coalesce((select sum(coalesce(sp2.czas_przejazdu, '0 minutes')) +
                                                      sum(coalesce(sp2.czas_postoju, '0 minutes'))
                                               from stacje_posrednie sp2
                                               where sp2.id_trasy = sp.id_trasy
                                                 and sp2.numer_stacji <= sp.numer_stacji
                                               group by sp2.id_trasy), '0 minutes') -
             coalesce(sp.czas_postoju, '0 minutes')                                 as czas_przyjazdu,
             p.timestamp_przejazdu + coalesce((select sum(coalesce(sp2.czas_przejazdu, '0 minutes')) +
                                                      sum(coalesce(sp2.czas_postoju, '0 minutes'))
                                               from stacje_posrednie sp2
                                               where sp2.id_trasy = sp.id_trasy
                                                 and sp2.numer_stacji <= sp.numer_stacji
                                               group by sp2.id_trasy), '0 minutes') as czas_odjazdu

      from przejazdy p
               join trasy_przewoznicy tp on tp.id_trasy_przewoznika = p.id_trasy_przewoznika
               join trasy t on tp.id_trasy = t.id_trasy
               join stacje_posrednie sp on t.id_trasy = sp.id_trasy
               join stacje s on sp.id_stacji = s.id_stacji
               join przewoznicy pr on pr.id_przewoznika = tp.id_przewoznika) as c
order by c.id_przejazdu, c.czas_przyjazdu;

insert into uzytkownicy(imie, nazwisko, data_urodzenia, email, nr_telefonu, login, haslo)
values ('Lord', 'Garmadon', '2001-09-11'::date, 'hotuwa@ninjago.pl', '420420420', 'daddy', 1334861830);
insert into uzytkownicy(imie, nazwisko, data_urodzenia, email, nr_telefonu, login, haslo)
values ('Slim', 'Shady', '2000-04-18'::date, 'slim.shady@dr.dre', '696969696', 'slim_shady', -1299522251);
insert into uzytkownicy(imie, nazwisko, data_urodzenia, email, nr_telefonu, login, haslo)
values ('Dua', 'Lipa', '1995-08-22'::date, 'physical@illusion.love', '213701337', 'houdini', 1100073550);

create or replace function correctNullsZamowienia() returns trigger as
$$
begin
    if tg_op = 'INSERT' and new.timestamp_zwrotu is not null then
        raise exception 'timestamp zwrotu jest nullem podczas próby utworzenia biletu';
    end if;
    if tg_op = 'UPDATE' and new.timestamp_zwrotu is null then
        raise exception 'timestamp zwrotu nie może być null podczas próby zwrotu biletu';
    end if;
    return new;
end;
$$ language plpgsql;

create trigger correctNullsInBiletyJednorazoweZamowienia
    before insert or update
    on bilety_jednorazowe_zamowienia
    for each row
execute procedure correctNullsZamowienia();

create trigger correctNullsInBiletyOkresoweZamowienia
    before insert or update
    on bilety_okresowe_zamowienia
    for each row
execute procedure correctNullsZamowienia();