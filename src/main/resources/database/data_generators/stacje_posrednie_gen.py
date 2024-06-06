import calculate_distance
import params
import os
import random


def fetch_random_waiting_time():
    return f"{random.randint(3, 6)} minutes"


def fetch_distance(adr1, adr2):
    dist = calculate_distance.get_distance(adr1, adr2)
    return f"{int(dist / 80)} hours {int((dist % 80) * 60 / 80)} minutes"


stacje_path = os.path.dirname(__file__)
stacje_path = stacje_path[:-16]
stacje_path += "/data/stacje.sql"

stacje_posrednie_path = os.path.dirname(__file__)
stacje_posrednie_path = stacje_posrednie_path[:-16]
stacje_posrednie_path += "/data/stacje_posrednie.sql"

with open(stacje_path) as file:
    stacje = [line.rstrip() for line in file]

stacje = stacje[1:]

file = open(stacje_posrednie_path, 'w')
file.write(
    "copy stacje_posrednie(id_trasy, numer_stacji, id_stacji, czas_postoju, czas_przejazdu) from stdin with (delimiter ',', null '');\n")

for i in range(1, params.liczba_tras + 1):

    trasa = []
    trasySize = int(random.triangular(6, 15))
    for j in range(trasySize):
        stacja = random.choice(stacje)
        while stacja in trasa:
            stacja = random.choice(stacje)
        trasa.append(stacja)

    for j in range(1, trasySize + 1):
        czas_postoju = ""
        czas_przejazdu = ""

        if j != 1 and j != trasySize:
            czas_postoju = fetch_random_waiting_time()

        if j != 1:
            czas_przejazdu = fetch_distance(trasa[j - 2], trasa[j - 1])

        file.write(f"{i},{j},{stacje.index(trasa[j - 1]) + 1},{czas_postoju},{czas_przejazdu}\n")
file.close()
