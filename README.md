# Imposter Party Suite

Offline-Party-Spiele-App für 3–15 Spieler mit drei Modi: Imposter, Undercover und Spyfall. Ein Gerät wird herumgereicht (Pass-and-Play). Keine Werbung, kein Tracking, keine Berechtigungen.

## Download

📥 Die aktuelle APK findest du unter [Releases](../../releases).

## Screenshots

| |
|---|
| ![Screenshot](Screenshot_20260825-151925_Imposter.png) |

## Features

- Hold-to-Reveal Mechanik – Rolle nur sichtbar, solange gedrückt
- 16 Kategorien (Imposter-Modus)
- 6×12 Wortpaare (Undercover-Modus)
- 38 Orte (Spyfall-Modus)
- Konfigurierbare Imposter-Anzahl
- Dark Mode
- Letzte Gruppe wird gespeichert
- Spielregeln direkt in der App erklärt

## Datenaustausch

Diese App funktioniert komplett offline ohne Datenaustausch. Sie ist Teil einer App-Familie, deren andere Mitglieder Nextcloud (WebDAV) für den Datenaustausch nutzen. Zukünftig könnten auch andere Services wie Syncthing oder generisches WebDAV zum Einsatz kommen.

## Tech-Stack

- Kotlin
- Jetpack Compose
- Material3
- DataStore
- Navigation Compose

## Entwicklung

Diese App wurde größtenteils mit einem KI Coding-Agenten entwickelt.

Wenn du dich nicht mit Android-App-Entwicklung auskennst, ist ein Coding-Agent wie
[Kiro](https://kiro.dev) (freies Kontingent verfügbar) oder [OpenCode](https://opencode.ai)
vermutlich die einfachste Art, die App weiterzuentwickeln oder zu bauen.

**Beispiel-Prompt:**

> Schau dir mal dieses Android-App-Projekt an und schaffe die Voraussetzungen für den
> Bau einer APK-Datei (Android App). Baue mir anschließend die APK-Datei.

Wenn du – genau wie ich – gerne mit europäischen Services arbeitest, lohnt sich ein
Blick auf [OpenCode](https://opencode.ai) und den französischen LLM-Provider
[Eden AI](https://www.edenai.co).

### Build

```bash
./gradlew assembleDebug
```

## Unterstützung

☕ [Buy Me a Coffee](https://buymeacoffee.com/asparagus11)

## Lizenz

MIT License – siehe [LICENSE](LICENSE)

## Kontakt

Wenn du die App weiterentwickeln oder forken möchtest, freue ich mich über eine kurze Info an thomas.ad.meyer@gmail.com
