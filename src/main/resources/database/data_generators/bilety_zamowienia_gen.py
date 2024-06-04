import os
import random

import dateutil.relativedelta

import params
import datetime as dt
from datetime import datetime

newrabaty = {}


def fetch_random_rabat(date):
    dat = datetime.strptime(date, "%Y-%m-%d %H:%M:%S.%f")
    accepted = []
    for i in list(newrabaty.keys()):
        if newrabaty.get(i)[0] <= dat <= newrabaty.get(i)[1]:
            accepted.append(i)
    if len(accepted) == 0:
        return ""
    return str(random.choice(accepted))


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
bilety_jednorazowe_zamowienia_path = path + "/data/bilety_jednorazowe_zamowienia.sql"
rabaty_path = path + "/data/rabaty.sql"
ulgi_path = path + "/data/ulgi.sql"
zamowienia_path = path + "/data/zamowienia.sql"

with open(rabaty_path) as file:
    rabaty = [line.rstrip() for line in file]

with open(ulgi_path) as file:
    ulgi = [line.rstrip() for line in file]

with open(zamowienia_path) as file:
    zamowienia = [line.rstrip() for line in file]

newzamowienia=[]

zamowienia=zamowienia[1:]

for i in zamowienia:
    newzamowienia.append(i.split(',')[1])

for i in range(1, len(rabaty)):
    data = rabaty[i].split(',')
    newrabaty[i] = [datetime.strptime(data[2], "%Y-%m-%d"), datetime.strptime(data[3], "%Y-%m-%d")]

okresowe = open(bilety_okresowe_zamowienia_path, 'w')
jednorazowe = open(bilety_jednorazowe_zamowienia_path, 'w')


okresowe.write(
    "copy bilety_okresowe_zamowienia(id_zamowienia, timestamp_zwrotu, id_ulgi, id_rabatu) from stdin with (delimiter ',', null '');\n")
jednorazowe.write(
    "copy bilety_jednorazowe_zamowienia(id_zamowienia, timestamp_zwrotu, id_ulgi, id_rabatu)  from stdin with (delimiter ',', null '');\n")

okresoweWrite = False

for i in range(1, params.liczba_zamowien + 1):
    if params.procent_biletow_okresowych >= random.uniform(0, 1):
        okresoweWrite = True
    else:
        okresoweWrite = False
    tsKupna = newzamowienia[i-1]
    for j in range(random.randrange(5)):
        tsZwrotu = ""
        if params.procent_zwroconych_biletow >= random.uniform(0, 1):
            tsZwrotu = fetch_random_timestamp()
            while not cmp_timestamps(tsKupna, tsZwrotu):
                tsZwrotu = fetch_random_timestamp()
        if okresoweWrite:
            okresowe.write(f"{i},{tsZwrotu},{random.randint(1, len(ulgi)-1)},{fetch_random_rabat(tsKupna)}\n")
        else:
            jednorazowe.write(f"{i},{tsZwrotu},{random.randint(1, len(ulgi)-1)},{fetch_random_rabat(tsKupna)}\n")


okresowe.close()
jednorazowe.close()
