import os
import random
import string

import params


def fetch_random_login():
    str = ""
    l = random.randint(8, 20)
    for i in range(l):
        str += random.choice(string.ascii_letters)
    return str


script_path = os.path.dirname(__file__)
script_path = script_path[:-16]
script_path += "/data/konto.sql"

file = open(script_path, 'w')
file.write("copy konto(login, haslo, id_klienta) from stdin delimiter ',';\n")
for i in range(params.liczba_klientow):
    if params.liczba_klientow * params.procent_osob_z_kontami <= random.randint(1, 100):
        continue
    file.write(f"{fetch_random_login()},{-1480617004},{i + 1}\n")
file.close()
