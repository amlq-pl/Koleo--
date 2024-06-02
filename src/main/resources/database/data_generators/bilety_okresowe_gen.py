import os
import random

import dateutil.relativedelta

import params
import datetime as dt
from datetime import datetime


def cmp_timestamps(ts1, ts2):
    timestamp_format = "%Y-%m-%d %H:%M:%S.%f"
    timestamp1 = datetime.strptime(ts1, timestamp_format)
    timestamp2 = datetime.strptime(ts2, timestamp_format)
    return timestamp1 <= timestamp2


def fetch_random_timestamp(date_format='%Y-%m-%d') -> str:
    start = datetime.strptime(params.start_symulacji, date_format)
    end = datetime.strptime(params.koniec_symulacji, date_format)
    start -= dateutil.relativedelta.relativedelta(months=1)
    end -= dateutil.relativedelta.relativedelta(months=1)

    delta = end - start
    random_days = random.randint(0, delta.days)
    random_date = start + dt.timedelta(days=random_days)

    return random_date.strftime(date_format) + f' {random.randrange(24)}:{random.randrange(60)}:00.000000'


path = os.path.dirname(__file__)
path = path[:-16]
bilety_okresowe_zamowienia_path = path + "/data/bilety_okresowe_zamowienia.sql"
bilety_okresowe_path = path + "/data/bilety_okresowe.sql"
cennik_path = path + "/data/cennik_biletow_okresowych.sql"

with open(bilety_okresowe_zamowienia_path) as file:
    bilety_okresowe_zamowienia = [line.rstrip() for line in file]

with open(cennik_path) as file:
    cennik_path = [line.rstrip() for line in file]

file = open(bilety_okresowe_path, 'w')
file.write(
    "copy bilety_okresowe(id_bilety_okresowe_zamowienia, timestamp_od, id_typ_biletu) from stdin delimiter ',';\n")
for i in range(1, len(bilety_okresowe_zamowienia)):
    mn = bilety_okresowe_zamowienia[i].split(',')[1]
    ts = fetch_random_timestamp()
    while not cmp_timestamps(ts, mn):
        ts = fetch_random_timestamp()
    file.write(f"{i},{ts},{random.randint(1, len(cennik_path) - 1)}\n")
file.close()
