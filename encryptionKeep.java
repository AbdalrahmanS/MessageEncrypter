/**
 * Project: MessageEncrypter
 * Last Edited: 09/25/2021
 * Summary: This class encryptes message with any cipher, so far the only 
 * cipher that can be used ceaser cipher, but hopefully this will expand in 
 * the future. Right Now it's very basic, with many inefficiencies, but that
 * will be fixed in the future. 
 */

import java.util.*;

public class encryptionKeep {
    private String cipherKey = "";
    private HashMap<Character, Integer> cipherKeyMap = new HashMap<>();
    private HashMap<Character, Integer> trueKeyMap = new HashMap<>();

    /**
     * Method: String
     * This method recives a message, it then encrypte it using a ceaser
     * cipher. First the method changes all the message's leters in lower-case.
     * It then generates the cipher. Then in the for-loop evey letter is 
     * replaced with the cipher equivlant 
     */
    public String caesarCipher(String message) {
        ceaserCipherGenerator();
        message = message.toLowerCase();
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) != ' ') {
                char cipherChar = getKey(cipherKeyMap, trueKeyMap.get(message.charAt(i)));
                message = message.substring(0, i) + cipherChar + message.substring(i+1);
            } 
        }
        return message;
    }

    /**
     * Method:String
     * This method is the same as the ceaserCipher(String, String), except it
     * will use the cipher that was previously made,
     */
    public String reuseCaesarCipher(String message) {
        message = message.toLowerCase();
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) != ' ') {
                char cipherChar = getKey(cipherKeyMap, trueKeyMap.get(message.charAt(i)));
                message = message.substring(0, i) + cipherChar + message.substring(i+1);
            } 
        }
        return message;
    }
    
    /**
     * Method: String
     * This method returns the key of the cipher. It does this by taking
     * the class variable and adding the key to it. The key is created by
     * generating a letter from the alphabet, then showing it's cypher equivilant
     * this done using the getKey method.
     */
    public String cipherKey() {
        cipherKey += "[a: " + getKey(trueKeyMap, cipherKeyMap.get('a'));
        for (char i = 'b'; i < 'z' + 1; i++) {
            cipherKey += ", " + String.valueOf(i) + ": " + 
                getKey(trueKeyMap, cipherKeyMap.get(i));
        }
        cipherKey += "]\n";
        return cipherKey;
    }

    /**
     * Method: Private Void
     * This method generates the ceaserCipher, the main crux of the cipher
     * generation is the use of the Random object, which is an issue, since
     * it isn't truley random. First an integer array is created, the first value
     * gets a number between 0-25. Then the last value of the array is given an 
     * unreachble number, since the default number in array is 0. The for-loop
     * generates a random number, then if statement checks to see if the number
     * is unique, if it's isn't, a while-loop will keep generating numbers untill
     * that number is unique. Once a unique number is found, it's put into the array
     * 
     * The last for-loop has 2 jobs, the first job is to add those numbers to
     * the cipherKeyMap, each letter in the cipherKeyMap gets a unique number. The 
     * second job of the for loop is to fill up the othr hashmap, this hashmap has
     * the letters mapped to their actual numbers. For example a -> 0, b -> 1.
     * The cipher works because for example, a would be mapped to the number 1, meaning
     * that once a message is encrupted, all As in that message become Bs. 
     *
     */
    private void ceaserCipherGenerator() {
        Random rand = new Random();
        int[] integerArray = new int[26];
        integerArray[0] = rand.nextInt(26);
        integerArray[25] = 26;
        for (int i = 1; i < 26; i++) {
            int currentNumber = rand.nextInt(26);
            if (arrayContains(integerArray, currentNumber)) {
                while (arrayContains(integerArray, currentNumber)) {
                    currentNumber = rand.nextInt(26);
                }
            }
            integerArray[i] = currentNumber;
        }

        int j = 0;
        for (char i = 'a'; i < 'z' + 1; i++) {
            trueKeyMap.put(i, j);
            cipherKeyMap.put(i, integerArray[j]);
            j++;
        }
    }

    /**
     * Method: Private Void
     * This method searched an array for a number
     */
    private boolean arrayContains(int[] numberLoop, int number) {
        for (int i = 0; i < numberLoop.length; i++) {
            if (numberLoop[i] == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method borrowed from 
     * www.techiedelight.com/get-map-key-from-value-java/,
     * this method kelps find a key, by using it's value
     */
    private static <K, V> K getKey(Map<K, V> map, V value) {
    return map.entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().get();
    }
}
