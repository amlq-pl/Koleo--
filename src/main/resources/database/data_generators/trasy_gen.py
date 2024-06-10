import os
import params

script_path = os.path.dirname(__file__)
script_path = script_path[:-16]
script_path += "/data/trasy.sql"

file = open(script_path, 'w')
file.write("copy trasy(id_trasy) from stdin delimiter ',';\n")
for i in range(1, params.liczba_tras + 1):
    file.write(f"""{i}\n""")
file.close()
