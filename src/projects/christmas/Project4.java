package projects.christmas;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Αναπτύξτε ένα παιχνίδι Τρίλιζα, όπου δύο παίκτες παίζουν Χ και Ο (ή 1 και 2 αν θέλετε
 * να υλοποιήσετε με πίνακα ακεραίων και όχι με πίνακα char) και κερδίζει ο παίκτης
 * που έχει συμπληρώσει τρία ίδια σύμβολα ή αριθμούς σε οποιαδήποτε διάσταση του
 * πίνακα, οριζόντια, κάθετα ή διαγώνια.
 * Η main() μπορεί να ελέγχει τη ροή του παιχνιδιού, όπως ποιος παίκτης παίζει κάθε
 * φορά (εναλλαγή μεταξύ των δύο παικτών), να διαβάζει από το stdin το σύμβολο που
 * δίνει ο κάθε παίκτης και να εμφανίζει με γραφικό τρόπο την τρίλιζα μετά από κάθε
 * κίνηση κάθε παίκτη.
 * Ενώ, μπορείτε να δημιουργήσετε και μία μέθοδο που να ελέγχει (μετά από κάθε
 * κίνηση) αν ο παίκτης που έκανε την κίνηση έκανε τρίλιζα.
 * Το πρόγραμμα θα πρέπει να λαμβάνει υπόψη την περίπτωση ισοπαλίας όπως και να
 * μην επιτρέπει ένας παίκτης να παίξει σε θέση που είναι ήδη κατειλημμένη.
 */

public class Project4 {

    public static void main(String[] args) {
        instructions();
        String[][] ticTacToe = ticTacToeInitialPresentation();
        int round = 1;
        int choice = 0;

        while (round < 10) {
            roundCheck(round);
            Scanner in = new Scanner(System.in);
            try {
                choice = in.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Choice must be integer 1 - 9.");
            }

            if (choice < 1 || choice > 9) {
                System.out.println("The number you entered is not correct. Try again.");
                System.out.println();
                continue;
            }

            if (!makeMove(ticTacToe, choice, round)) {
                System.out.println("You can't override marks! Try again.");
                System.out.println();
                continue;
            }

            showTheMove(ticTacToe);

            if (isWinnerCheck(ticTacToe)) {
                System.out.println("Player " + (round % 2 == 1 ? "1" : "2") + " won the round!");
                break;
            }
            round++;
        }
        if (round == 9) {
            System.out.println("The game is a draw!");
        }
    }

    //Αρχική παρουσίαση τρίλιζας
    public static String[][] ticTacToeInitialPresentation() {
        String[][] tempTrillArr = new String[3][3];
        int position = 1;
        for (int i = 0; i < tempTrillArr.length; i++) {
            for (int j = 0; j < tempTrillArr[i].length; j++) {
                tempTrillArr[i][j] = "[" + position + "]";
                position++;
            }
        }
        int number = 1;
        for (int i = 0; i < tempTrillArr.length; i++) {
            for (int j = 0; j < tempTrillArr[i].length; j++) {
                System.out.print("[" + number + "]");
                number++;
            }
            System.out.println();
        }
        return tempTrillArr;
    }

    //Εμφανίζει οδηγίες
    public static void instructions() {
        System.out.println("Welcome to TicTacToe 2-player game");
        System.out.println("Player 1 has X mark. Player 2 has O mark");
        System.out.println("Enter the position number as represented below!");
        System.out.println("Good luck!");
    }

    //Κίνηση παίχτη
    public static boolean makeMove(String[][] ticTacToe, int choice, int round) {
        int position = 1;
        for (int i = 0; i < ticTacToe.length; i++) {
            for (int j = 0; j < ticTacToe[i].length; j++) {
                if (position == choice) {
                    //Έλεγχει εάν έχει παιχτεί το συγκεκριμένο κελί
                    if (!Objects.equals(ticTacToe[i][j], "X") && !Objects.equals(ticTacToe[i][j], "O")) {
                        ticTacToe[i][j] = (round % 2 != 0) ? "X" : "O";
                        return true;
                    } else {
                        return false;
                    }
                }
                position++;
            }
        }
        return false;
    }

    //Εμφανίζει την ανανεωμένη τρίλιζα
    public static void showTheMove(String[][] ticTacToe) {
        for (String[] strings : ticTacToe) {
            for (String string : strings) {
                System.out.print(" " + string + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //Ανακοινώνει την σειρά του παίχτη
    public static void roundCheck(int round) {
        System.out.println("Player " + (round % 2 != 0 ? "1" : "2") + " turn");
        System.out.println();
    }

    //Ελέγχει εαν υπάρχει νικητής
    public static boolean isWinnerCheck(String[][] ticTacToe) {
        for (int i = 0; i < 3; i++) {
            if ((ticTacToe[i][0].equals(ticTacToe[i][1]) && ticTacToe[i][0].equals(ticTacToe[i][2])) ||
                    (ticTacToe[0][i].equals(ticTacToe[1][i]) && ticTacToe[0][i].equals(ticTacToe[2][i]))) {
                return true;
            }
        }
        if ((ticTacToe[0][0].equals(ticTacToe[1][1]) && ticTacToe[0][0].equals(ticTacToe[2][2])) ||
                (ticTacToe[0][2].equals(ticTacToe[1][1]) && ticTacToe[0][2].equals(ticTacToe[2][0]))) {
            return true;
        }
        return false;
    }
}