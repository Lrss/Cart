package ml.lrss;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("calc"))
                calc();
            else
                System.out.println(args[0] + " Is not a valid argument");
        } else {
            System.out.println("You need to type an arguments");
        }
    }

    private static void calc() {
        System.out.println("Enter a letter prefix and an 8bit value:\nB(inary), H(exadecimal), U(nsigned) or S(igned)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input = input.toLowerCase();
        String[] parts = input.split(" ");

        int Signed;
        int USigned;

        switch (parts[0]) {
            case "b":
                if (parts.length > 2)
                    for (int i = 2; i < parts.length; i++) {
                        parts[1] += parts[i];
                    }
                USigned = Integer.parseInt(parts[1], 2);
                Signed = UnSignedToSigned(USigned);
                break;
            case "h":
                if (!parts[1].substring(0, 2).equals("0x")) //checks for hex prefix
                    parts[1] = "0x" + parts[1];
                USigned = Integer.decode(parts[1]);//converts hex
                Signed = UnSignedToSigned(USigned);
                break;
            case "u":
                USigned = Integer.parseInt(parts[1]);
                Signed = UnSignedToSigned(USigned);
                break;
            default:
                Signed = Integer.parseInt(parts.length > 1 ? parts[1] : parts[0]);
                USigned = (Signed < 0 ? Signed + 256 : Signed);
                break;
        }
        if (Signed > 127 || Signed < -128) {
            System.out.println("The entered value (" + Signed + ") is larger than 8 bits.");
        } else {

            String binaryStringValue = SignedToBinary(Signed);
            System.out.println(
                    "Binary: " + binaryStringValue.substring(0, 4) + " " + binaryStringValue.substring(4, 8) +
                            ", Hexadecimal: " + SignedToHexadecimal(Signed) +
                            ", Unsigned: " + USigned +
                            ", Signed: " + Signed
            );
        }
    }

    private static int UnSignedToSigned(int unSigned) {
        return (unSigned > 255 || unSigned < 0) ? //is the number more than 8 bits
                666 ://an invalid number
                (unSigned > 127 ? //is convertion necessary?
                        unSigned - 256 : //convert to negative signed value
                        unSigned
                );
    }

    private static String SignedToBinary(int Signed) {
        String binaryString = Integer.toBinaryString(Signed);//converting
        if (binaryString.length() > 8)
            binaryString = binaryString.substring(binaryString.length() - 8, binaryString.length());//removing trailing 1's
        else if (binaryString.length() < 8) {
            for (int i = binaryString.length(); i < 8; i++) {
                binaryString = "0" + binaryString;
            }
        }
        return binaryString;
    }

    private static String SignedToHexadecimal(int Signed) {
        String hexaString = Integer.toHexString(Signed);//converting
        if (hexaString.length() > 2) {
            hexaString = hexaString.substring(hexaString.length() - 2, hexaString.length());//removing trailing f's
        } else if (hexaString.length() < 2)
            hexaString = "0" + hexaString;

        return "0x" + hexaString;
    }
}
//http://www.electronics.dit.ie/staff/tscarff/signed_numbers/signed_numbers.htm