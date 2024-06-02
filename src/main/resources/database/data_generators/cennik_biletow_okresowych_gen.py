import os
import params


def nazwa(i):
    if i == 1:
        return 'dzień'
    return 'dni'


path = os.path.dirname(__file__)
path = path[:-16]
cennik_biletow_okresowych_path = path + "/data/cennik_biletow_okresowych.sql"
przewoznicy_path = path + "/data/przewoznicy.sql"

with open(przewoznicy_path) as file:
    przewoznicy = [line.rstrip() for line in file]

przewoznicy = przewoznicy[1:]

for i in range(len(przewoznicy)):
    przewoznicy[i] = przewoznicy[i][:przewoznicy[i].find(',')]

okresy_waznosci_dni = [1, 2, 3, 7, 14, 30, 60, 90, 180, 365]

file = open(cennik_biletow_okresowych_path, 'w')
file.write("copy cennik_biletow_okresowych(cena_bazowa, okres_waznosci, id_przewoznika, nazwa) from stdin delimiter ',';\n")
for id_przewoznika in range(1, params.liczba_przewoznikow + 1):
    for i in range(len(okresy_waznosci_dni)):
        cena = float(okresy_waznosci_dni[i]) * 20 * params.wagi_przewoznikow[id_przewoznika - 1] * (1 - float(i) / 20)
        cena = cena.__round__(2)
        file.write(f"""{cena},{okresy_waznosci_dni[i]} days,{id_przewoznika},{przewoznicy[id_przewoznika - 1]}-{okresy_waznosci_dni[i]} {nazwa(okresy_waznosci_dni[i])}\n""")
file.close()
