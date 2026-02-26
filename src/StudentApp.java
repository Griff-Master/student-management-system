import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;
import java.util.Map;
public class StudentApp {
    static Scanner scanner = new Scanner(System.in);
    static StudentService service = new StudentService();
    static Map<String, String> admins = new HashMap<>();
    static final String ADMIN_FILE = "admin.txt";

    public static void main(String[] args) {
        service.loadFromFile();
        while (true) {
            if (!login()) {
                System.out.println("Exiting system.");
                break;
            } else {
                showMenu();
            }
        }
    }
    public static void showMenu(){
boolean running = true;
        while (running) {
            System.out.println("------Student System---------");
            System.out.println("1. Add Student");
            System.out.println("2. View students");
            System.out.println("3. Search student");
            System.out.println("4. Update student");
            System.out.println("5. Delete student");
            System.out.println("6. Exit");
            System.out.println("7. Sort by name or age");
            System.out.println("8. Search by name");
            System.out.println("9. Add new admin");
            System.out.println("------------------------------");
            System.out.print("Enter your choice: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format.");
                continue;
            }
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> service.viewStudents();
                case 3 -> searchStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> {
                    service.saveToFile();
                    System.out.println("Good bye.");

                    running = false;

                }
                case 7 -> {
                    System.out.println("Sort by: ");
                    System.out.println("1. Name");
                    System.out.println("2. Age");
                    int fieldChoice = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Sort order: ");
                    System.out.println("1. Ascending");
                    System.out.println("2. Descending");
                    int orderChoice = scanner.nextInt();
                    scanner.nextLine();
                    boolean ascending = orderChoice == 1;
                    if (fieldChoice == 1) {
                        service.sortByName(ascending);
                    } else if (fieldChoice == 2) {
                        service.sortByAge(ascending);
                    } else {
                        System.out.println("Invalid choice");
                    }
                }
                case 8 -> findByName();
                case 9 -> addAdmin();
            }

        }
    }

    static void addStudent() {
        System.out.print("Enter ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
            return;
        }

        if (id <= 0) {
            System.out.println("ID must be postive.");
            return;
        }
        System.out.print("Enter Student name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Name can't be empty.");
            return;
        }
        System.out.print("Enter student course: ");
        String course = scanner.nextLine();
        if (course.trim().isEmpty()) {
            System.out.println("Course can't be empty.");
            return;
        }
        System.out.print("Enter Student Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        if (age < 5 || age > 100) {
            System.out.println("Age must be between 5 and 100.");
            return;
        }
        service.addStudent(new Student(id, name, course, age));

    }

    static void searchStudent() {
        System.out.print("Enter Student ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
            return;
        }

        if (id <= 0) {
            System.out.println("ID must be postive.");
            return;
        }
        Student s = service.findStudent(id);
        if (s == null) {
            System.out.println("Student not Found.");
            return;
        }
        System.out.println(s.getId() + " | " + s.getName() + " | " + s.getAge());
    }

    static void updateStudent() {
        System.out.print("Enter ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
            return;
        }

        if (id <= 0) {
            System.out.println("ID must be postive.");
            return;
        }
        System.out.print("Enter new Name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Name can't be empty.");
            return;
        }
        System.out.print("Enter student course: ");
        String course = scanner.nextLine();
        if (course.trim().isEmpty()) {
            System.out.println("Course can't be empty.");
            return;
        }
        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        if (age < 5 || age > 100) {
            System.out.println("Age must be between 5 and 100.");
            return;
        }
        service.updateStudent(id, name, course, age);
    }

    static void deleteStudent() {
        System.out.print("Enter ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
            return;
        }

        if (id <= 0) {
            System.out.println("ID must be postive.");
            return;
        }
        service.deleteStudent(id);
    }

    static void findByName() {
        System.out.print("Enter  name to search: ");
        String keyword = scanner.nextLine();
        if (keyword.trim().isEmpty()) {
            System.out.println("keyword can't be empty.");
            return;
        }
        service.findByName(keyword);

    }

    public static void loadAdmins() {
        File file = new File(ADMIN_FILE);
        try {
            if (!file.exists()) {
                try (FileWriter writer = new FileWriter(file)) {
                String salt1 = generateSalt();
                String hash1 =hashPassword("1234", salt1);
                writer.write("admin," + salt1 + "," + hash1 + "\n");
                String salt2 = generateSalt();
                String hash2 = hashPassword("2580" , salt2);
                writer.write("griffins," + salt2 + "," + hash2 + "\n");

                }
                System.out.println("Default admin created.");
            }
            admins.clear();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts.length == 3) {
                       String username = parts[0].trim();
                       String salt = parts[1].trim();
                       String hash = parts[2].trim();
                       admins.put(username, salt + "," + hash);

                        }
                    }
                }
            }
            } catch (Exception e) {
                System.out.println("Error loading admin file: " + e.getMessage());
            }

        }
        public static void addAdmin(){
            System.out.print("Enter username: ");
            String newUsername = scanner.nextLine().trim();
            System.out.print("Enter password: ");
            String newPassword = scanner.nextLine().trim();
            if(newUsername.isEmpty() || newPassword.isEmpty()){
                System.out.println("New username or new password cannot be empty.");
                return;
            }
            if(admins.containsKey(newUsername)){
                System.out.println("Username already exists.");
                return;
            }
            try(FileWriter writer = new FileWriter(ADMIN_FILE, true)){
                String salt = generateSalt();
                String hashed =hashPassword(newPassword,salt);
                writer.write(newUsername + "," + salt +"," + hashed  + "\n");
                admins.put(newUsername, salt + "," + hashed);
                System.out.println("New admin added successfully.");
            }catch(IOException e){
                System.out.println("Error adding new admin: " + e.getMessage());
            }
        }
        public static boolean login(){
        loadAdmins();

        int attempts = 3;
        while(attempts > 0){
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter passworrd: ");
            String password = scanner.nextLine().trim();
            String stored = admins.get(username);
            if(stored != null){
                String[] parts = stored.split(",");
                String salt = parts[0];
                String storedHash =parts[1];
                String inputHash = hashPassword(password,salt);
                if(storedHash.equals(inputHash)){
                    System.out.println("Login Successful.");

                    return true;
                }
            }

            else{
                attempts--;
                System.out.println("Incorrect credentials. Attempts left: " + attempts);

            }
        }
            System.out.println("Too many failed attempts. System locked");
        return false;
        }
        public static String generateSalt(){
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return
        Base64.getEncoder().encodeToString(salt);
        }
       public static String hashPassword(String password, String salt){
           try{

               PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt),

                       100000,

                       256);

               SecretKeyFactory factory =SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

               byte[] hash = factory.generateSecret(spec).getEncoded();

               return

                       Base64.getEncoder().encodeToString(hash);



           }catch (Exception e){

               throw new RuntimeException("Error hashing password");

           }

       }

}











