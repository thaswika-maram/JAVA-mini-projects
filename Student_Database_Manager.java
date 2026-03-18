import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Student_Database_Manager {

    static final String FILE_NAME = "students.txt";
    static Scanner scanner = new Scanner(System.in);

    public static void addStudent() throws Exception {
        System.out.print("Enter Student Roll Number (Numeric): ");
        String roll = scanner.nextLine().trim();

        if (roll == null || roll.isEmpty()) {
            System.out.println("Invalid Roll Number!");
            return;
        }

        boolean isNumeric = true;
        for (int i = 0; i < roll.length(); i++) {
            if (!Character.isDigit(roll.charAt(i))) {
                isNumeric = false;
                break;
            }
        }
        if (!isNumeric) {
            System.out.println("Roll number must be numeric.");
            return;
        }

        ArrayList<String> lines = new ArrayList<>();
        if (Files.exists(Paths.get(FILE_NAME))) {
            lines = new ArrayList<>(Files.readAllLines(Paths.get(FILE_NAME)));
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).startsWith(roll + ",")) {
                    System.out.println("Roll number already exists.");
                    return;
                }
            }
        }

        System.out.print("Enter First Name: ");
        String name = scanner.nextLine().trim();
        boolean isAlpha = true;
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i))) {
                isAlpha = false;
                break;
            }
        }
        if (!isAlpha) {
            System.out.println("Name must only contain letters.");
            return;
        }

        System.out.print("Enter Age: ");
        String age = scanner.nextLine().trim();
        boolean isAge = true;
        for (int i = 0; i < age.length(); i++) {
            if (!Character.isDigit(age.charAt(i))) {
                isAge = false;
                break;
            }
        }
        if (!isAge || Integer.parseInt(age) > 100) {
            System.out.println("Age must be a valid number under 100.");
            return;
        }

        System.out.print("Enter Course: ");
        String course = scanner.nextLine().trim();
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine().trim();

        String newStudent = roll + "," + name + "," + age + "," + course + "," + dept;
        lines.add(newStudent);
        Files.write(Paths.get(FILE_NAME), lines);

        System.out.println("Student added successfully.");
    }

    public static void viewStudents() throws Exception {
        if (!Files.exists(Paths.get(FILE_NAME))) {
            System.out.println("No student records found.");
            return;
        }

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(FILE_NAME)));
        if (lines.size() == 0) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\nRoll Number\tName\tAge\tCourse\tDepartment");
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length == 5) {
                System.out.println(parts[0] + "\t\t" + parts[1] + "\t" + parts[2] + "\t" + parts[3] + "\t" + parts[4]);
            }
        }
    }

    public static void searchStudent() throws Exception {
        System.out.print("Enter Roll Number or Name to search: ");
        String query = scanner.nextLine().trim();

        if (!Files.exists(Paths.get(FILE_NAME))) {
            System.out.println("No student records found.");
            return;
        }

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(FILE_NAME)));
        boolean found = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length == 5 && (parts[0].equals(query) || parts[1].equalsIgnoreCase(query))) {
                System.out.println("\nStudent Found:");
                System.out.println("Roll Number\tName\tAge\tCourse\tDepartment");
                System.out.println(parts[0] + "\t\t" + parts[1] + "\t" + parts[2] + "\t" + parts[3] + "\t" + parts[4]);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
        }
    }

    public static void updateStudent() throws Exception {
        System.out.print("Enter Roll Number to update: ");
        String roll = scanner.nextLine().trim();

        if (!Files.exists(Paths.get(FILE_NAME))) {
            System.out.println("No student records found.");
            return;
        }

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(FILE_NAME)));
        boolean found = false;

        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length == 5 && parts[0].equals(roll)) {
                found = true;

                System.out.print("Enter New Name (" + parts[1] + "): ");
                String name = scanner.nextLine().trim();
                if (!name.isEmpty()) parts[1] = name;

                System.out.print("Enter New Age (" + parts[2] + "): ");
                String age = scanner.nextLine().trim();
                if (!age.isEmpty()) parts[2] = age;

                System.out.print("Enter New Course (" + parts[3] + "): ");
                String course = scanner.nextLine().trim();
                if (!course.isEmpty()) parts[3] = course;

                System.out.print("Enter New Department (" + parts[4] + "): ");
                String dept = scanner.nextLine().trim();
                if (!dept.isEmpty()) parts[4] = dept;

                String updatedLine = parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3] + "," + parts[4];
                lines.set(i, updatedLine);
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
            return;
        }

        Files.write(Paths.get(FILE_NAME), lines);
        System.out.println("Student updated successfully.");
    }

    public static void deleteStudent() throws Exception {
        System.out.print("Enter Roll Number to delete: ");
        String roll = scanner.nextLine().trim();

        if (!Files.exists(Paths.get(FILE_NAME))) {
            System.out.println("No student records found.");
            return;
        }

        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(FILE_NAME)));
        boolean found = false;

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith(roll + ",")) {
                lines.remove(i);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student not found.");
            return;
        }

        Files.write(Paths.get(FILE_NAME), lines);
        System.out.println("Student deleted successfully.");
    }

    public static void handleUserChoice(String choice) throws Exception {
        if (choice.equals("1")) addStudent();
        else if (choice.equals("2")) viewStudents();
        else if (choice.equals("3")) searchStudent();
        else if (choice.equals("4")) updateStudent();
        else if (choice.equals("5")) deleteStudent();
        else if (choice.equals("6")) {
            System.out.println("Goodbye!");
            return;
        } else {
            System.out.println("Invalid option. Try again.");
        }
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("\n==== Student Database Menu ====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();
            handleUserChoice(choice);
        }
    }
}
