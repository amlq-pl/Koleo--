create materialized view stations as
select c.id_przejazdu, c.nazwa_stacji, c.czas_przyjazdu, c.czas_odjazdu
from (select p.id_przejazdu,
             s.nazwa                                                                as nazwa_stacji,
             p.timestamp_przejazdu + coalesce((select sum(coalesce(sp2.czas_przejazdu, '0 minutes')) +
                                                      sum(coalesce(sp2.czas_postoju, '0 minutes'))
                                               from stacje_posrednie sp2
                                               where sp2.id_trasy = sp.id_trasy
                                                 and sp2.numer_stacji < sp.numer_stacji
                                               group by sp2.id_trasy), '0 minutes') as czas_przyjazdu,
             p.timestamp_przejazdu + coalesce((select sum(coalesce(sp2.czas_przejazdu, '0 minutes')) +
                                                      sum(coalesce(sp2.czas_postoju, '0 minutes'))
                                               from stacje_posrednie sp2
                                               where sp2.id_trasy = sp.id_trasy
                                                 and sp2.numer_stacji < sp.numer_stacji
                                               group by sp2.id_trasy), '0 minutes') +
             coalesce(sp.czas_postoju, '0 minutes')                                 as czas_odjazdu

      from przejazdy p
               join trasy_przewoznicy tp on tp.id_trasy_przewoznika = p.id_trasy_przewoznika
               join trasy t on tp.id_trasy = t.id_trasy
               join stacje_posrednie sp on t.id_trasy = sp.id_trasy
               join stacje s on sp.id_stacji = s.id_stacji
               join przewoznicy pr on pr.id_przewoznika = tp.id_przewoznika) as c
order by c.id_przejazdu, c.czas_przyjazdu;


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

create or replace function getNumOfStation(id_przejazduu int,nazwa_stacji varchar) returns int as
$$
begin
    return (select sp.numer_stacji from stacje_posrednie sp join stacje s on sp.id_stacji = s.id_stacji
    join trasy t on sp.id_trasy = t.id_trasy join trasy_przewoznicy on t.id_trasy = trasy_przewoznicy.id_trasy
    join przejazdy p on trasy_przewoznicy.id_trasy_przewoznika = p.id_trasy_przewoznika
    where p.id_przejazdu=id_przejazduu and s.nazwa=nazwa_stacji);
end;
$$ language plpgsql;

create or replace function duplikatyTras() returns trigger as $$
    begin

    end;
    $$

