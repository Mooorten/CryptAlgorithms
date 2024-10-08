import java.util.Scanner;

public class caesarcrypt {
    public static StringBuffer encrypt(String plaintext, int shift) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < plaintext.length(); i++) {
            if (Character.isUpperCase(plaintext.charAt(i))) {
                char ch = (char) (((int) plaintext.charAt(i) + shift - 65) % 26 + 65);
                result.append(ch);
            } else {
                char ch = (char) (((int) plaintext.charAt(i) + shift - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result;
    }

    public static StringBuffer decrypt(String encryptedtext, int shift){
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < encryptedtext.length(); i++){
            if (Character.isUpperCase(encryptedtext.charAt(i))){
                char ch = (char) (((int)encryptedtext.charAt(i) - shift - 65 + 26) % 26 + 65);
                result.append(ch);
            } else {
                char ch = (char) (((int)encryptedtext.charAt(i) - shift - 97 + 26) % 26 + 97);
                result.append(ch);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter some text to encrypt: ");
        String plaintext = scanner.next();
        System.out.println("Enter shift value: ");
        int shift = scanner.nextInt();
        scanner.nextLine();

        StringBuffer encryptedText = encrypt(plaintext, shift);
        System.out.println("Encrypted text: " + encryptedText);
        StringBuffer decryptedText = decrypt(String.valueOf(encryptedText), shift);
        System.out.println("Decrypted text: " + decryptedText);
        scanner.close();
    }
}