import os
import random
from collections import defaultdict

import params


def append_new_wagon(skladd):
    newWagon = random.randint(1, params.liczba_roznych_wagonow)
    while newWagon == 26 and 26 in sklad:
        newWagon = random.randint(1, params.liczba_roznych_wagonow)
    skladd.append(newWagon)


def zmien_sklad(sklad):
    if random.randint(0, 1) == 0:
        for delete in range(random.randint(1, 3)):
            if len(sklad) > 5:
                sklad.pop()
    else:
        for delete in range(random.randint(1, 3)):
            append_new_wagon(sklad)


path = os.path.dirname(__file__)
path = path[:-16]

trasy_przewoznicy_path = path + "/data/trasy_przewoznicy.sql"

trasy_path = path + "/data/trasy.sql"

przejazdy_path = path + "/data/przejazdy.sql"

przejazdy_sklad_path = path + "/data/przejazdy_sklad.sql"

przejazdy_sklad_czesci_path = path + "/data/przejazdy_sklad_czesci.sql"

stacje_posrednie_path = path + "/data/stacje_posrednie.sql"

with open(przejazdy_path) as file:
    przejazdy = [line.rstrip() for line in file]

with open(trasy_przewoznicy_path) as file:
    przewoznicy_trasy = [line.rstrip() for line in file]

with open(trasy_path) as file:
    trasyToLiczba = [line.rstrip() for line in file]

with open(stacje_posrednie_path) as file:
    stacje_posrednie = [line.rstrip() for line in file]

tpToTrasy = {}
przejazdyToTP = {}
trasyCount = defaultdict(lambda: 0)

for i in range(1, len(przejazdy)):
    przejazdyToTP[i] = int(przejazdy[i].split(',')[0])

for i in range(1, len(przewoznicy_trasy)):
    tpToTrasy[i] = int(przewoznicy_trasy[i].split(',')[0])

for i in range(1, len(stacje_posrednie)):
    trasyCount[int(stacje_posrednie[i].split(',')[0])] += 1

przejazdy_sklad_file = open(przejazdy_sklad_path, 'w')
przejazdy_sklad_czesci_file = open(przejazdy_sklad_czesci_path, 'w')

przejazdy_sklad_file.write("copy przejazdy_sklad(id_przejazdu, od_stacji, do_stacji) from stdin delimiter ',';\n")
przejazdy_sklad_czesci_file.write(
    "copy przejazdy_sklad_czesci(id_przejazdu_skladu, id_wagonu, nr_wagonu) from stdin delimiter ',';\n")

id_przejazd_sklad_counter = 1

for i in range(1, len(przejazdy)):
    liczbaStacji = int(trasyCount[tpToTrasy[przejazdyToTP[i]]])
    sklad = []
    start = 1
    end = liczbaStacji
    liczbaWagonow = random.randint(8, 12)
    for j in range(liczbaWagonow):
        append_new_wagon(sklad)

        # sprobuj (wpisac sklad na odcinku, potem go zmien)
    while random.uniform(0, 1) < params.szansa_na_zmiane_skladu and (start + 3 < end - 3):
        tmp_end = random.randint(start + 3, end - 3)
        przejazdy_sklad_file.write(f"{i},{start},{tmp_end}\n")
        for wagonInx in range(len(sklad)):
            przejazdy_sklad_czesci_file.write(f"{id_przejazd_sklad_counter},{sklad[wagonInx]},{wagonInx + 1}\n")
        id_przejazd_sklad_counter += 1
        start = tmp_end
        zmien_sklad(sklad)

        # bazowy wpis skladu
    przejazdy_sklad_file.write(f"{i},{start},{end}\n")
    for wagonInx in range(len(sklad)):
        przejazdy_sklad_czesci_file.write(f"{id_przejazd_sklad_counter},{sklad[wagonInx]},{wagonInx + 1}\n")
    id_przejazd_sklad_counter += 1
