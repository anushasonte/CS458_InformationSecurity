// Problem: Write Java/Python code that performs a Buruteforce attack on a shift cipher.

import java.util.Scanner;

public class BruteForceShiftCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ciphertext? ");
        String ciphertext = scanner.nextLine();

        // Try all possible keys (0-25) to decrypt the ciphertext
        for (int key = 0; key < 26; key++) {
            String plaintext = decrypt(ciphertext, key);
            System.out.println("Key: " + key + "\tPlaintext: " + plaintext);
        }
        
        scanner.close();
    }

    // Decrypts the ciphertext using the specified key
    public static String decrypt(String ciphertext, int key) {
        StringBuilder plaintext = new StringBuilder();

        // Iterate through each character in the ciphertext
        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            
            // Decrypt the character by shifting it backward by the key (wrapping around within 'A' to 'Z')
            int decryptedChar = (26 + c - key - 'A') % 26 + 'A';
            
            // Append the decrypted character to the plaintext
            plaintext.append((char) decryptedChar);
        }

        return plaintext.toString();
    }
}
