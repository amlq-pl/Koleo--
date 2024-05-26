#!/bin/bash
export PGPASSWORD="koleo"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "klienci.sql
konto.sql
przewoznicy.sql
ulgi.sql
rabaty.sql
szczegoly_biletu.sql
wagony.sql
wagony_typy_miejsc.sql
stacje.sql
trasy.sql
stacje_posrednie.sql
trasy_przewoznicy.sql
przejazdy.sql
przejazdy_sklad.sql
przejazdy_sklad_czesci.sql"

unset PGPASSWORD

