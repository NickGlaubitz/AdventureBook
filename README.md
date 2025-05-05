# AdventureBook

**Eine interaktive Kinderbuch-App mit KI-generierten Geschichten**

AdventureBook ist eine Android-App, die personalisierte, interaktive Geschichten für Kinder erstellt. 
Mithilfe von OpenAI werden Texte und Bilder generiert, die auf den Vorlieben des Nutzers basieren. 
Kinder können ihre eigene Geschichte konfigurieren, fortsetzen und speichern 
– alles mit einer modernen, benutzerfreundlichen UI basierend auf Jetpack Compose.

## Funktionen

- **Personalisierung**: Erstelle einen Avatar mit Name, Geschlecht und Alter im Onboarding.
- **Geschichten erstellen**: Wähle Typ, Thema, Welt und Nebencharaktere, um eine einzigartige Geschichte zu generieren.
- **Interaktivität**: Am Ende jeder Geschichte gibt es Fortsetzungsoptionen, die Kinder auswählen können, um die Story weiterzuführen – inklusive neuer Bilder!
- **Lokale Speicherung**: Geschichten werden mit Room lokal gespeichert und können in der Bibliothek angezeigt, fortgesetzt oder gelöscht werden.
- **UI**: Dunkel-Theme mit dynamisch anpassbaren Textfeldern, Cards für Optionen und einer pulsierenden Ladeanimation.

## Technologien

- **Kotlin**: Hauptsprache für die Entwicklung.
- **Jetpack Compose**: Moderne UI mit dynamischer Höhe und Animationen.
- **Room**: Lokale Datenbank für Geschichten und Avatare.
- **Koin**: Dependency Injection für saubere Architektur.
- **Retrofit**: API-Calls zu OpenAI für Text- und Bildgenerierung.
- **Coil**: Asynchrones Laden von Bildern.
- **OpenAI API**: Generierung von Geschichten und Illustrationen.
- **MVVM**: Architektur mit ViewModels für Datenverwaltung.

## Installation

1. **Repository klonen**:
   ```bash
   git clone https://github.com/[dein-username]/AdventureBook.git

2. **API-Key hinzufügen**
   - Erstelle eine Datei local.properties im Root-Verzeichnis
   - Füge einen OpenAI API-Key hinzu
     ```bash
     OPENAI_API_KEY=dein-api-key-hier

3. **Projekt bauen**
   - Öffne das Projekt in ANdroid Studio
   - Synchronisiere das Projekt mit Gradle und baue es

4. **App starten**
   - Verbinde ein Gerät oder Emulator und starte die App

## Verwendung

1. **Onboarding:** Erstelle einen Avatar
2. **Home:** Konfiguriere eine Geschichte und generiere sie
3. **Story:** Lies die Geschichte, blättere durch Absätze und wähle am Ende eine Fortsetzung
4. **Library:** Sieh dir gespeicherte Geschichten an, setze sie fort oder lösche sie

## Screenshots
![Onboarding](app/src/main/res/drawable/screenshot_onboarding.png|width=100) ![Home](app/src/main/res/drawable/screenshot_home.png|width=100) ![Story](app/src/main/res/drawable/screenshot_story.png|width=100)



## Architektur
- **Screens:** OnBoardingScreen, HomeScreen, StoryScreen, LibraryScreen, StoryDetailScreen
- **ViewModels:** StoryViewModel für Geschichten, AvatarViewModel für Avatare
- **Repositories:** StoryRepo und AvatarRepo für Datenbankzugriff
- **Services:** OpenAiService für API-Calls




