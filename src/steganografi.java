import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;

public class steganografi {

    // Funktion til at skjule beskeden i et billede
    public static void encodeMessageInImage(String imagePath, String message, String outputImagePath) throws IOException {
        // Brug ClassLoader til at hente ressourcen fra src-mappen
        ClassLoader classLoader = steganografi.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(imagePath);

        if (inputStream == null) {
            System.out.println("Filen blev ikke fundet: " + imagePath);
            return;
        }

        BufferedImage image = ImageIO.read(inputStream);
        if (image == null) {
            System.out.println("Kunne ikke læse billedet. Filtypen kan være ukendt eller beskadiget.");
            return;
        }

        // Tilføj en afslutning på beskeden, så vi ved, hvor den slutter
        message += "###";
        String messageBinary = toBinaryString(message);

        int bitIndex = 0;

        // Gå igennem pixels og skjul beskedens bits i LSB (mindst betydende bit)
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);

                if (bitIndex < messageBinary.length()) {
                    int modifiedPixel = modifyLSB(pixel, messageBinary.charAt(bitIndex));
                    image.setRGB(x, y, modifiedPixel);
                    bitIndex++;
                }
            }
        }

        // Gem det ændrede billede
        ImageIO.write(image, "png", new File(outputImagePath));
        System.out.println("Beskeden er skjult i billedet og gemt som: " + outputImagePath);
    }

    // Funktion til at ændre LSB af en pixel
    private static int modifyLSB(int pixel, char bit) {
        if (bit == '1') {
            return pixel | 1; // Sæt LSB til 1
        } else {
            return pixel & ~1; // Sæt LSB til 0
        }
    }

    // Funktion til at konvertere en streng til binær
    private static String toBinaryString(String message) {
        StringBuilder binary = new StringBuilder();
        for (char c : message.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(c)).replaceAll(" ", "0"));
        }
        return binary.toString();
    }

    // Hovedmetode til at køre programmet
    public static void main(String[] args) {
        try {
            // Brug relativ sti (filen ligger i src-mappen)
            String imagePath = "bigv.png";  // Input billede, sørg for at denne fil ligger i src-mappen
            String outputImagePath = "output_image.png"; // Output billede med skjult besked
            String secretMessage = "Diaco Lugter";

            // Skjul beskeden i billedet
            encodeMessageInImage(imagePath, secretMessage, outputImagePath);
            System.out.println("Beskeden er skjult i billedet.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
