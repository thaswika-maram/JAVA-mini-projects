import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word_Counter {

    public static String readFile(String filepath) {
        try {
            String content = Files.readString(Paths.get(filepath));
            return content;  
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    public static ArrayList<String> countWordsRegex(String text) {
        ArrayList<String> words = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\b\\w+\\b").matcher(text);
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return words;
    }

    public static int wordFrequency(ArrayList<String> words, String searchWord) {
        int count = 0;
        for (int i=0;i<words.size();i++) {
            if (words.get(i).equals(searchWord)) {
                count++;
            }
        }
        return count;
    }

    //------------------------------------------------------------------
    public static void displayResults(int totalWords, String searchWord, int wordFreq) {
        System.out.println("Total Words: " + totalWords);
        System.out.println("Frequency of '" + searchWord + "': " + wordFreq);
    }
    //------------------------------------------------------------------

    public static void main(String[] args) {
        System.out.println("Welcome to the Word Counter!\n");

        String filepath = "/home/chef/workspace/input.txt";
        String text = readFile(filepath);

        if (text.equals("")) {
            return;
        }
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> words =  countWordsRegex(text);
        int totalWords = words.size();

        System.out.print("Enter the word to search: ");
        String searchWord = scanner.nextLine();

        int wordFreq = wordFrequency(words, searchWord);
        displayResults(totalWords, searchWord, wordFreq);
        scanner.close();
    }
}