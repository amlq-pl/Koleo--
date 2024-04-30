#!/bin/bash
sudo -u postgres psql -c "drop database if exists koleo_db;"
sudo -u postgres psql -c "drop user if exists koleo_user;"

sudo -u postgres psql -c "create user koleo_user with password 'koleo';"
sudo -u postgres psql -c "alter user koleo_user with superuser;"
sudo -u postgres psql -c "grant pg_read_server_files to koleo_user with admin option;"
sudo -u postgres psql -c "create database koleo_db;"
sudo -u postgres psql -c "grant all privileges on database koleo_db to koleo_user;"
export PGPASSWORD="koleo"
psql -h 127.0.0.1  -U "koleo_user" -d "koleo_db" -f "create.sql"
unset PGPASSWORD
