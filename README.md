# Student Management System

A Java console-based Student Management System with:

- Admin authentication (PBKDF2 password hashing)
- Add, update, delete, search, sort students
- Add admins
- File persistence
- Secure login system

## 🔐 Security
Passwords are hashed using PBKDF2WithHmacSHA256 with:
- 100,000 iterations
- 256-bit key length
- Unique salt per user

## 🛠 Technologies Used
- Java
- IntelliJ IDEA
- File I/O
- PBKDF2 Password Hashing

## 🚀 How to Run
1. Clone the repository
2. Open in IntelliJ
3. Run `StudentApp.java`

## 📌 Author
Griffins Kyei