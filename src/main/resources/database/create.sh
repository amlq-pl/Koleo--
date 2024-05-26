#!/bin/bash
sudo -u postgres psql -c "SELECT pg_terminate_backend(pg_stat_activity.pid)
FROM pg_stat_activity
WHERE pg_stat_activity.datname = 'koleo_db'
  AND pid <> pg_backend_pid();
drop database if exists koleo_db;
drop user if exists koleo_user;
create user koleo_user with password 'koleo';
alter user koleo_user with superuser;
grant pg_read_server_files to koleo_user with admin option;
create database koleo_db;
grant all privileges on database koleo_db to koleo_user;"
export PGPASSWORD="koleo"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "create.sql"
unset PGPASSWORD
