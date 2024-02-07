import java.io.File;
import java.util.Scanner;

public class HashTagTokenizer {

    // Inner class for reading files
    private static class In {
        private Scanner scanner;

        // Constructor attempts to open the specified file
        public In(String fileName) {
            try {
                File file = new File(fileName);
                scanner = new Scanner(file);
            } catch (Exception e) { // Catches any exception, including FileNotFoundException
                System.err.println("Error opening file: " + fileName + " - " + e.getMessage());
                System.exit(1);
            }
        }

        // Reads a single line from the file
        public String readLine() {
            return scanner.hasNextLine() ? scanner.nextLine() : null;
        }

        // Closes the scanner
        public void close() {
            scanner.close();
        }
    }

    // Reads the dictionary from a file into an array
    public static String[] readDictionary(String fileName) {
        In in = new In(fileName);
        String[] dictionary = new String[3000]; // Assumes the dictionary has exactly 3000 words
        int index = 0;
        while (in.scanner.hasNextLine() && index < dictionary.length) {
            dictionary[index++] = in.readLine();
        }
        in.close();
        return dictionary;
    }

    // Checks if a word exists in the dictionary
    public static boolean existInDictionary(String word, String[] dictionary) {
        for (String dictWord : dictionary) {
            if (dictWord.equals(word)) {
                return true;
            }
        }
        return false;
    }

    // Recursive function to break down a hashtag into dictionary words
    public static void breakHashTag(String hashtag, String[] dictionary) {
        hashtag = hashtag.toLowerCase(); // Converts the hashtag to lowercase for comparison
        breakHashTagHelper(hashtag, dictionary, "");
    }

    // Helper function for recursive breaking of the hashtag
    private static void breakHashTagHelper(String remainingHashtag, String[] dictionary, String currentPrefix) {
        if (remainingHashtag.isEmpty()) {
            return;
        }

        for (int i = 1; i <= remainingHashtag.length(); i++) {
            String newPrefix = remainingHashtag.substring(0, i);
            if (existInDictionary(newPrefix, dictionary)) {
                System.out.println(newPrefix);
                breakHashTagHelper(remainingHashtag.substring(i), dictionary, "");
                return;
            }
        }
    }

    // Main method to run the tokenizer
    public static void main(String[] args) {
        if (args.length > 0) {
            String hashtag = args[0];
            String[] dictionary = readDictionary("dictionary.txt"); // Adjust the path as necessary
            breakHashTag(hashtag, dictionary);
        } else {
            System.out.println("Please provide a hashtag to tokenize.");
        }
    }
}
