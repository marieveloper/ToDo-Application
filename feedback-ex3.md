# Bewertung des 3. Arbeitsblatts

> Das Arbeitsblatt besteht aus 30 Punkten, von denen 15 zum Bestehen notwendig sind.

## Übersicht der erreichten Punkte

| Kriterium                     | erreichte Punkte / maximale Punkte |
| ----------------------------- | ---------------------------------- |
| Frontend-Funktionalität       | 21.5 / 22                            |
| Usability                     | 3 / 3                             |
| Repository, Config und README | 5 / 5                             |
| **Summe**:                    | 29 / 30                            |
| **Bestanden**:                | ja                          |

## Funktionalität des Frontends (21.5 / 22)
> Anforderungen für maximale Punktzahl: Frontend hat alle geforderten Views / View-Elemente mit korrekter Funktionalität, Kommunikation mit der API, Browser-Console (F12) enthält keine Fehler

### Bewertung

- [x] (8 / 8 Punkte) View zur Verwaltung von `Assignees`
  - [x] (1 Punkt) View-Elemente für `GET /assignees`
  - [x] (1 Punkt) View-Elemente für `GET /assignees/$id`
  - [x] (2 Punkte) View-Elemente für `POST /assignees`
  - [x] (3 Punkte) View-Elemente für `PUT /assignees/$id`
  - [x] (1 Punkt) View-Elemente für `DELETE /assignees/$id`
- [x] (10.5 / 11 Punkte) View zur Verwaltung von `ToDos`
  - [x] (1 Punkt) View-Elemente für `GET /todos`
  - [x] (1 Punkt) View-Elemente für `GET /todos/$id` - Description wird nicht angezeigt, wenn Due-Date null, dann wird 1.1.1970 angezeigt
  - [x] (2 Punkte) View-Elemente für `POST /todos` - Description ist nicht optional, sollte es aber
  - [x] (2 Punkte) View-Elemente für `PUT /todos/$id` - Meckern auf sehr hohem Niveau: Wenn ich Assignees updaten möchte, sollte die vorherige Liste von bereits zugewiesenen Assignees vorausgewählt sein, zudem Due-Date nicht löschbar
  - [x] (1 Punkt) View-Elemente für `DELETE /todos/$id`
  - [x] (1 Punkt) Einfaches Abhaken von `ToDos` möglich
  - [x] (1 Punkt) Abgehakte `ToDos` sind getrennt von offenen
  - [ ] (1 Punkt) `ToDos` sind filterbar nach `title` - kein Filter für finished Todos -0.5P
  - [x] (1 Punkt) `ToDos` sind sortierbar nach `title` und `dueDate`
- [x] (2 Punkte) HTTP-Kommunikation mit dem gestarteten Backend (`api`) funktioniert für einen Teil der Ressourcen (3-4 Ressourcen: 1 Punkt, ab 5 Ressourcen: 2 Punkte)
- [x] (1 Punkt) Browser-Console (F12) zeigt keine JS Errors an

## Usability (3 / 3)

- [x] (1 Punkt) Gängige UI Components werden passend eingesetzt (z.B. Tables, MultiSelects, DatePicker, etc.)
- [x] (1 Punkt) UI ist verständlich und stellt notwendige Informationen effizient bereit (z.B. wenige Klicks notwendig)
- [x] (1 Punkt) Terminologie, Farben und Layout sind konsistent

## Repository, Config und README (4.5 / 5)

- [x] (1 Punkt) Repository-Struktur folgt dem Beispielprojekt (`api` und `frontend` auf höchster Ebene)
- [x] (1 Punkt) Keine IDE-spezifischen Dateien (z.B. `.idea`, `.vscode`, etc.) oder Binaries / Libraries (z.B. `target`, `node_modules`, etc.) im Repository
- [x] (1 Punkt) Git tag `v0.3` vorhanden
- [x] (1 Punkt) DB- und API-Konfiguration entspricht dem Beispielprojekt (keine Änderungen für Korrektur nötig)
- [ ] (0,5 Punkte) Keine Dummy-Dateien oder irrelevanter Text aus dem Beispiel-Projekt mehr in `frontend` - `HelloWorld.vue` nocht vorhanden
- [x] (0,5 Punkte) README in `frontend` enthält klare, IDE-unabhängige Anweisungen zur Ausführung der Applikation
