package projects.christmas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Αναπτύξτε μία εφαρμογή που διαβάζει έναν-έναν τους χαρακτήρες ενός αρχείου και
 * τους εισάγει σε ένα πίνακα 128x2. Υποθέστε ότι οι χαρακτήρες είναι λατινικοί. Κάθε
 * θέση του πίνακα είναι επομένως ένας πίνακας δύο θέσεων, όπου στην 1η θέση
 * αποθηκεύεται ο χαρακτήρας που έχει διαβαστεί (αν δεν υπάρχει ήδη στον πίνακα)
 * και στην 2η θέση αποθηκεύεται το πλήθος των φορών που έχει διαβαστεί (βρεθεί)
 * κάθε χαρακτήρας. Αγνοήστε τα κενά και τις αλλαγές γραμμής και γενικά τα
 * whitespaces.
 * Στο τέλος η main() παρουσιάζει στατιστικά στοιχεία για κάθε χαρακτήρα όπως η
 * συχνότητα εμφάνισής του στο κείμενο ταξινομημένα ανά χαρακτήρα και ανά
 * συχνότητα εμφάνισης.
 */

public class Project3 {
    public static void main(String[] args) {
        CharCount[] charCounts = new CharCount[128];
        System.out.println("Please enter the text-file full path");
        Scanner in = new Scanner(System.in);
        String txtPath = in.nextLine();

        int uniqueCharCount = arrayFiller(txtPath, charCounts);

        for (int i = 0; i < uniqueCharCount; i++){
            System.out.println(charCounts[i].character + " has been found " + charCounts[i].count + " times.");
        }



    }

    public static int arrayFiller (String txtPath, CharCount[] charCounts){
        int uniqueCharCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(txtPath))) {
            int character;
            while ((character = reader.read()) != -1){
                char inputChar = (char) character;

                //Αγνοεί whitespaces
                if (Character.isWhitespace(inputChar)){
                    continue;
                }

                //Ελέγχει εαν είναι έγκυρος χαρακτήρας ASCII
                if (inputChar < 128 && uniqueCharCount < 128){
                    boolean alreadyExists = false;
                    //Ελέγχει εαν ο χαρακτήρας του αρχείου υπάρχει στον πίνακα, εαν υπάρχει αυξάνει το count
                    //στην δεύτερη στήλη για τον συγκεκριμένο χαρακτήρα
                    for (int i = 0; i < uniqueCharCount; i++){
                        if (charCounts[i].character == inputChar){
                            alreadyExists = true;
                            charCounts[i].count++;
                            break;
                        }
                    }
                    //Εαν δεν υπάρχει ο χαρακτήρας, τον προσθέτει στον πίνακα
                    if (!alreadyExists){
                        charCounts[uniqueCharCount] = new CharCount(inputChar, 1);
                        uniqueCharCount++;
                    }
                }else {
                    System.out.println("Error with filling the table.");
                }

            }

        }catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        return uniqueCharCount;
    }
}


//Ενθυλακώνει χαρακτήρες και το άθροισμα του καθενός.
class CharCount {
    char character;
    int count;

    CharCount(char character, int count){
        this.character = character;
        this.count = count;
    }
}
