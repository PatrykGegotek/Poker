# Poker in Terminal

Aby uruchomić grę należy:
- uruchomić poker-server/target/poker-server-1.0-SNAPSHOT-jar-with-dependencies.jar
- wpisać ilość graczy (2-4)
- uruchomić poker-client/target/poker-client-1.0-SNAPSHOT-jar-with-dependencies.jar tyle razy ilu jest graczy

Gra składa się z następujących etapów:
- Gracze podają swoje imiona
- Graczom pokazane zostają ich karty
- Zostaje pobrana początkowa suma pieniędzy od graczy
- Gra pyta kolejnych graczy czy chcą (y) czy też nie (n) rozpocząć pierwszą licytację
- Jeśli, któryś z graczy podbije stawkę reszta musi mu dorównać lub przebić kwotę. W przeciwnym razie odpada z rundy.
- Licytacja kończy się, gdy wszyscy aktywni gracze wyrównają stawkę
- Gra pyta się aktywnych graczy, czy chcą wymienić swoje karty (jeśli tak, to które)
- Następuje druga tura licytacji, która wygląda tak samo jak poprzednia
- Wyłożone zostają wszystkie karty i serwer sprawdza, który z graczy ma najsilniejszy zestaw. Ten, który wygra, zgarnia wszystkie pieniądze z puli
- Należy podać serwerowi informację, czy rozpocząć kolejną turę (y) czy też zakończyć grę (n)
- Gra zostaje zakończona, gdy wszyscy poza jednym graczem nie mają możliwości wpłaty stawki początkowej
