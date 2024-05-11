import os
import random
import params


def fetch_random_polish_name():
    polish_names = [
        "Adam", "Agnieszka", "Aleksander", "Alicja", "Andrzej", "Aneta", "Barbara", "Bartosz", "Beata", "Czesław",
        "Dorota", "Dominik", "Elżbieta", "Ewa", "Filip", "Gabriela", "Grzegorz", "Hanna", "Henryk", "Irena",
        "Jacek", "Joanna", "Jan", "Justyna", "Krzysztof", "Karolina", "Łukasz", "Magdalena", "Marek", "Monika",
        "Michał", "Natalia", "Norbert", "Olga", "Piotr", "Patrycja", "Rafał", "Renata", "Robert", "Sylwia",
        "Tomasz", "Urszula", "Wojciech", "Wioletta", "Zbigniew", "Zofia"
    ]
    return random.choice(polish_names)


def fetch_random_polish_surname():
    polish_surnames = [
        "Nowak", "Kowalski", "Wiśniewski", "Wójcik", "Kowalczyk", "Kamiński", "Lewandowski", "Zieliński",
        "Szymański", "Woźniak", "Dąbrowski", "Kozłowski", "Jankowski", "Mazur", "Wojciechowski", "Kwiatkowski",
        "Krawczyk", "Kaczmarek", "Piotrowski", "Grabowski", "Zając", "Pawłowski", "Michalski", "Król", "Wieczorek",
        "Wróbel", "Jabłoński", "Nowakowski", "Majewski", "Olszewski", "Stępień", "Jaworski", "Malinowski",
        "Adamczyk", "Dudek", "Witkowski", "Walczak", "Rutkowski", "Sikora", "Baran", "Michalak", "Szewczyk",
        "Ostrowski", "Tomaszewski", "Pietrzak", "Marciniak", "Wróblewski", "Zalewski", "Jakubowski", "Lis",
        "Kubiak", "Kucharski", "Zawadzki", "Serafin", "Włodarczyk", "Majchrzak", "Wilczyński", "Szymczak",
        "Kaźmierczak", "Gajewski", "Wesołowski", "Sobczak", "Czarnecki", "Konieczny", "Urbański", "Kaczmarczyk",
        "Stankiewicz", "Stasiak", "Piątek", "Zakrzewski", "Klimek", "Kurowski", "Różycki", "Olszewski",
        "Cieślak", "Łuczak", "Borkowski", "Wrona", "Marcinkowski", "Michałowski", "Szczepański", "Sadowski",
        "Brzeziński", "Sawicki", "Krupa", "Kaczor", "Laskowski", "Makowski", "Ziółkowski", "Przybylski",
        "Domański", "Nowicki", "Borowski", "Piotrowicz", "Mucha", "Wilk", "Śliwiński", "Tomczak", "Kłos",
        "Orłowski", "Sobolewski"
    ]
    return random.choice(polish_surnames)


def fetch_random_domain():
    domains = ["gmail.com", "o2.pl", "wp.pl", "onet.pl", "interia.pl", "yahoo.com", "poczta.fm", "gazeta.pl",
               "outlook.com"]
    return random.choice(domains)


def fetch_random_date():
    start_year = 1945
    end_year = 2000
    year = random.randint(start_year, end_year)
    month = random.randint(1, 12)

    if month in [1, 3, 5, 7, 8, 10, 12]:
        day = random.randint(1, 31)
    elif month in [4, 6, 9, 11]:
        day = random.randint(1, 30)
    else:
        if year % 4 == 0 and (year % 100 != 0 or year % 400 == 0):
            day = random.randint(1, 29)
        else:
            day = random.randint(1, 28)

    return f"""{year}-{month}-{day}"""


def fetch_random_phone_number():
    if 4 < random.randint(1, 5):
        return ""
    phone = ""
    for i in range(9):
        phone += f"""{random.randint(0, 9)}"""
    return phone


def remove_polish_letters(input_string):
    polish_letters = {
        'ą': 'a', 'ć': 'c', 'ę': 'e',
        'ł': 'l', 'ń': 'n', 'ó': 'o',
        'ś': 's', 'ź': 'z', 'ż': 'z',
    }
    modified_string = ''.join(polish_letters.get(char, char) for char in input_string)
    return modified_string


script_path = os.path.dirname(__file__)
script_path = script_path[:-16]
script_path += "/data/klienci.sql"

file = open(script_path, 'w')
file.write("copy klienci(imie,nazwisko,data_urodzenia,email,nr_telefonu) from stdin delimiter ',';\n")
for i in range(params.liczba_klientow):
    name = fetch_random_polish_name()
    surname = fetch_random_polish_surname()
    str = f"""{name},{surname},{fetch_random_date()},{remove_polish_letters((name + surname + "@" + fetch_random_domain()).lower())},{"+48 " + fetch_random_phone_number()}\n"""

    file.write(str)
file.close()
