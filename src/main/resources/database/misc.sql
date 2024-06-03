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
