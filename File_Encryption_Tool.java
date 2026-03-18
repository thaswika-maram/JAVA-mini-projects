import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class File_Encryption_Tool {
    
    private static String ALGORITHM = "AES";
    private static String KEY_FILE = "/home/chef/workspace/secret.key";

    //-----------------------------------------------------------
    public static void decryptFile(String inputFile, String outputFile) {
        try {
            // Read key from file
            byte[] keyBytes = Files.readAllBytes(Paths.get(KEY_FILE));
            SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
            
            // Initialize cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            // Read encrypted file
            byte[] encryptedData = Files.readAllBytes(Paths.get(inputFile));
            
            // Decrypt data
            byte[] decryptedData = cipher.doFinal(encryptedData);
            
            // Write decrypted data to output file
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(decryptedData);
            System.out.println("File decrypted successfully! Saved as "+ outputFile);
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error decrypting file.");
        }
    }
    //-----------------------------------------------------------
    
    public static void generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();

            // Save the key to file
            FileOutputStream keyFile = new FileOutputStream(KEY_FILE);
            keyFile.write(secretKey.getEncoded());
            System.out.println("Key generated and saved in 'secret.key' file.");
            keyFile.close();
        } catch (Exception e) {
            System.out.println("Error generating key.");
        }
    }
    
    public static void encryptFile(String inputFile, String outputFile) {
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(KEY_FILE));
            
            SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
            
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            byte[] fileData = Files.readAllBytes(Paths.get(inputFile));
            
            byte[] encryptedData = cipher.doFinal(fileData);
            
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(encryptedData);
            System.out.println("File encrypted successfully! Saved as "+ outputFile);
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error encrypting file.");
        }
    }
    
    public static String userChoice(int choice, Scanner scanner) {
        if (choice == 1) {
            generateKey();
        } 
        else if (choice == 2) {
            System.out.print("Enter the file to encrypt: ");
            String inputFile = scanner.nextLine();
            System.out.print("Enter the output file name: ");
            String outputFile = scanner.nextLine();
            encryptFile(inputFile, outputFile);
        } 
        else if (choice == 3) {
            System.out.print("Enter the file to decrypt: ");
            String inputFile = scanner.nextLine();
            System.out.print("Enter the output file name: ");
            String outputFile = scanner.nextLine();
            decryptFile(inputFile, outputFile);
        } 
        else if (choice == 4) {
            return ("Exiting... Goodbye!");
        } 
        else {
            System.out.println("Invalid choice! Try again.");
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to File Encryption Tool!\n");
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("File Encryption Tool");
            System.out.println("1. Generate Key");
            System.out.println("2. Encrypt a File");
            System.out.println("3. Decrypt a File");
            System.out.println("4. Exit");
            
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            String option = userChoice(choice, scanner);
            if(option != null && option.equals("Exiting... Goodbye!")){
                System.out.println(option);
                break;
            }
        }
        scanner.close();
    }
}