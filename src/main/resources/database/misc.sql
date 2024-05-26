create view stations as
select c.id_przejazdu, c.nazwa, c.numer_stacji, c.czas_przyjazdu, czas_odjazdu
from (select p.id_przejazdu,
             s.nazwa,
             sp.numer_stacji,
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
               join stacje s on sp.id_stacji = s.id_stacji) as c
order by c.id_przejazdu, c.numer_stacji;


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

create or replace function getDirectConnection(id int)
    returns table
            (
                numer_stacji   int,
                nazwa          varchar(30),
                czas_przyjazdu timestamp,
                czas_odjazdu   timestamp
            )
as
$$
declare
    idTrasy       int;
    currTimestamp timestamp;
    x             record;
begin
    create temporary table toReturn
    (
        numer_stacji   int,
        nazwa          varchar(30),
        czas_przyjazdu timestamp,
        czas_odjazdu   timestamp
    );
    select p.timestamp_przejazdu
    into currTimestamp
    from przejazdy p
    where p.id_przejazdu = id;
    select getIDTrasy(id) into idTrasy;
    for x in select * from stacje_posrednie where id_trasy = idTrasy order by numer_stacji
        loop
            currTimestamp := currTimestamp + coalesce(x.czas_przejazdu, interval '0 minutes');
            insert into toReturn
            values (x.numer_stacji,
                    (select s.nazwa
                     from stacje s
                              join stacje_posrednie sp on s.id_stacji = sp.id_stacji
                     where sp.id_stacji_posrednich = x.id_stacji_posrednich),
                    currTimestamp,
                    currTimestamp + coalesce(x.czas_postoju, interval '0 minutes'));
        end loop;
    return query select * from toReturn;
    drop table toReturn;
end;
$$ language plpgsql;