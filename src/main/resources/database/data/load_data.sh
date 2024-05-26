#!/bin/bash
export PGPASSWORD="koleo"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "klienci.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "konto.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "przewoznicy.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "ulgi.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "rabaty.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "szczegoly_biletu.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "wagony.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "wagony_typy_miejsc.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "stacje.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "trasy.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "stacje_posrednie.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "trasy_przewoznicy.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "przejazdy.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "przejazdy_sklad.sql"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "przejazdy_sklad_czesci.sql"

unset PGPASSWORD

