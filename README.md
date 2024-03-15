# ChatVerse - Komunikator Internetowy z Wykrywaniem Toksycznych Wiadomości

![ChatVerse Logo](https://avatars.githubusercontent.com/u/162829883?s=200&v=4)

## Projekt i Implementacja

- **Data podjęcia tematu projektu:** 08.03.2024
- **Skład zespołu:**
  - Olaf Bykowski (Scrum Master)
  - Rafał Majewski
  - Karol Jarosz
  - Grzegorz Dziedzic

## Założenia Projektu

Projekt ChatVerse zakłada stworzenie komunikatora internetowego umożliwiającego użytkownikom bezpieczną interakcję poprzez wykrywanie toksycznych treści w wiadomościach. Poniżej przedstawiono główne funkcjonalności projektu:

- **Logowanie Google OAuth:** Użytkownicy mogą zalogować się do aplikacji za pomocą swojego konta Google, co zapewnia wygodę i bezpieczeństwo procesu logowania.
- **Integracja Profilu Google:** Aplikacja będzie integrować dane profilowe użytkowników z ich kontami Google, takie jak imię i nazwisko, co pozwoli na bardziej personalizowaną interakcję.
- **Globalny Chat Tekstowy:** Umożliwia użytkownikom komunikację tekstową w czasie rzeczywistym z innymi użytkownikami.
- **Historia Chatu dla Admina:** System będzie zapisywać historię czatu, co umożliwi administratorom monitorowanie i analizowanie interakcji użytkowników.
- **Statystyki Użytkowników:** Zapewniona będzie możliwość wyświetlenia top 10 użytkowników pod względem toksyczności ich wiadomości.
- **Wykrywanie Toksyczności:** System będzie analizować wiadomości i na podstawie ustalonych progów identyfikować treści uznawane za toksyczne.
- **Filtrowanie Toksycznych Wiadomości:** Zaimplementowana zostanie funkcjonalność filtrowania toksycznych wiadomości, aby zapobiec negatywnym interakcjom.
- **Uproszczona Wersja Panelu Admina:** Zapewniony będzie panel administracyjny umożliwiający prostą obsługę aplikacji przez administratorów.
- **Banowanie Toksycznych Użytkowników:** Administratorzy będą mieli możliwość zablokowania dostępu do aplikacji dla użytkowników generujących toksyczne treści.
- **Symulowanie Obsługi Płatności:** Dodana zostanie funkcjonalność symulowania obsługi płatności za przywrócenie dostępu do konta, co może pomóc w kontroli zachowań użytkowników.

## Instrukcje Instalacji i Uruchomienia

1. Sklonuj repozytorium na swój lokalny komputer.
2. Zainstaluj niezbędne zależności przy użyciu polecenia `gradlew build`.
3. Skonfiguruj połączenie z bazą danych oraz integrację z usługą Google OAuth.
4. Uruchom aplikację za pomocą polecenia `docker-compose up --build`.
5. Aplikacja będzie dostępna pod adresem `http://localhost:8080`.

## Kontrybucje

Zachęcamy do zgłaszania problemów i propozycji poprawek poprzez system zgłaszania problemów (Issues) oraz do przesyłania wniosków o zmiany (Pull Requests).


---
*Autorzy: Olaf Bykowski, Rafał Majewski, Karol Jarosz, Grzegorz Dziedzic*
