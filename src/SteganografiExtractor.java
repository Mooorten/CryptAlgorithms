import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class SteganografiExtractor {

    // Funktion til at udtrække beskeden fra et billede
    public static String decodeMessageFromImage(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));

        if (image == null) {
            System.out.println("Kunne ikke læse billedet.");
            return "";
        }

        StringBuilder messageBinary = new StringBuilder();

        // Læs LSB fra hvert pixel
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                messageBinary.append(pixel & 1); // Udtræk LSB (0 eller 1)
            }
        }

        // Konverter binær streng til almindelig tekst
        String message = binaryToString(messageBinary.toString());

        // Find afslutningen af beskeden markeret med "###"
        int endIndex = message.indexOf("###");
        if (endIndex != -1) {
            return message.substring(0, endIndex); // Returner beskeden før ###
        } else {
            return "Besked ikke fundet";
        }
    }

    // Funktion til at konvertere en binær streng til tekst
    private static String binaryToString(String binary) {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i + 7 < binary.length(); i += 8) {
            String byteString = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteString, 2);
            message.append((char) charCode);
        }
        return message.toString();
    }

    // Hovedmetode til at køre dekodning (udtrækning af besked)
    public static void main(String[] args) {
        try {
            // Brug relativ sti til billedet med den skjulte besked
            String imagePath = "output_image.png";  // Output billede med skjult besked

            // Udtræk beskeden fra billedet
            String decodedMessage = decodeMessageFromImage(imagePath);
            System.out.println("Den udtrukne besked er: " + decodedMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
