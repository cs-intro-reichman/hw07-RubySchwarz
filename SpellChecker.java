import java.io.File;
import java.util.Scanner;

public class SpellChecker {

    // Method to read the dictionary from a file
    public static String[] readDictionary(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            String[] dictionary = new String[3000]; // Assuming the dictionary has exactly 3000 words
            int index = 0;
            while (scanner.hasNextLine() && index < dictionary.length) {
                dictionary[index++] = scanner.nextLine();
            }
            scanner.close();
            return dictionary;
        } catch (Exception e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
            return new String[0]; // Return an empty array on error
        }
    }

    // Spell checker function
    public static String spellChecker(String word, int threshold, String[] dictionary) {
        String closestWord = word; // Start with the original word
        int minDistance = Integer.MAX_VALUE;

        for (String dictWord : dictionary) {
            int distance = levenshteinDistance(word, dictWord);
            if (distance < minDistance) {
                minDistance = distance;
                closestWord = dictWord;
            }
        }

        return minDistance <= threshold ? closestWord : word;
    }

    // Public and static method for Levenshtein distance calculation
    public static int levenshteinDistance(String s1, String s2) {
        int[] prev = new int[s2.length() + 1];
        int[] curr = new int[s2.length() + 1];

        for (int i = 0; i <= s2.length(); i++) {
            prev[i] = i;
        }

        for (int i = 1; i <= s1.length(); i++) {
            curr[0] = i;
            for (int j = 1; j <= s2.length(); j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                curr[j] = Math.min(Math.min(curr[j - 1] + 1, prev[j] + 1), prev[j - 1] + cost);
            }
            int[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[s2.length()];
    }

    // Main method for testing
    public static void main(String[] args) {
        if (args.length >= 2) {
            String[] dictionary = readDictionary("dictionary.txt"); // Adjust path as necessary
            String wordToCheck = args[0];
            int threshold = Integer.parseInt(args[1]);
            String correctedWord = spellChecker(wordToCheck, threshold, dictionary);
            System.out.println(correctedWord);
        } else {
            System.out.println("Usage: java SpellChecker <word> <threshold>");
        }
    }
}
