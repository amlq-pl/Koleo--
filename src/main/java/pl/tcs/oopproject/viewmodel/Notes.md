1. Może być nadmiar abstract class i interfejsów, ale już taki urok tego przedmiotu

2. Connection / Trail w nazwie. 
- Station
- DirectConnection - całe połączenie, nawet jeżeli teoretycznie potrzebny jest
tylko wycinek trasy, bo KOLEO oferuje podgląd całej i nie możemy być gorsi, Uzupełnianie
braków to już kwestia pracy z bazą, basic metody zaimplementowane
- Connection - całkowity opis połączenia z punktu A do B, **nadal nie ustaliliśmy co z cenami**
- FindConnection - interface do szukania połączeń, będzie oferował możliwość podglądu tras
tak normalnie, ale też tylko tych bezpośrednich czy najtańszych

3. Carriage
- do opisu wagonu (numer, klasa, etc) są enumy CarriageType i CarriageClassType

4. Order
- BestTrail implementuje interface FindConnection i w założeniu metody tej klasy
będą zwracać szukane listy / sorty
- są braki w sprawie implementacji kosztów
- rodzaje biletów typ jednoprzejazdowy, 3miesieczny itd narazie są traktowane jako String

5. Person / User
- user do logowania, person do rejestrowania się i kupowania biletu bez rejestracji
- one nie są abstract jako tak naprawdę jedyne i docenię opinię czy mam poprawiać to tak, by były
