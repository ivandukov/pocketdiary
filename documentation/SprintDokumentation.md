### Meilenstein: Implement Basic Structures and Landing Pages (1. Sprint)

- SettingsActivity: Eingabefeld für die Uhrzeit (siehe finalen Paperprototypen) zu einem Numberpicker abgeändert, um die Usability zu verbessern
- Datenbank: Room speichert die Einträge und Achievements jeweils lokal auf dem Handy ab
- SettingsActivity und NameActivity auf front-end-Seite fertig 
- Landingpages auf front-end-Seite fertig 
- Restliche Activities angelegt


### Meilenstein: Implement Activity Design and Navigation (2. Sprint)


- Navigation: AppBar implementiert, sodass man mit Buttons navigieren kann (siehe HomeScreenActivity)
- Top Bar Navigation: Einstellungen können ausgewählt werden (Siehe HomeScreenActivity oben rechts)
- Mood and Activity selection: Aktivitäten können ausgewählt werden, Gefühlzustände können ausgewählt werden 


### Meilenstein: Implement RecyclerView and Basic Checks (3. Sprint)


- RecyclerView: Neue Einträge können im Hauptmenü angesehen und gedrückt werden
- Achievements: Achievements können in einer RecyclerView angesehen werden (ausgegraut = Nicht geschafft, bunt = geschafft)
- Navigation: Man kann nur einen Eintrag erstellen, wenn man genau eine Mood ausgewählt hat
- Actions: Pro Eintrag kann man max. 3 Actions auswählen (z.B. Arbeit, Sport, etc.)


### Meilenstein: Refactor Existing Code and Implement Achievements (4. Sprint)

- Benutzername: kann eingestellt und gespeichert werden
- Notification: Man kann einstellen, um wieviel Uhr man eine Benachrichtigung erhalten möchte (Numberpicker) und eine Benachrichtigung erhalten
- Achievements: Werden nun in einem Workmanager im Hintergrund nach Erstellung eines neuen Eintrags geprüft und behandelt
- Home-Screen: Cards der Einträge können geklickt werden und führen den Benutzer zu einer Übersicht dieses Eintrags
- Refactoring: Funktionsauslagerung + Kommentare
- Logo: Ein passendes Logo wurde zu der App designt.


### Meilenstein Bug Fixes and Refactoring (5. Sprint)


- Dark Mode: Die App benutzt die Systemeinstellung des Androidgeräts und ist nun auch im Dark Mode bedienbar
- Eintrags-Übersicht: Einträge können, nachdem sie im Homescreen geklickt wurden, von ihrer jeweiligen Übersicht aus gelöscht werden.
- Tutorial: Wenn die App zum ersten Mal startet, wird der User in einem kleinem Tutorial in die App eingeführt Nach dem Tutorial beginnt die App dann immer im Home Screen, wo die ganzen Einträge zu sehen sind (sortiert nach Erstellungszeitpunkt)
- Landscape Mode bugs gefixt
- Achievements: Anpassungen an die Navigation (Listener entfernt, redundante Aufrufmöglichkeiten)
- Refactoring: Funktionsauslagerung + Kommentare
- Bug fixes: Bottom App Bar, Landscape Mode, Dark Mode, On-delete-cascade für Achievement-Datenbank
- Start/Landing Page: Das Logo der App und dessen Entwicker werden kurz gezeigt, während die App startet.