// Author: Aland Abubakr
// GitHub: https://github.com/Aland-Abubakr-Kurd

import java.util.Random;
import java.util.Scanner;

// BB84 simulates the first quantum cryptography protocol proposed in 1984.
// It demonstrates how Alice and Bob can generate a perfectly secure shared
// secret key, and how the laws of quantum mechanics guarantee that an
// eavesdropper (Eve) will always be detected.

public class BB84 {

    static final int SIZE = 50; // Number of initial qubits to send
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);

    // Command line text colors
    final static String RED = "\u001b[31;1m";
    final static String BLUE = "\u001b[36;1m";
    final static String GREEN = "\u001b[32;1m";
    final static String YELLOW = "\u001b[33;1m";
    final static String RESET = "\u001b[0m";

    // Randomly selects a measurement basis: rectilinear (+) or diagonal (x)
    static char randomBasis() {
        return random.nextBoolean() ? '+' : 'x';
    }

    // Randomly generates a bit (0 or 1)
    static int randomBit() {
        return random.nextInt(2);
    }

    // Utility method to pause the program so the user can follow the simulation
    static void waitForUser() {
        System.out.print(YELLOW + "\nEnter 'c' to continue or 'q' to quit: " + RESET);
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("c")) {
                return;
            } else if (input.equalsIgnoreCase("q")) {
                System.out.println(GREEN + "- Program terminated. Have a great day! :D" + RESET);
                System.exit(0);
            } else {
                System.out.print(RED + "*) Invalid input. Please enter 'c' or 'q': " + RESET);
            }
        }
    }

    public static void main(String[] args) {

        System.out.println(BLUE + "\n=== BB84 Quantum Key Distribution Simulation ===" + RESET);

        // =============================
        // Choose direction
        // =============================
        int direction = 0;
        while (true) {
            System.out.println("\n" + BLUE + "[ Initial Setup ]" + RESET);
            System.out.println("- Who sends the qubits?");
            System.out.println("  1 - Alice → Bob");
            System.out.println("  2 - Bob → Alice");
            System.out.print("> ");

            try {
                direction = Integer.parseInt(scanner.nextLine());
                if (direction == 1 || direction == 2) {
                    break;
                } else {
                    System.out.println(RED + "*) Invalid option. Please enter 1 or 2." + RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "*) Invalid input. Please enter a valid number." + RESET);
            }
        }

        String sender = (direction == 1) ? "Alice" : "Bob";
        String receiver = (direction == 1) ? "Bob" : "Alice";

        // =============================
        // Eavesdropper option
        // =============================
        boolean hasEve = false;
        while (true) {
            System.out.print("- Is there an eavesdropper (Eve) intercepting the channel? (y/n): ");
            String eveInput = scanner.nextLine();
            if (eveInput.equalsIgnoreCase("y")) {
                hasEve = true;
                break;
            } else if (eveInput.equalsIgnoreCase("n")) {
                hasEve = false;
                break;
            } else {
                System.out.println(RED + "*) Invalid input. Please enter 'y' or 'n'." + RESET);
            }
        }

        // Arrays to hold the quantum states and measurements
        int[] senderBits = new int[SIZE];
        char[] senderBases = new char[SIZE];
        char[] receiverBases = new char[SIZE];
        int[] receiverResults = new int[SIZE];

        char[] eveBases = new char[SIZE];
        int[] eveResults = new int[SIZE];

        // =============================
        // Phase 1: Sender prepares qubits
        // =============================
        System.out.println(BLUE + "\n=== Phase 1: Preparation ===" + RESET);
        System.out.println("- " + sender + " prepares random bits and encodes them into quantum bases.");

        for (int i = 0; i < SIZE; i++) {
            senderBits[i] = randomBit();
            senderBases[i] = randomBasis();
        }

        System.out.print("Bits:    ");
        for (int b : senderBits) System.out.print(b + " ");
        System.out.print("\nBases:   ");
        for (char b : senderBases) System.out.print(b + " ");

        System.out.println(GREEN + "\n- " + sender + " sent the qubits through the quantum channel." + RESET);

        waitForUser();

        // =============================
        // Phase 2: Eve intercepts (optional)
        // =============================
        if (hasEve) {
            System.out.println(RED + "\n=== Phase 2: Interception ===" + RESET);
            System.out.println("⚠ Eve intercepts the qubits in transit!");
            System.out.println("- Because of Quantum Mechanics, Eve must guess the bases to measure them.");
            System.out.println("- If she guesses wrong, she permanently alters the qubit's state!");

            for (int i = 0; i < SIZE; i++) {
                eveBases[i] = randomBasis();
                // If Eve guesses the correct basis, she reads the bit perfectly.
                if (eveBases[i] == senderBases[i]) {
                    eveResults[i] = senderBits[i];
                } else {
                    // If she guesses wrong, the qubit collapses randomly!
                    eveResults[i] = randomBit();
                }
            }

            System.out.print("Bases:   ");
            for (char b : eveBases) System.out.print(b + " ");
            System.out.print("\nResults: ");
            for (int b : eveResults) System.out.print(b + " ");
            System.out.println();

            waitForUser();
        }

        // =============================
        // Phase 3: Receiver measures
        // =============================
        System.out.println(BLUE + "\n=== Phase 3: Measurement ===" + RESET);
        System.out.println("- " + receiver + " receives the qubits and randomly guesses the measurement bases.");

        for (int i = 0; i < SIZE; i++) {
            receiverBases[i] = randomBasis();

            if (!hasEve) {
                // No Eve: Receiver gets the exact bit if bases match, otherwise it's a random collapse
                receiverResults[i] = (receiverBases[i] == senderBases[i]) ? senderBits[i] : randomBit();
            } else {
                // With Eve: The receiver is now measuring the qubits *after* Eve altered them!
                receiverResults[i] = (receiverBases[i] == eveBases[i]) ? eveResults[i] : randomBit();
            }
        }

        System.out.print("Bases:   ");
        for (char b : receiverBases) System.out.print(b + " ");

        System.out.println("\n- " + receiver + " measures them:");

        System.out.print("Results: ");
        for (int b : receiverResults) System.out.print(b + " ");
        System.out.println();

        waitForUser();

        // =============================
        // Phase 4: Basis publishing
        // =============================
        System.out.println(BLUE + "\n=== Phase 4: Public Sifting ===" + RESET);
        System.out.println("- Sender and Receiver publicly compare which BASES they used.");
        System.out.println("- They throw away any bits where their bases didn't match.");
        System.out.println(YELLOW + "⚠ Note: Only bases are published, NOT the actual bit values!" + RESET + "\n");

        System.out.println(" Index | Sender Basis | Sender Bit | Receiver Basis | Receiver Bit | Keep?");
        System.out.println("-------------------------------------------------------------------------------");

        int[] keptIndices = new int[SIZE];
        int keptCount = 0;

        for (int i = 0; i < SIZE; i++) {
            boolean keep = senderBases[i] == receiverBases[i];

            // Using printf for perfect tabular alignment! %2d ensures single digits take up 2 spaces.
            System.out.printf("  %2d   |      %c       |     %d      |       %c        |      %d       | %s%n",
                    i, senderBases[i], senderBits[i], receiverBases[i], receiverResults[i], (keep ? GREEN + "YES" + RESET : RED + "NO " + RESET));

            if (keep) {
                keptIndices[keptCount] = i;
                keptCount++;
            }
        }

        waitForUser();

        // =============================
        // Phase 5: Error checking
        // =============================
        System.out.println(BLUE + "\n=== Phase 5: Error Checking ===" + RESET);

        int checkLength = keptCount / 2;

        // Randomly select indices for checking
        boolean[] isPublished = new boolean[SIZE];
        int[] checkIndices = new int[checkLength];
        int[] finalIndices = new int[keptCount - checkLength];

        // Mark random indices as published
        int publishedCount = 0;
        while (publishedCount < checkLength) {
            int randomPos = random.nextInt(keptCount);
            int idx = keptIndices[randomPos];
            if (!isPublished[idx]) {
                isPublished[idx] = true;
                checkIndices[publishedCount] = idx;
                publishedCount++;
            }
        }

        // Collect final key indices
        int finalCount = 0;
        for (int i = 0; i < keptCount; i++) {
            int idx = keptIndices[i];
            if (!isPublished[idx]) {
                finalIndices[finalCount] = idx;
                finalCount++;
            }
        }

        System.out.println("- They randomly select half of their matching bits to publicly compare.");
        System.out.println("- If Eve was listening, her wrong guesses will cause mismatches here!");
        System.out.println("- Published bits count: " + checkLength + "\n");

        System.out.println(" Index | Sender Basis | Sender Bit | Receiver Basis | Receiver Bit | Published?");
        System.out.println("-----------------------------------------------------------------------------------");

        String senderCheck = "";
        String receiverCheck = "";

        for (int i = 0; i < keptCount; i++) {
            int idx = keptIndices[i];

            System.out.printf("  %2d   |      %c       |     %d      |       %c        |      %d       | %s%n",
                    idx, senderBases[idx], senderBits[idx], receiverBases[idx], receiverResults[idx], (isPublished[idx] ? YELLOW + "YES" + RESET : "NO "));

            if (isPublished[idx]) {
                senderCheck += senderBits[idx];
                receiverCheck += receiverResults[idx];
            }
        }

        System.out.println("\n" + sender + " publishes:   " + senderCheck);
        System.out.println(receiver + " publishes: " + receiverCheck);

        // If the bits don't match, Eve altered the quantum state!
        if (!senderCheck.equals(receiverCheck)) {
            System.out.println(RED + "\n❌ EAVESDROPPER DETECTED! The laws of physics caught Eve." + RESET);
            System.out.println(RED + "Communication aborted. The key is compromised." + RESET);

            // Show exactly where Eve messed up the quantum state
            System.out.println("\nQuantum state mismatches found at these indices:");
            for (int i = 0; i < checkLength; i++) {
                int idx = checkIndices[i];
                if (senderBits[idx] != receiverResults[idx]) {
                    System.out.println("  Index " + idx + ": " + sender + " had " + senderBits[idx] + ", " + receiver + " measured " + receiverResults[idx]);
                }
            }
            return; // End the program
        }

        System.out.println(GREEN + "\n✅ No eavesdropper detected. The quantum channel is secure." + RESET);

        waitForUser();

        // =============================
        // Phase 6: Final key
        // =============================
        System.out.println(BLUE + "\n=== Phase 6: Final Secret Key ===" + RESET);

        String finalKey = "";
        for (int i = 0; i < finalCount; i++) {
            finalKey += senderBits[finalIndices[i]];
        }

        System.out.println("- The remaining unpublished matching bits form the secure key.");
        System.out.println(GREEN + "★ Final Shared Secret Key: " + finalKey + " ★" + RESET);

        System.out.println(BLUE + "\n=== End of BB84 Simulation ===" + RESET);
    }
}