#!/bin/bash
sudo -u postgres psql -c "drop database if exists koleo;"
sudo -u postgres psql -c "drop user if exists koleo;"
