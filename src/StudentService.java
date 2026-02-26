import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.util.Comparator;
public class StudentService {
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if (idExists(student.getId())) {
            System.out.println("ID already exists.");
            return;
        }
        students.add(student);
        System.out.println("Student added successfully,");
    }

    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\nID | NAME | COURSE| AGE");
        for (Student s : students) {
            System.out.println(s.getId() + " | " + s.getName() + " | " + s.getCourse() + " | " + s.getAge());

        }
    }

    public Student findStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public void updateStudent(int id, String name, String course, int age) {
        Student s = findStudent(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        s.setName(name);
        s.setCourse(course);
        s.setAge(age);
        System.out.println("Student updated successfully.");

    }

    public void deleteStudent(int id) {
        Student s = findStudent(id);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }
        students.remove(s);
        System.out.println("Student deleted successfully.");
    }

    public boolean idExists(int id) {
        return findStudent(id) != null;

    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("students.txt")) {
            for (Student s : students) {
                writer.write(s.getId() + "," + s.getName() + "," + s.getCourse() + "," + s.getAge() + "\n");
            }
        } catch (IOException e) {

            System.out.println("Error saving file.");
        }
    }

    public void loadFromFile() {
        students.clear();
        File file = new File("students.txt");
        if (!file.exists()) {
            System.out.println("No saved data found starting fresh.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] data = line.split(",");
                if (line.length() < 3) {
                    System.out.println("Skipping invalid line " + lineNumber + ". " + line);
                    continue;

                }
                try {

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    String course = data[2];
                    int age = Integer.parseInt(data[3]);
                    if (idExists(id)) {
                        System.out.println("Duplicate ID " + id + " found in file at line " + line + " skipping.");
                        continue;

                    }

                    students.add(new Student(id, name, course, age));
                    sortById();
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number at line " + lineNumber + ". " + line);
                }

            }

        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }

    public void sortByName(boolean ascending) {
        Comparator<Student> comp =
                Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER);
        students.sort(ascending ? comp : comp.reversed());
        students.forEach(System.out::println);
    }

    public void sortByAge(boolean ascending) {
        Comparator<Student> comp =
                Comparator.comparingInt(Student::getAge);
        students.sort(ascending ? comp : comp.reversed());
        students.forEach(System.out::println);

    }

    public void findByName(String keyword) {
        boolean found = false;
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(s.getId() + " | " + s.getName() + " | " + s.getCourse() + " | " + s.getAge());
                found = true;

            }
        }
        if (!found) {
            System.out.println("Student not found.");

        }
    }

    public void sortById() {
        students.sort(Comparator.comparingInt(Student::getId));

    }

}







