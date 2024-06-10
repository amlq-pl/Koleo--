#!/bin/bash

sudo -u postgres psql -c "SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'koleo_db'
  AND pid <> pg_backend_pid();"
sudo -u postgres psql -c "drop database if exists koleo_db;"
sudo -u postgres psql -c "drop user if exists koleo_user;"
sudo -u postgres psql -c "create user koleo_user with password 'koleo';"
sudo -u postgres psql -c "alter user koleo_user with superuser;"
sudo -u postgres psql -c "grant pg_read_server_files to koleo_user with admin option;"
sudo -u postgres psql -c "create database koleo_db;"
sudo -u postgres psql -c "grant all privileges on database koleo_db to koleo_user;"

export PGPASSWORD="koleo"

psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "create.sql"

data/load_data.sh

psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "afterLoadingData.sql"

unset PGPASSWORD