import os
import random

import dateutil.relativedelta

import params
import datetime as dt
from datetime import datetime

def fetch_random_timestamp(date_format='%Y-%m-%d') -> str:
    start = datetime.strptime(params.start_symulacji, date_format)
    end = datetime.strptime(params.koniec_symulacji, date_format)
    start -= dateutil.relativedelta.relativedelta(months=1)
    end -= dateutil.relativedelta.relativedelta(months=1)

    delta = end - start
    random_days = random.randint(0, delta.days)
    random_date = start + dt.timedelta(days=random_days)

    return random_date.strftime(date_format) + f' {random.randrange(24)}:{random.randrange(60)}:00.000000'

script_path = os.path.dirname(__file__)
script_path = script_path[:-16]
script_path += "/data/zamowienia.sql"

file = open(script_path, 'w')
file.write("copy zamowienia(id_klienta) from stdin delimiter ',';\n")
for i in range(params.liczba_zamowien):
    file.write(f"""{random.randint(1, params.liczba_klientow)},{fetch_random_timestamp()}\n""")
file.close()
