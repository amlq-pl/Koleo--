import os

wagony_path = os.path.dirname(__file__)
wagony_path = wagony_path[:-16]
wagony_path += "/data/wagony.sql"
wagony_typy_miejsc_path = os.path.dirname(__file__)
wagony_typy_miejsc_path = wagony_typy_miejsc_path[:-16]
wagony_path += "/data/wagony_typy_miejsc.sql"
wagony=open(wagony_path,'w')
wagony_typy_miejsc=open(wagony_typy_miejsc_path,'w')

wagony.write("copy wagony(id_wagonu, typ_wagonu, klasa, liczba_miejsc) from stdin delimiter ',';\n")
wagony_typy_miejsc.write("copy wagony_typy_miejsc(id_wagony_typy_miejsc, id_wagonu, miejsce_mod, typ_miejsca) from stdin delimiter ',';\n")

wagony_counter=0
wagony_typy_miejsc_counter=0

typy_wagonow=["Przedziałowy 8-osobowy","Przedziałowy 6-osobowy","Bezprzedziałowy","Sypialny","Restauracyjny"]
for i in range(40,88,8):
    str=f"""{wagony_counter},{typy_wagonow[0]},{}"""