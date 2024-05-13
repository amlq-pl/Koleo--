from geopy.geocoders import Nominatim
from math import radians, sin, cos, sqrt, atan2


coordinates_map={}

def get_coordinates(address):
    if address in coordinates_map:
        return coordinates_map.get(address)
    geolocator = Nominatim(user_agent="abcd")
    location = geolocator.geocode(address)
    coordinates_map[address]=location.latitude, location.longitude
    if location:
        return location.latitude, location.longitude
    else:
        return None


def haversine(lat1, lon1, lat2, lon2):
    """
    Calculate the great circle distance between two points
    on the earth (specified in decimal degrees)
    """
    # Convert latitude and longitude from degrees to radians
    lat1, lon1, lat2, lon2 = map(radians, [lat1, lon1, lat2, lon2])

    # Haversine formula
    dlat = lat2 - lat1
    dlon = lon2 - lon1
    a = sin(dlat / 2) ** 2 + cos(lat1) * cos(lat2) * sin(dlon / 2) ** 2
    c = 2 * atan2(sqrt(a), sqrt(1 - a))
    distance = 6371 * c  # Radius of Earth in kilometers
    return distance


def get_distance(address1, address2):
    coordinates1 = get_coordinates(address1)
    coordinates2 = get_coordinates(address2)
    odl = haversine(coordinates1[0], coordinates1[1], coordinates2[0], coordinates2[1])
    return int(odl * 1.1)
