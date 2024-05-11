import os
import random
import params

script_path = os.path.dirname(__file__)
script_path = script_path[:-16]
script_path += "/data/trasy.sql"

file = open(script_path, 'w')
file.write("copy trasy(ile_stacji) from stdin delimiter ',';\n")
for i in range(params.liczba_tras):
    file.write(f"""{int(random.triangular(6,15))}\n""")
file.close()