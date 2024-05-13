import requests
import os
import random
import api_key
import params


def get_distance(origin, destination):
    url = f"https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins={origin}&destinations={destination}&key={api_key.key}"
    response = requests.get(url)
    data = response.json()

    if data["status"] == "OK":
        distance_text = data["rows"][0]["elements"][0]["distance"]["text"]
        return distance_text
    else:
        return "Error occurred while fetching distance information."


print("Warszawa Kraków " + get_distance("Warszaw", "Kraków"))
