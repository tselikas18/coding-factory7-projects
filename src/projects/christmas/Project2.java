package projects.christmas;

/**
 * Ο αλγόριθμος βρίσκει τον υποπίνακα με το μεγαλύτερο άθροισμα σε έναν πίνακα ακεραίων.
 * Στην function maxContiguousSubArray οι μεταβλητές:
 * 1) maxNum -> αποθηκεύει το μέγιστο άθροισμα που έχουμε βρει μέχρι στιγμής.
 * 2) currentSum -> αποθηκεύει το τρέχον άθροισμα του υποπίνακα.
 * 3) subArrSize -> παρακολουθεί το μέγεθος του μέγιστου υποπίνακα
 * 4) subArrIndex -> παρακολουθεί την αρχική θέση του μέγιστου υποπίνακα
 * Στην συνέχεια διασχίζουμε την FOR από το δεύτερο στοιχείο  του πίνακα εώς το τέλος.
 * Για κάθε στοιχείο ελέγχουμε εαν το currentSun είναι αρνητικό:
 * 1)(ΝΑΙ) Σημαίνει ότι το άθροισμα του τρέχοντος υποπίνακα είναι μικρότερο από το μηδέν και δεν
 * προσφέρει πλέον αξία. Συνεπώς, επαναφέρουμε το currentSum στο τρέχον στοιχείο και ενημερώνουμε
 * την αρχική θέση tempStart του νέου υποπίνακα
 * 2)(ΟΧΙ) Προσθέτουμε το τρέχον στοιχείο στο currentSum
 * Έπειτα με μια δομή ελέγχου και αφού ενημερώσουμε το currentSum, ελέγχουμε αν είναι μεγαλύτερο από
 * το maxNum. Αν είναι, ενημερώνουμε το maxNum, το μέγεθος του υποπίνακα subArrSize
 * και την αρχική θέση subArrIndex του μέγιστου υποπίνακα.
 * Τέλος δημιουργείται ο υποπίνακας (αφού έχουμε την αρχική θέση και το μέγεθος του υποπίνακα) που
 * περιέχει τα στοιχεία του μέγιστου υποπίνακα και επιστρέφει στη main(), όπου και εκπυπώνεται.
 * Ο αλγόριθμος έχει πολυπλοκότητα χρόνου Ο(n) αφού κάθε στοιχείο του πίνακα εξετάζεται ακριβώς
 * μία φορά και όλες οι πράξεις που εκτελούνται είναι σταθερού χρόνου.
 */

public class Project2 {
    public static void main(String[] args) {
        int[] arr = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int[] subArr = maxContiguousSubArray(arr);


        System.out.print("Maximum Contiguous Subarray: ");
        for (int num : subArr) {
            System.out.print(num + " ");
        }
    }

    //Επιστρέφει τον υποπίνακα με το μεγαλύτερο άθροισμα σε έναν πίνακα ακεραίων.
    public static int[] maxContiguousSubArray(int[] arr){
        int maxNum = arr[0];
        int currentSum = arr[0];
        int subArrSize = 1;
        int subArrIndex = 0;
        int tempStart = 0;

        for (int i = 1; i < arr.length; i++){
            if(currentSum < 0){
                currentSum = arr[i];
                tempStart = i;
            }else {
                currentSum += arr[i];
            }

            if (currentSum > maxNum){
                maxNum = currentSum;
                subArrSize = i - tempStart + 1;
                subArrIndex = tempStart;
            }
        }
        int[] sumOfSubArray = new int[subArrSize];
        System.arraycopy(arr, subArrIndex, sumOfSubArray, 0, subArrSize);
        return sumOfSubArray;
    }
}
