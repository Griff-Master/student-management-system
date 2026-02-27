# :rocket: Student Management System

A Java console-based Student Management System with:

- :closed_lock_with_key: Admin authentication (PBKDF2 password hashing)
- :heavy_plus_sign: Add students
- :pencil2: Update students
- :x: Delete students
- :mag: Search students
- :arrows_clockwise: Sort students
- :heavy_plus_sign: Add admins
- :file_folder: File persistence
- :door: Secure login system
- :arrows_counterclockwise: Re_login after exit

## :closed_lock_with_key: Security
Passwords are hashed using PBKDF2WithHmacSHA256 with:
- 100,000 iterations
- 256-bit key length
- Unique salt per user

## 🛠 Technologies Used
- Java
- IntelliJ IDEA
- File I/O
- PBKDF2 Password Hashing
- Git & Github

## :arrow_forward: How to Run
1. Clone the repository
    - Copy the URL in the Github
    - Open  IntelliJ
    - Go to Files - New - project - project from version control
    - Paste the URL
    -  clone
2. Run `StudentApp.java`

## :bust_in_silhouette: Author
Griffins Kyei