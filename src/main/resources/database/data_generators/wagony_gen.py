import os

wagony_path = os.path.dirname(__file__)
wagony_path = wagony_path[:-16]
wagony_path += "/data/wagony.sql"
wagony = open(wagony_path, 'w')

wagony_typy_miejsc_path = os.path.dirname(__file__)
wagony_typy_miejsc_path = wagony_typy_miejsc_path[:-16]
wagony_typy_miejsc_path += "/data/wagony_typy_miejsc.sql"
wagony_typy_miejsc = open(wagony_typy_miejsc_path, 'w')

wagony.write("copy wagony(typ_wagonu, klasa, liczba_miejsc) from stdin delimiter ',';\n")
wagony_typy_miejsc.write(
    "copy wagony_typy_miejsc(id_wagonu, miejsce_mod, typ_miejsca) from stdin delimiter ',';\n")

wagony_counter = 1

typy_wagonow = ["Przedziałowy 8-osobowy", "Przedziałowy 6-osobowy", "Bezprzedziałowy", "Sypialny", "Restauracyjny"]
for i in range(24, 41, 8):
    str = f"""{typy_wagonow[0]},{2},{i}\n"""
    wagony.write(str)
    wagony_typy_miejsc.write(f"""{wagony_counter},{1},{"Okno"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{2},{"Środek"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{3},{"Środek"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{4},{"Korytarz"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{5},{"Korytarz"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{6},{"Środek"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{7},{"Środek"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{0},{"Okno"}\n""")
    wagony_counter += 1

for i in range(24, 43, 6):
    str = f"""{typy_wagonow[1]},{2},{i}\n"""
    wagony.write(str)
    wagony_typy_miejsc.write(f"""{wagony_counter},{1},{"Okno"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{2},{"Środek"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{3},{"Korytarz"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{4},{"Korytarz"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{5},{"Środek"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{0},{"Okno"}\n""")
    wagony_counter += 1

for i in range(40, 81, 8):
    str = f"""{typy_wagonow[2]},{2},{i}\n"""
    wagony.write(str)
    wagony_typy_miejsc.write(f"""{wagony_counter},{1},{"Okno"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{2},{"Środek"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{3},{"Środek"}\n""")
    wagony_typy_miejsc.write(f"""{wagony_counter},{0},{"Okno"}\n""")
    wagony_counter += 1

wagony.write(f"""{typy_wagonow[4]},{1},{0}\n""")
wagony_counter += 1
wagony.write(f"""{typy_wagonow[3]},{1},{30}\n""")
wagony_counter += 1
wagony.close()
wagony_typy_miejsc.close()
