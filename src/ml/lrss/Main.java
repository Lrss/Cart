package ml.lrss;

import com.sun.xml.internal.ws.util.StringUtils;

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
        System.out.println("Enter a letter prefix and an 8bit value:\nB(inary), H(exadecimal), U(nsigned) or bit value (signed)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input = input.toLowerCase();
        String[] parts = input.split(" ");

        int Signed;
        int USigned;
        int BitSize;
        switch (parts[0]) {
            case "b":
                if (parts.length > 2) for (int i = 2; i < parts.length; i++) parts[1] += parts[i];

                BitSize = parts[1].length();
                USigned = Integer.parseInt(parts[1], 2);
                Signed = UnSignedToSigned(BitSize, USigned);
                break;
            case "h":
                if (parts[1].length() <= 2 || !parts[1].substring(0, 2).equals("0x")) //checks for hex prefix
                    parts[1] = "0x" + parts[1];

                BitSize = (parts[1].length() - 2) * 4;
                USigned = Integer.decode(parts[1]);//converts hex
                Signed = UnSignedToSigned(BitSize, USigned);
                break;
            case "u":
                USigned = Integer.parseInt(parts[1]);
                BitSize = 1;
                if (USigned > 1) {
                    BitSize = 2;
                    while (USigned > Math.pow(2, BitSize) - 1) {
                        BitSize *= 2;
                    }
                }
                Signed = UnSignedToSigned(BitSize, USigned);
                break;
            default:
                BitSize = Integer.parseInt(parts[0]);
                Signed = Integer.parseInt(parts[1]);
                USigned = (Signed < 0 ? Signed + (int) Math.pow(2, BitSize) : Signed);
                break;
        }
        System.out.println("The entered value requires " + BitSize + " bits");

        String binaryStringValue = SignedToBinary(BitSize, Signed);

        String binaryWithSpaces = "";
        for (int i = 0; i < binaryStringValue.length(); i += 4) {//Todo:Should work with binaries not divisible by 4.
            binaryWithSpaces += " " + binaryStringValue.substring(i, i + 4);
        }

        System.out.println(
                "Binary:" + binaryWithSpaces +
                        ", Hexadecimal: " + USignedToHexadecimal(BitSize, USigned) +
                        ", Unsigned: " + USigned +
                        ", Signed: " + Signed
        );
    }

    private static int UnSignedToSigned(int bitSize, int unSigned) {
        int maxValue = (int) Math.pow(2, bitSize);
        return unSigned > maxValue / 2 - 1 ? //is conversion necessary?
                unSigned - maxValue : //convert to negative signed value
                unSigned; //value is same as signed.
    }
    //http://kias.dyndns.org/comath/11.html

    private static String SignedToBinary(int bitSize, int Signed) {
        String binaryString = Integer.toBinaryString(Signed);//converting
        if (binaryString.length() > bitSize)
            binaryString = binaryString.substring(binaryString.length() - bitSize, binaryString.length());//removing trailing 1's
        else if (binaryString.length() < bitSize) {
            for (int i = binaryString.length(); i < bitSize; i++) {
                binaryString = "0" + binaryString;
            }
        }
        return binaryString;
    }

    private static String USignedToHexadecimal(int bitSize, int USigned) {
        String hexaString = Integer.toHexString(USigned);//converting
        int bitsOfFour = (bitSize + 4 - 1) / 4;

        if (hexaString.length() > bitsOfFour) {
            hexaString = hexaString.substring(hexaString.length() - bitsOfFour, hexaString.length());
        } else if (hexaString.length() < bitsOfFour) {
            while (hexaString.length() < bitsOfFour)
                hexaString = "0" + hexaString;
        }

        return "0x" + hexaString;
    }
}
//http://www.electronics.dit.ie/staff/tscarff/signed_numbers/signed_numbers.htm