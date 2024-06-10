import os
import random
import datetime as dt
from datetime import datetime
import params

nazwy = [
    'Perełka', 'Płotka', 'Lambert', 'Brunhilda', 'Balladyna', 'Frodo', 'Lloyd', 'Megatron', 'Mojżesz', 'Dunajec',
    'Skywalker', 'Houdini', 'DeBruyne', 'Ibarbo', 'VanDerWaerden', 'VanDyck', 'Chopin', 'Alonso', 'Silverhand', 'Zuber',
    'Miś', 'Kiler',
    'Bareja', 'Zirael', 'Słowik', 'Vega', 'Escobar', 'Alcapone', 'Sigma', 'Gigachad', 'Brzęczyszczykiewicz', 'Torvalds',
    'Carmach', 'Devris', 'Nolan',
    'Barbie', 'Muszelka', 'Kleopatra', 'Midas', 'Verstappen', 'Marsylianka', 'Erike', 'Napoleon', 'Sycylijczyk',
    'Robbespierre', 'Danton',
    'Zośka', 'Batory', 'Baryka', 'Tadeusz', 'Makbet', 'Julia', 'Romeo', 'Dalglish', 'Maradona', 'Salanca', 'Fring',
    'Jaskier', 'Talar', 'Vesemir',
    'Triss', 'Vilgefortz', 'Cahir', 'Emhyr', 'Rience', 'Regis', 'Milva', 'Eskel', 'Organa', 'Malak', 'Sidous', 'Dokoo',
    'Plagueis', 'Tyson',
    'Linkiewicz', 'Jumper', 'Freeman', 'Gosling', 'Reynolds', 'Venus', 'Merkury', 'Gyllenhaal', 'Pattison', 'Gertruda',
    'Beskidy', 'Łomnica',
    'Wołowiec', 'Grześ', 'Amadablam', 'Gasherbrum', 'Songbird', 'Eminem', 'Bambi', 'Bedoes', 'Tiger', 'Hemingway',
    'MonaLisa', 'Dijkstra', 'Kai', 'Cole', 'Zayne', 'Jay', 'Marinette', 'Lupin', 'Robot', 'Bereze', 'Spinjitsu',
    'Batman', 'Wojtyła', 'Kaladin', 'Ali', 'Alia', 'Dżesika', 'Pandera', 'Bogdańska', 'Maurycy', 'Jarowid', 'Mieszko',
    'Warneńczyk', 'Chrobry', 'Sienkiewicz', 'Jadwiga', 'Zawisza', 'Gandalf', 'Styles', 'Garmadon', 'Awiator',
    'ChairChallenge', 'SubwaySurfer', 'Bajtek', 'Baranek', 'Blane', 'Sparrow', 'Skowronek', 'Przebiśnieg', 'Cochran',
    'Krzywousty',
    'Chewbacca', 'Drakula', 'Biedronka', 'Guardiola', 'Amadeusz', 'Baggins', 'Saruman', 'Potter', 'Voldemort', 'Molier',
    'Łosza', 'Husaria', 'Luter', 'Samson', 'Syracydes', 'Spirydion', 'Ragnar', 'Thor', 'Zeus', 'Posejdon', 'Ares',
    'Persefona', 'Syzyf', 'Kronos', 'Gaja', 'Reja', 'Artemida', 'Hefajstos', 'Hermes', 'Jackson', 'Chrysomallos',
    'Prometeusz', 'Maggelan', 'DaGama', 'Konfucjusz', 'Uranos'
]


def fetch_random_name() -> str:
    id = ""
    for i in range(5):
        id += str(random.randint(0, 9))
    return random.choice(nazwy) + '-' + id


def fetch_random_date(date_format='%Y-%m-%d') -> str:
    start = datetime.strptime(params.start_symulacji, date_format)
    end = datetime.strptime(params.koniec_symulacji, date_format)

    delta = end - start
    random_days = random.randint(0, delta.days)
    random_date = start + dt.timedelta(days=random_days)

    return random_date.strftime(date_format) + f' {random.randrange(24)}:{random.randrange(60)}:00.000000'


path = os.path.dirname(__file__)
path = path[:-16]
script_path = path + "/data/przejazdy.sql"

trasy_przewoznicy_path = path + "/data/trasy_przewoznicy.sql"

trasy_path = path + "/data/trasy.sql"

stacje_posrednie_path = path + "/data/stacje_posrednie.sql"

with open(trasy_przewoznicy_path) as file:
    trasy_przewoznicy_trasy = [line.rstrip() for line in file]

with open(trasy_path) as file:
    trasy = [line.rstrip() for line in file]

with open(stacje_posrednie_path) as file:
    stacje_posrednie = [line.rstrip() for line in file]

tpToPrzewoznicy = {}
tpToTrasy = {}

trasyToLiczba = [0] * len(trasy)

for i in range(1, len(trasy_przewoznicy_trasy)):
    tpToTrasy[i] = int(trasy_przewoznicy_trasy[i].split(',')[0])
    tpToPrzewoznicy[i] = int(trasy_przewoznicy_trasy[i].split(',')[1])

for i in range(1, len(stacje_posrednie)):
    trasyToLiczba[int(stacje_posrednie[i].split(',')[0])] += 1

file = open(script_path, 'w')
file.write(
    "copy przejazdy(id_trasy_przewoznika, timestamp_przejazdu, koszt_bazowy, czy_rezerwacja_miejsc, nazwa) from stdin delimiter ',';\n")
for i in range(params.liczba_przejazdow):
    id_trasy_przewoznika = random.randint(1, params.liczba_tras_przewoznikow)
    koszt = (float(trasyToLiczba[tpToTrasy[id_trasy_przewoznika]]) * 24 *
             random.uniform(0.8, 1.2) * params.wagi_przewoznikow[tpToPrzewoznicy[id_trasy_przewoznika] - 1])
    file.write(f"""{id_trasy_przewoznika},{fetch_random_date()},{round(koszt, 2)},{'true'},{fetch_random_name()}\n""")

file.close()
