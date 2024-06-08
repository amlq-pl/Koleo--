import os

# TODO fix it

path = os.path.dirname(__file__)
path = path[:-16]
bilety_jednorazowe_zamowienia_path = path + "/data/bilety_jednorazowe_zamowienia.sql"
bilety_jednorazowe_path = path + "/data/bilety_jednorazwe.sql"
przejazdy_path = path + "/data/przejazdy.sql"
przejazdy_sklad_path = path + "/data/przejazdy_sklad.sql"
przejazdy_sklad_czesci_path = path + "/data/przejazdy_sklad_czesci.sql"
wagony_path = path + "/data/wagony.sql"

with open(bilety_jednorazowe_zamowienia_path) as file:
    bilety_jednorazowe_zamowienia = [line.rstrip() for line in file]

with open(przejazdy_path) as file:
    przejazdy = [line.rstrip() for line in file]

with open(przejazdy_sklad_path) as file:
    przejazdy_sklad = [line.rstrip() for line in file]

with open(przejazdy_sklad_czesci_path) as file:
    przejazdy_sklad_czesci = [line.rstrip() for line in file]

with open(wagony_path) as file:
    wagony = [line.rstrip() for line in file]

driver = [{}] * len(przejazdy)

for id_przejazdy_sklad_czesci in range(1, len(przejazdy_sklad_czesci)):
    if id_przejazdy_sklad_czesci % 1000 == 0:
        print(id_przejazdy_sklad_czesci)
    splitted = przejazdy_sklad_czesci[id_przejazdy_sklad_czesci].split(',')
    id_przejazdy_skladu = int(splitted[0])
    id_wagonu = int(splitted[1])
    nr_wagonu = int(splitted[2])

    splitted = przejazdy_sklad[id_przejazdy_skladu].split(',')
    id_przejazdu = int(splitted[0])
    start = int(splitted[1])
    end = int(splitted[2])

    for miniPrzejazd in range(start, end):
        if miniPrzejazd not in driver[id_przejazdu]:
            driver[id_przejazdu][miniPrzejazd] = {}
        driver[id_przejazdu][miniPrzejazd][nr_wagonu] = {}
        for miejsce in range(int(wagony[id_wagonu].split(',')[2])):
            driver[id_przejazdu][miniPrzejazd][nr_wagonu][miejsce + 1] = 0

for przeja in driver[1]:
    for wagon in driver[1][przeja]:
        print(przeja, wagon, len(driver[1][przeja][wagon]))

# file = open(bilety_jednorazowe_path, 'w')
# file.write(
#     "copy bilety_jednorazowe(id_bilety_jednorazowe_zamowienia, id_przejazdu, od_stacji, do_stacji, nr_wagonu, nr_miejsca, id_szczegolow) from stdin delimiter ',';\n")
# for i in range(1, len(bilety_jednorazowe_zamowienia)):
#     if i % 1000 == 0:
#         print(i)
#     Losuj = True
#     id_przejazdu, start, end, wagon, miejsce = i, 0, 0, 0, 0
#     while Losuj:
#         Losuj = False
#
#         mx = len(driver[id_przejazdu])
#         start = random.randint(1, mx - 2)
#         end = random.randint(start + 1, mx - 1)
#         minWagon = 999
#         for miniPrzejazd in range(start, end):
#             minWagon = min(minWagon, len(driver[id_przejazdu][miniPrzejazd]))
#         wagon = random.randint(1, minWagon)
#         print(id_przejazdu, start, end, wagon, miejsce)
#         if len(driver[id_przejazdu][start][wagon]) == 0:
#             Losuj = True
#             break
#         miejsce = random.randint(1, len(driver[id_przejazdu][start][wagon]))
#         for miniPrzejazd in range(start, end):
#             if driver[id_przejazdu][miniPrzejazd][wagon][miejsce] == 1:
#                 Losuj = True
#                 break
#
#     file.write(f"{i},{id_przejazdu},{start},{end},{wagon},{miejsce},{random.randint(0, 7)}\n")
#     for miniPrzejazd in range(start, end):
#         driver[id_przejazdu][miniPrzejazd][wagon][miejsce] = 1
# file.close()
