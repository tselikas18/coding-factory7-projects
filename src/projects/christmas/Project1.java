package projects.christmas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

/**
 * Αναπτύξτε ένα πρόγραμμα σε Java που να διαβάζει από ένα αρχείο ακέραιους
 * αριθμούς (το αρχείο πρέπει να περιέχει περισσότερους από 6 αριθμούς και το πολύ
 * 49 αριθμούς) με τιμές από 1 έως 49. Τους αριθμούς αυτούς τους εισάγει σε ένα
 * πίνακα, τον οποίο ταξινομεί (π.χ. με την Arrays.sort()). Στη συνέχεια, το πρόγραμμα
 * παράγει όλες τις δυνατές εξάδες (συνδυασμούς 6 αριθμών). Ταυτόχρονα και αμέσως
 * μετά την παραγωγή κάθε εξάδας ‘φιλτράρει’ κάθε εξάδα ώστε να πληροί τα
 * παρακάτω κριτήρια: 1) Να περιέχει το πολύ 4 άρτιους, 2) να περιέχει το πολύ 4
 * περιττούς, 3) να περιέχει το πολύ 2 συνεχόμενους, 4) να περιέχει το πολύ 3
 * ίδιους λήγοντες, 5) να περιέχει το πολύ 3 αριθμούς στην ίδια δεκάδα.
 * Τέλος, εκτυπώνει τις τελικές εξάδες σε ένα αρχείο με όνομα της επιλογής σας και
 * κατάληξη.txt.
 */

public class Project1 {

    public static void main(String[] args) {
        System.out.println("Please enter the text-file full path. (Example ->  C:\\Users\\tsel\\Desktop\\project1.txt )");
        Scanner in = new Scanner(System.in);
        String sourceFilePath = in.nextLine();
        String parentFilePath = getParentPath(sourceFilePath);
        String targetFilePath = parentFilePath + File.separator + UUID.randomUUID().toString() + ".txt";

        try {
            // Διαβάζει ακεραίους από το txt αρχείο
            int[] numbersArr = readNumbersFromFile(sourceFilePath);

            if (numbersArr.length < 7 || numbersArr.length > 49) {
                System.out.println("The file must contain between 7 and 49 integers.");
                return;
            }

            Arrays.sort(numbersArr);

            int[][] validCombinations = new int[10000][6];
            int validCount = 0;
            int[] currentCombination = new int[6];
            validCount = generateCombinations(numbersArr, currentCombination, 0, 0, validCombinations, validCount);

            // Γράφει τους αποδεκτούς συνδυασμούς στο νέο αρχείο txt.
            writeValidCombinations(validCombinations, validCount, targetFilePath);

            System.out.printf("Valid combinations have been written to %s. There are %d valid combinations.%n", targetFilePath, validCount);
        } catch (IOException e) {
            System.err.println("Error processing the file: " + e.getMessage());
        } finally {
            in.close();
        }
    }

    // Εκτυπώνει τους συνδυασμούς στο νέο αρχείο txt.
    public static void writeValidCombinations(int[][] validCombinations, int validCount, String outputFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (int i = 0; i < validCount; i++) {
                writer.write(Arrays.toString(validCombinations[i]));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Επιστρέφει τον parentPath από ένα filePath.
    public static String getParentPath(String filePath) {
        File file = new File(filePath);
        String parentPath = file.getParent();
        return parentPath != null ? parentPath : "";
    }

    // Διαβάζει από το source txt αρχείο και επιστρέφει έναν πίνακα με τους ακεραίους που επιτρέπονται.
    public static int[] readNumbersFromFile(String sourceFilePath) throws IOException {
        String[] lines = Files.readAllLines(Paths.get(sourceFilePath)).toArray(new String[0]);
        int[] tempNumbersArr = new int[49];
        int count = 0;

        for (String line : lines) {
            String[] elements = line.split("\\s+");
            for (String element : elements) {
                try {
                    int num = Integer.parseInt(element);
                    if (num >= 1 && num <= 49) {
                        tempNumbersArr[count++] = num;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("The text file format is wrong for the element: " + element);
                }
            }
        }

        return Arrays.copyOf(tempNumbersArr, count);
    }

    // Δημιουργεί και φιλτράρει συνδυασμούς των 6.
    public static int generateCombinations(int[] numbers, int[] current, int start, int depth, int[][] validCombinations, int validCount) {
        if (depth == 6) {
            if (isValidCombination(current)) {
                validCombinations[validCount++] = Arrays.copyOf(current, current.length);
            }
            return validCount;
        }
        for (int i = start; i < numbers.length; i++) {
            current[depth] = numbers[i];
            validCount = generateCombinations(numbers, current, i + 1, depth + 1, validCombinations, validCount);
        }
        return validCount;
    }

    // Ελέγχει για αποδεκτούς συνδυασμούς.
    public static boolean isValidCombination(int[] combination) {
        return !(isEven(combination) || isOdd(combination) ||
                isContiguous(combination) || isSameEnding(combination) ||
                isSameTen(combination));
    }

    // Ελέγχει εάν υπάρχουν πάνω από 4 ζυγούς αριθμούς σε μια 6άδα.
    public static boolean isEven(int[] combination) {
        int evenCount = 0;
        for (int num : combination) {
            if (num % 2 == 0) {
                evenCount++;
            }
        }
        return evenCount > 4;
    }

    // Ελέγχει εάν υπάρχουν πάνω από 4 περιττούς αριθμούς σε μια 6άδα.
    public static boolean isOdd(int[] combination) {
        int oddCount = 0;
        for (int num : combination) {
            if (num % 2 != 0) {
                oddCount++;
            }
        }
        return oddCount > 4;
    }

    // Ελέγχει εάν υπάρχουν 2 συνεχόμενοι αριθμοί.
    public static boolean isContiguous(int[] combination) {
        Arrays.sort(combination); // Sorting is necessary here for contiguous check
        for (int i = 0; i < combination.length - 1; i++) {
            if (combination[i + 1] - combination[i] == 1) {
                return true;
            }
        }
        return false;
    }

    // Ελέγχει εάν υπάρχουν 3 ίδιοι λήγοντες σε μια 6άδα.
    public static boolean isSameEnding(int[] combination) {
        int[] endingCount = new int[10];
        for (int num : combination) {
            endingCount[num % 10]++;
        }
        for (int count : endingCount) {
            if (count > 3) {
                return true;
            }
        }
        return false;
    }

    // Ελέγχει εάν υπάρχουν 3 αριθμοί στην ίδια δεκάδα.
    public static boolean isSameTen(int[] combination) {
        int[] decadeCount = new int[5];
        for (int num : combination) {
            decadeCount[num / 10]++;
        }
        for (int count : decadeCount) {
            if (count > 3) {
                return true;
            }
        }
        return false;
    }
}