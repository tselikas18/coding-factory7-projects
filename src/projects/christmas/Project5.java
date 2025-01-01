package projects.christmas;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Έστω ένα θέατρο που έχει θέσεις όπου η κάθε θέση περιγράφεται με ένα χαρακτήρα
 * που είναι η στήλη και ένα αριθμό που είναι η σειρά. Για παράδειγμα η θέση C2
 * βρίσκεται στην 2η σειρά και 3η στήλη.
 * Αναπτύξτε ένα πρόγραμμα διαχείρισης θεάτρου με 30 σειρές και 12 στήλες. Πιο
 * συγκεκριμένα γράψτε μία μέθοδο void book(char column, int row) που να κάνει book
 * μία θέση αν δεν είναι ήδη booked και μία μέθοδο void cancel(char column, int row)
 * που να ακυρώνει την κράτηση μία θέσης αν είναι ήδη booked.
 * Hint. Υποθέστε ότι ο δυσδιάστατος πίνακας που απεικονίζει το θέατρο είναι ένα
 * πίνακας από boolean, όπου το true σημαίνει ότι η θέση είναι booked και false ότι δεν
 * είναι booked. Αρχικά όλες οι θέσεις πρέπει να είναι non-booked.
 */

public class Project5 {
    static final int rowsInTheater = 30;
    static final int columnsInTheater = 12;
    private static boolean[][] theater = theaterInitialization();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int choice;

        do {
            menu();
            try {
                choice = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter one of the following options");
                System.out.println();
                menu();
                choice = -1;
                in.next(); // Clear the invalid input
            }
            if (choice == 1 || choice == 2) {
                switcher(choice);
            }
        } while (choice != 3);

        in.close(); // Close the scanner
    }

    //Αρχικοποιεί τον 2D theater σε false
    public static boolean[][] theaterInitialization() {
        boolean[][] theater = new boolean[rowsInTheater][columnsInTheater];
        for (boolean[] booleans : theater) {
            Arrays.fill(booleans, false);
        }
        return theater;
    }

    public static void menu() {
        System.out.println("Tselikas Theater Booking Home page");
        System.out.println();
        System.out.println("1. Book a seat");
        System.out.println("2. Cancel reservation");
        System.out.println("3. Exit");
        System.out.println("Our theater has 30 rows and 12 columns with great view");
    }

    //Δέχεται την επιλογή του χρήστη και έπειτα κάνει book ή cancel στην theater.
    public static void switcher(int choice) {
        Scanner in = new Scanner(System.in);
        switch (choice) {
            case 1:
                System.out.println("This is the availability of the theater:");
                showAvailability();
                System.out.println("Enter the seat you want to book like <<A3>>");
                String seatInput = in.next();
                if (seatInput.length() < 2) {
                    System.out.println("Invalid seat format. Please enter like <<A3>>");
                    break;
                }
                char columnBook = seatInput.charAt(0);
                int rowBook = Integer.parseInt(seatInput.substring(1));
                book(columnBook, rowBook);
                break;
            case 2:
                System.out.println("This is the availability of the theater:");
                showAvailability();
                System.out.println("Enter the seat you want to cancel like <<A3>>");
                String cancelInput = in.next();
                if (cancelInput.length() < 2) {
                    System.out.println("Invalid seat format. Please enter like <<A3>>");
                    break;
                }
                char columnCancel = cancelInput.charAt(0);
                int rowCancel = Integer.parseInt(cancelInput.substring(1));
                cancel(columnCancel, rowCancel);
                System.out.println("This is the new availability of the theater:");
                showAvailability();
                break;
        }
    }

    //Κλείνει την θέση στον theater
    public static void book(char column, int row) {
        column = Character.toUpperCase(column);
        int columnNumber = (column - 'A');
        int rowNumber = row - 1;

        if (columnNumber < 0 || columnNumber >= columnsInTheater || rowNumber < 0 || rowNumber >= rowsInTheater) {
            System.out.println("The number of seat you tried to book does not exist");
            return;
        }
        if (!theater[rowNumber][columnNumber]) {
            theater[rowNumber][columnNumber] = true;
            System.out.println("Your booking for seat " + column + row + " is completed!");
        } else {
            System.out.println("The seat " + column + row + " is already occupied!");
        }
    }

    //Ακυρώνει την θέση στον theater
    public static void cancel(char column, int row) {
        column = Character.toUpperCase(column);
        int columnNumber = (column - 'A');
        int rowNumber = row - 1;

        if (columnNumber < 0 || columnNumber >= columnsInTheater || rowNumber < 0 || rowNumber >= rowsInTheater) {
            System.out.println("The number of booked seat you tried to cancel does not exist");
            return;
        }
        if (theater[rowNumber][columnNumber]) {
            theater[rowNumber][columnNumber] = false;
            System.out.println("Your booked seat at " + column + row + " has been canceled!");
        } else {
            System.out.println("The seat " + column + row + " is not booked yet!");
        }
    }

    //Δείχνει την διαθεσιμότητα στον theater
    public static void showAvailability() {
        for (int i = 0; i < rowsInTheater; i++) {
            for (int j = 0; j < columnsInTheater; j++) {
                if (theater[i][j]) {
                    System.out.print((char) (65 + j) + String.valueOf(i + 1) + " Booked   ");
                } else {
                    System.out.print((char) (65 + j) + String.valueOf(i + 1) + " Available ");
                }
            }
            System.out.println();
        }
    }
}