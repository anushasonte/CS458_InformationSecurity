import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LetterFrequencies {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the cipher text: ");
        String cipherText = scanner.nextLine();

        Map<Character, Integer> frequencyMap = new HashMap<>();

        // Calculate letter frequencies
        for (char c : cipherText.toCharArray()) {
            //To include comma as well in the map.
            if (Character.isLetter(c) || c == ',') { 
                c = Character.toUpperCase(c);
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }


        //Placing frequencing the descending order by creating a sorted map
        Map<Character, Integer> sortedFrequencyMap = frequencyMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        // Printing frequencies of letters present in the cipher text.         
        System.out.println("{"); 
        boolean firstEntry = true;
        for (char c : sortedFrequencyMap.keySet()) {
            int frequency = sortedFrequencyMap.get(c);
            if (!firstEntry) {
                System.out.print(", ");
            }
            System.out.printf("'%c' : %d", c, frequency);
            firstEntry = false;
        }
        System.out.println("\n}");

        // Printing frequencies of letters present in the cipher text and English frequencies
        for (char c : sortedFrequencyMap.keySet()) {
            int frequency = sortedFrequencyMap.get(c);
            double englishFrequency = getEnglishFrequency(c);
            System.out.printf("%c %3d %.2f%n", c, frequency, englishFrequency);
        }

        //Taking substitution input from the user for all characters present in the cipher text.
        Map<Character, Character> substitutionMap = new HashMap<>();
        for (char c : sortedFrequencyMap.keySet()) {
            System.out.printf("What letter do you think '%c' corresponds to in the English language? ", c);
            String substitutionInput = scanner.nextLine();
            //Avoiding out of bounds exception in case there is no substitution provided, we assume it to be ' '
            char substitution = (substitutionInput.isEmpty()) ? ' ' : Character.toUpperCase(substitutionInput.charAt(0));
            substitutionMap.put(c, substitution);
        }

        System.out.println("\nThe original letters with their substituted letters are:");
        
        // Printing the original letters present in the cipher text along with their substituted letters.
        System.out.print("Key: {");
        boolean firstSubstitution = true;
        for (char c : substitutionMap.keySet()) {
            if (!firstSubstitution) {
                System.out.print(", ");
            }
            System.out.printf("'%c': '%c'", c, substitutionMap.get(c));
            firstSubstitution = false;
        }
        System.out.println("}");


        // Performing substitutions
        StringBuilder substitutedText = new StringBuilder();
        for (char c : cipherText.toCharArray()) {
            if (Character.isLetter(c) || c == ',') {
                char substitutedChar = substitutionMap.getOrDefault(Character.toUpperCase(c), c);
                substitutedText.append(Character.isLowerCase(c) ? Character.toLowerCase(substitutedChar) : substitutedChar);
            } else {
                substitutedText.append(c);
            }
        }

        System.out.println("\nAfter Substitution = \"" + substitutedText.toString() + "\"");
        scanner.close();
    }
      // Define a map to store English letter frequencies
      private static double getEnglishFrequency(char c) {
        Map<Character, Double> letterFrequencies = new HashMap<>();
        letterFrequencies.put('E', 12.02);
        letterFrequencies.put('T', 9.10);
        letterFrequencies.put('A', 8.12);
        letterFrequencies.put('O', 7.68);
        letterFrequencies.put('I', 7.31);
        letterFrequencies.put('N', 6.95);
        letterFrequencies.put('S', 6.28);
        letterFrequencies.put('R', 6.02);
        letterFrequencies.put('H', 5.92);
        letterFrequencies.put('D', 4.32);
        letterFrequencies.put('L', 3.98);
        letterFrequencies.put('U', 2.88);
        letterFrequencies.put('C', 2.71);
        letterFrequencies.put('M', 2.61);
        letterFrequencies.put('F', 2.30);
        letterFrequencies.put('Y', 2.11);
        letterFrequencies.put('W', 2.09);
        letterFrequencies.put('G', 2.03);
        letterFrequencies.put('P', 1.82);
        letterFrequencies.put('B', 1.49);
        letterFrequencies.put('V', 1.11);
        letterFrequencies.put('K', 0.69);
        letterFrequencies.put('X', 0.17);
        letterFrequencies.put('Q', 0.11);
        letterFrequencies.put('J', 0.10);
        letterFrequencies.put('Z', 0.07);
        
        return letterFrequencies.getOrDefault(Character.toUpperCase(c), 0.0);
    }
}
