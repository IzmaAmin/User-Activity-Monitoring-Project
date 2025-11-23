# 🎓 QUICK VIVA REFERENCE GUIDE

## Memorize These 5 Things for Viva!

### 1️⃣ **Application Flow**
```
Start → Login Screen → Authenticate → Dashboard (User/Admin)
```

### 2️⃣ **Authentication Logic**
```java
onLogin():
  - Get username & password from UI
  - Find user in list (O(n) search, ~50 iterations)
  - Check password match
  - If wrong: record failure, lock after 5 attempts
  - If right: open dashboard, reset attempt counter
```

### 3️⃣ **Three Algorithms**

| Algorithm | Complexity | What it does |
|-----------|-----------|-------------|
| Linear Search | O(n) | Find activities by username/IP/action |
| Tim Sort | O(n log n) | Sort activities by timestamp |
| Recursion | O(n) | Detect repeated failed attempts |

### 4️⃣ **Data Structures**
```
ActivityLog: LinkedList (O(1) append)
  ├─ ActivityNode: doubly-linked node
  └─ ActivityRecord: timestamp, user, action, IP, severity

SecurityManager: HashMap (O(1) lookup)
  ├─ Failed attempts per user
  └─ Account lock expiration times

Users: ArrayList of 50 dummy users
Documents: List per user
```

### 5️⃣ **Key Methods to Explain**

```java
// Authentication
LoginController.onLogin(ActionEvent) - Full login validation

// Search (O(n))
SearchingAlgorithms.searchByUsernameOrIp(List, String)

// Sort (O(n log n))
SortingAlgorithms.sortByTimestamp(List<ActivityRecord>)

// Security
SecurityManager.lockAccount(username, days)
SecurityManager.recordFailedAttempt(username)

// Recursion
RecursionDetector.hasRepeatedFailedAttempts()
```

---

## 🔐 Security Features (Must Know!)

| Feature | Details |
|---------|---------|
| **Failed Attempts** | Tracked per user with HashMap |
| **Account Lock** | After 5 failed attempts |
| **Lock Duration** | 3 days (LocalDateTime) |
| **Auto-Unlock** | When expiration time passes |
| **Activity Logging** | Timestamp, User, Action, IP, Severity |
| **Severity Levels** | 0=normal, 1=warning, 2-3=alert, 4=critical |

---

## 🏗️ Architecture Pattern

```
FXML (UI Layout)
    ↓ (loaded by)
Controller (Event Handling)
    ↓ (uses)
Model (Data Objects)
    ↓ (processed by)
Utils (Algorithms)
```

---

## 📋 Admin Dashboard Features

| Button | Action | Algorithm | Time |
|--------|--------|-----------|------|
| Search | Find activities | Linear search | O(n) |
| Sort | Order by time | Collections.sort | O(n log n) |
| View Suspicious | Filter severity ≥2 | Linear filter | O(n) |
| Add User | Create new user | GridPane form | O(1) |

---

## 💻 Build & Run Commands

```bash
# Build project
mvn clean compile -DskipTests

# Full build with JAR
mvn clean package -DskipTests

# Run from Maven
mvn javafx:run

# Run JAR directly
java -jar target/UserActivityMonitoringSystem-1.0-SNAPSHOT.jar
```

---

## 🎯 One-Liners to Memorize

1. **"What is this project?"**  
   User Activity Monitoring System built with Java 21, JavaFX, and Maven

2. **"Why LinkedList for ActivityLog?"**  
   Because O(1) append is faster than ArrayList's O(n) resize

3. **"How many failed attempts to lock?"**  
   5 attempts, lock duration 3 days

4. **"What is admin password?"**  
   SIMI / SIMIGROUP123 (hardcoded)

5. **"How many test users?"**  
   50 dummy users generated on startup

---

## 📊 Complexity Comparison

```
Linear Search:     O(n)        vs    Binary Search: O(log n)
  - But: unsorted list needed    +    Requires sorted list

Insertion Sort:    O(n²)       vs    Tim Sort: O(n log n)
  - Simple but slow              +    Fast, but complex

Single-threaded:   O(1) per     vs    Synchronized: O(1) safe
                   operation          but with lock contention
```

---

## 🔍 File Location Quick Reference

```
src/main/java/com/monitoring/
  ├── LoginController.java       ← MAIN authentication logic
  ├── AdminDashboard.java        ← Admin features (search, sort, stats)
  ├── UserDashboard.java         ← User dashboard
  ├── MainApp.java               ← Entry point
  ├── models/
  │   ├── User.java              ← User model
  │   ├── Document.java          ← Document model
  │   ├── ActivityLog.java       ← LinkedList activity storage ⭐
  │   ├── ActivityNode.java      ← LinkedList node
  │   └── SecurityManager.java   ← Security tracking ⭐
  └── utils/
      ├── SearchingAlgorithms.java    ← O(n) search ⭐
      ├── SortingAlgorithms.java      ← O(n log n) sort ⭐
      ├── RecursionDetector.java      ← Recursion example ⭐
      └── DataGenerator.java          ← 50 dummy users

src/main/resources/
  ├── login.fxml                 ← Login screen UI
  ├── user_dashboard.fxml        ← User dashboard UI
  └── admin_dashboard.fxml       ← Admin dashboard UI
```

---

## ⚡ Quick Problem Solving

**Q: "How to find an activity by username?"**
```java
// Linear search in ActivityLog
List<ActivityRecord> results = new ArrayList<>();
for (ActivityRecord record : activityLog.toList()) {
    if (record.getUser().equalsIgnoreCase(username)) {
        results.add(record);  // O(n) time
    }
}
```

**Q: "How to lock an account?"**
```java
// Security manager with 3-day lock
LocalDateTime lockUntil = LocalDateTime.now().plusDays(3);
securityManager.lockAccount(username, 3);
// Auto-unlocks when expired
```

**Q: "How to show button on keyboard shortcut?"**
```java
// Ctrl+Shift+A reveals admin button
@FXML void onKeyPressed(KeyEvent event) {
    if (event.isControlDown() && event.isShiftDown() 
        && event.getCode() == KeyCode.A) {
        hiddenAdminButton.setVisible(true);
    }
}
```

---

## 🎨 Color Scheme (UI Design)

```
#1a237e = Dark Blue (titles, headers)
#f57c00 = Orange (logout, secondary)
#4caf50 = Green (search, add, positive)
#2196f3 = Blue (sort, info)
#f44336 = Red (suspicious, alert)
#9c27b0 = Purple (admin/management)
#f5f5f5 = Light Gray (background)
#e0e0e0 = Medium Gray (borders)
```

---

## ✅ Checklist Before Viva

- [ ] Understand login flow completely
- [ ] Know all 5 failed attempt locks after 5 tries
- [ ] Explain LinkedList benefits (O(1) append)
- [ ] Explain Linear Search O(n) algorithm
- [ ] Explain Tim Sort O(n log n) algorithm  
- [ ] Know admin credentials (SIMI/SIMIGROUP123)
- [ ] Understand FXML and Controller binding
- [ ] Know Maven build commands
- [ ] Explain security features (tracking, locking)
- [ ] Be ready to trace code execution

---

## 🚀 Final Tips

1. **Read through LoginController.java** - It's the heart of authentication
2. **Draw the login flow diagram** - Practice on paper
3. **Know the algorithms by heart** - SearchingAlgorithms, SortingAlgorithms
4. **Explain security** - Why lock after 5 attempts?
5. **Be specific about complexity** - Always mention O(n), O(n log n), etc.

---

**Good luck with your viva! 🎓**
