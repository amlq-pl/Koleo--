#!/bin/bash
sudo -u postgres psql -c "drop database if exists koleo_db;"
sudo -u postgres psql -c "drop user if exists koleo_user;"
