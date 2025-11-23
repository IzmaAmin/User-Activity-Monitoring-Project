📌 User Activity Monitoring System – GitHub Repository Description

A Java-based security application designed to monitor, record, and analyze user activities inside a system. Built using JavaFX, object-oriented principles, and custom data structures, this project provides a lightweight but effective solution for enhancing security, transparency, and accountability in digital environments.

🔐 Overview

The User Activity Monitoring System is designed to address modern cybersecurity challenges such as unauthorized access, suspicious login attempts, and the lack of real-time monitoring in many systems.
The application includes:

Secure user authentication

A User Dashboard for document access and activity reporting

A powerful Admin Dashboard for log inspection and analytics

A custom doubly linked list for activity logs

Searching, sorting, and pattern detection using DSA concepts

A Security Manager that detects repeated failed logins and locks accounts

This project is ideal for academic learning, demonstrations, or as a foundation for advanced monitoring tools.

🛠️ Key Features
👤 User-Side

Secure login with lockout after 5 failed attempts

Simulated email alert on account lock

Personal dashboard to view/add documents

All actions (login, logout, document actions) are recorded as activity logs

🛡️ Admin-Side

Hidden admin panel (Username: SIMI, Password: SIMIGROUP123)

View system-wide logs with timestamps, IPs, usernames, and severity

Analyze:

Suspicious login attempts

User activity patterns

Failed login frequencies

Search and sort logs using custom algorithms

Navigate logs via doubly linked list

📚 Tech Stack

Java (OOP)

JavaFX (UI Framework)

Custom Data Structures:

Linked List

Doubly Linked List

HashMap

ArrayList

Search & Sorting Algorithms

Recursion for anomaly detection

📈 Performance Highlights

O(1) insertion into activity log (linked list)

O(1) login attempt tracking via HashMap

Sorting logs in O(n log n) using QuickSort/MergeSort

Effective anomaly detection with recursive logic

🧪 Project Outcomes

Successful tracking of user actions

Efficient real-time activity logging

Accurate detection of repeated failed login attempts

Fully functional admin analysis tools

Demonstrates practical knowledge of:

JavaFX

Data structures

OOP

Cybersecurity fundamentals

🚀 Future Improvements

This project provides a solid foundation and can be expanded with advanced features such as:

🔒 Advanced Security

Password hashing (BCrypt/SHA-256)

Two-factor authentication (2FA)

Real-time system notifications

🗄️ Database Integration

Replace file/array-based storage with:

MySQL

SQLite

MongoDB

Persistent activity logs

Better scalability

📊 Enhanced Admin Tools

Data visualization dashboards

Real-time suspicious activity alerts

Export logs as PDF/CSV

Role-based access controls (RBAC)

🔌 System Integration

Integration with enterprise Active Directory / LDAP

API layer for connecting with other security tools

💻 UI/UX Improvements

Modern UI with CSS styling

Dark/Light mode support

Responsive layouts

📄 References

Oracle JavaFX Documentation

Data Structures & Algorithms in Java – Goodrich

Cisco Cybersecurity Essentials

Oracle Java SE API Documentation
