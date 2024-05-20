import os
import random
import params
from collections import defaultdict

script_path = os.path.dirname(__file__)
script_path = script_path[:-16]
script_path += "/data/trasy_przewoznicy.sql"

vis = defaultdict(lambda: [])
file = open(script_path, 'w')
file.write("copy trasy_przewoznicy(id_trasy, id_przewoznika) from stdin delimiter ',';\n")
for i in range(params.liczba_tras_przewoznikow):
    id_trasy = random.randint(1, params.liczba_tras)
    id_przewoznika = random.randint(1, params.liczba_przewoznikow)
    while id_przewoznika in vis[id_trasy]:
        id_trasy = random.randint(1, params.liczba_tras)
        id_przewoznika = random.randint(1, params.liczba_przewoznikow)
    vis[id_trasy].append(id_przewoznika)
    file.write(f"""{id_trasy},{id_przewoznika}\n""")
file.close()
