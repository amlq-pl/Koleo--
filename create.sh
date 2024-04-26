#!/bin/bash
sudo -u postgres psql -c "create user koleo with password 'koleo';"
sudo -u postgres psql -c "create database koleo;"
sudo -u postgres psql -c "grant all privileges on database koleo to koleo;"
export PGPASSWORD="koleo"
psql -h 127.0.0.1  -U "koleo" -d "koleo" -f "create.sql"
unset PGPASSWORD
