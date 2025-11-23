# User Activity Monitoring System (JavaFX)

This is a minimal JavaFX application that simulates a User Activity Monitoring System with normal users and a hidden admin dashboard. It includes:

- Login UI (normal users)
- Hidden admin login (press Ctrl+Shift+A to reveal)
- Admin dashboard: logs, basic analytics, search & sort
- User dashboard: view/add documents
- Activity log implemented as a doubly linked list

Prerequisites
- Java 11+ (or later) installed
- Maven and JavaFX SDK for your platform (if needed)

Running with Maven

```powershell
mvn clean javafx:run
```

Notes
- Admin credentials: `SIMI` / `SIMIGROUP123`.
- 50 dummy users are generated automatically.
- Failed login behavior: after 3 failed attempts a warning shows; after 5 the account is locked for 3 days (simulated).

If you'd prefer Gradle or a simple compiled run script, I can add that next.
