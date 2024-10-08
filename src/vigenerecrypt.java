import java.util.Scanner;

public class vigenerecrypt {

    public static StringBuffer encrypt(String plaintext, String key) {
        StringBuffer result = new StringBuffer();
        key = key.toLowerCase();

        for (int i = 0, j = 0; i < plaintext.length(); i++) {
            char letter = plaintext.charAt(i);

            if (Character.isUpperCase(letter)) {
                char ch = (char) (((int) letter + (key.charAt(j) - 'a') - 65) % 26 + 65);
                result.append(ch);
                j = (j + 1) % key.length();
            } else if (Character.isLowerCase(letter)) {
                char ch = (char) (((int) letter + (key.charAt(j) - 'a') - 97) % 26 + 97);
                result.append(ch);
                j = (j + 1) % key.length();
            } else {
                result.append(letter);
            }
        }
        return result;
    }

    public static StringBuffer decrypt(String encryptedtext, String key) {
        StringBuffer result = new StringBuffer();
        key = key.toLowerCase();

        for (int i = 0, j = 0; i < encryptedtext.length(); i++) {
            char letter = encryptedtext.charAt(i);

            if (Character.isUpperCase(letter)) {
                char ch = (char) (((int) letter - (key.charAt(j) - 'a') - 65 + 26) % 26 + 65);
                result.append(ch);
                j = (j + 1) % key.length();
            } else if (Character.isLowerCase(letter)) {
                char ch = (char) (((int) letter - (key.charAt(j) - 'a') - 97 + 26) % 26 + 97);
                result.append(ch);
                j = (j + 1) % key.length();
            } else {
                result.append(letter);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter some text to encrypt: ");
        String plaintext = scanner.nextLine();
        System.out.println("Enter key: ");
        String key = scanner.nextLine();

        StringBuffer encryptedText = encrypt(plaintext, key);
        System.out.println("Encrypted text: " + encryptedText);

        StringBuffer decryptedText = decrypt(String.valueOf(encryptedText), key);
        System.out.println("Decrypted text: " + decryptedText);
        scanner.close();
    }
}