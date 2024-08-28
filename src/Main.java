import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Farbkodierungsmodus auswählen
        System.out.println("Wählen Sie den Farbkodierungsmodus (RGB oder Hex): ");
        String modus = scanner.nextLine().trim().toLowerCase();

        String farbcode = "";
        boolean gueltigeEingabe = false;

        // Farbcode eingeben und validieren
        if (modus.equals("rgb")) {
            while (!gueltigeEingabe) {
                System.out.println("Geben Sie den Farbcode im Format R,G,B ein (z.B. 255,0,0): ");
                farbcode = scanner.nextLine().trim();
                gueltigeEingabe = validiereRGB(farbcode);
                if (!gueltigeEingabe) {
                    System.out.println("Ungültiger RGB-Farbcode. Bitte versuchen Sie es erneut.");
                }
            }
        } else if (modus.equals("hex")) {
            while (!gueltigeEingabe) {
                System.out.println("Geben Sie den Farbcode im Hex-Format ein (z.B. FF0000): ");
                farbcode = scanner.nextLine().trim();
                gueltigeEingabe = validiereHex(farbcode);
                if (!gueltigeEingabe) {
                    System.out.println("Ungültiger Hex-Farbcode. Bitte versuchen Sie es erneut.");
                }
            }
        } else {
            System.out.println("Ungültiger Modus. Das Programm wird beendet.");
            return;
        }

        // Umwandlung in CMY
        double[] cmyWerte;
        if (modus.equals("rgb")) {
            cmyWerte = rgbToCmy(farbcode);
        } else {
            cmyWerte = hexToCmy(farbcode);
        }

        // Ausgabe des Ergebnisses
        System.out.printf("CMY-Werte: C=%.2f, M=%.2f, Y=%.2f%n", cmyWerte[0], cmyWerte[1], cmyWerte[2]);
    }

    private static boolean validiereRGB(String farbcode) {
        String[] teile = farbcode.split(",");
        if (teile.length != 3) return false;

        try {
            int r = Integer.parseInt(teile[0].trim());
            int g = Integer.parseInt(teile[1].trim());
            int b = Integer.parseInt(teile[2].trim());

            return r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validiereHex(String farbcode) {
        return farbcode.matches("[0-9A-Fa-f]{6}");
    }

    private static double[] rgbToCmy(String farbcode) {
        String[] teile = farbcode.split(",");
        int r = Integer.parseInt(teile[0].trim());
        int g = Integer.parseInt(teile[1].trim());
        int b = Integer.parseInt(teile[2].trim());

        double c = (255.0 - r) / 255.0 * 100;
        double m = (255.0 - g) / 255.0 * 100;
        double y = (255.0 - b) / 255.0 * 100;

        return new double[]{c, m, y};
    }

    private static double[] hexToCmy(String farbcode) {
        int r = Integer.parseInt(farbcode.substring(0, 2), 16);
        int g = Integer.parseInt(farbcode.substring(2, 4), 16);
        int b = Integer.parseInt(farbcode.substring(4, 6), 16);

        double c = (255.0 - r) / 255.0 * 100;
        double m = (255.0 - g) / 255.0 * 100;
        double y = (255.0 - b) / 255.0 * 100;

        return new double[]{c, m, y};
    }
}
