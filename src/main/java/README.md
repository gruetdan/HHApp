# Haushalts-Hub – Modularbeit ZHAW

##  Projektbeschreibung

**Haushalts-Hub** ist eine Anwendung zur Verwaltung von Ausgaben für Einzelpersonen und Gruppen (z. B. WGs, Reisen).  
Die Anwendung besteht aus einer JavaFX-Desktop-App und einer Spring Boot-basierten REST-API mit Web-Frontend.  
Ziel war es, eine lokal ausführbare, erweiterbare und datenschutzfreundliche Lösung zu entwickeln – ohne Cloudbindung.

## Funktionen

- Ausgaben erfassen, bearbeiten und löschen
- Gruppen erstellen, Mitglieder verwalten
- automatische Saldenberechnung pro Person
- Datenanzeige und Bearbeitung über JavaFX oder Browser
- REST-API zur externen Anbindung
- persistente Datenspeicherung mit H2-Datenbank

## Technologien

| Bereich           | Technologie            |
|-------------------|------------------------|
| Backend           | Java, Spring Boot, REST |
| Frontend (Desktop)| JavaFX, FXML           |
| Frontend (Web)    | HTML, CSS, JavaScript  |
| Datenhaltung      | H2-Datenbank, Spring Data JPA |
| Projektstruktur   | Maven, MVC             |

##  Projekt starten

###  JavaFX-Variante
```bash
# In IntelliJ oder via Terminal:
mvn javafx:run
