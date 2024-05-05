import random
import os
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
file.write("copy konto(id_konta, login, haslo, id_klienta) from stdin delimiter ',';\n")
counter = 0
for i in range(round(params.general_size / 100)):
    if (5 < random.randint(1, 6)):
        continue
    str = f"""{counter},{fetch_random_login()},{-1480617004},{i}\n"""
    file.write(str)
    counter += 1
file.close()
