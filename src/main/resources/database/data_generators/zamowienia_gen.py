import os
import random
import params

script_path = os.path.dirname(__file__)
script_path = script_path[:-16]
script_path += "/data/zamowienia.sql"

file = open(script_path, 'w')
file.write("copy zamowienia(id_klienta) from stdin delimiter ',';\n")
for i in range(params.liczba_zamowien):
    file.write(f"""{random.randint(1, params.liczba_klientow)}\n""")
file.close()
