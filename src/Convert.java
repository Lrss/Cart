import java.util.Scanner;

class Convert {

    static void CliConvert() {
        System.out.println(
                "Enter a letter prefix and an value:\n" +
                        "Prefixes:\n" +
                        "\tB(inary), \n" +
                        "\tH(exadecimal), \n" +
                        "\tU(nsigned) (bit-size) or \n" +
                        "\tbit-size (ex: 8 -128)"
        );
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        input = input.toLowerCase();
        String[] parts = input.split(" ");

        long Signed;
        long USigned;
        int BitSize;
        switch (parts[0]) {
            case "b":
                if (parts.length > 2) for (int i = 2; i < parts.length; i++) parts[1] += parts[i];

                BitSize = parts[1].length();
                USigned = Long.parseLong(parts[1], 2);
                Signed = USignedToSigned(BitSize, USigned);
                break;
            case "h": //Page 36 Chapter 2 "Representing and Manipulating Information" for manual solution. 
                if (parts[1].length() <= 2 || !parts[1].substring(0, 2).equals("0x")) //checks for hex prefix
                    parts[1] = "0x" + parts[1];

                BitSize = (parts[1].length() - 2) * 4;
                USigned = HexToUSigned(parts[1].substring(2,parts[1].length()));
                Signed = USignedToSigned(BitSize, USigned);
                break;
            case "u":
                if (parts.length == 2) {
                    USigned = Long.parseLong(parts[1]);
                    if (USigned < 0) throw new IllegalArgumentException("Unsigned value can not be less than 0");
                    BitSize = 1;
                    if (USigned > 1) {
                        BitSize = 2;
                        while (USigned > Math.pow(2, BitSize) - 1) {
                            BitSize *= 2;
                        }
                    }
                }
                else if (parts.length == 3) {
                    USigned = Long.parseLong(parts[2]);
                    if (USigned < 0) throw new IllegalArgumentException("Unsigned value can not be less than 0");
                    BitSize = Integer.parseInt(parts[1]);
                }
                else {
                    throw new IllegalArgumentException("Unsigned takes only one or two arguments.");
                }
                Signed = USignedToSigned(BitSize, USigned);
                break;
            default:
                if (parts.length == 2) {
                    BitSize = Integer.parseInt(parts[0]);
                    Signed = Long.parseLong(parts[1]);

                    long maxValues = (long) Math.pow(2, BitSize);

                    if (Signed > maxValues / 2 - 1 || Signed < maxValues / 2 * -1 ){
                        throw new IllegalArgumentException("Bit Size is not large enough!");
                    }

                }
                else {
                    throw new IllegalArgumentException(
                                    "Signed takes only two arguments, " +
                                    "a bit size and a Signed value. "
                    );
                }
                USigned = (Signed < 0 ? Signed + (long) Math.pow(2, BitSize) : Signed);
                break;
        }
        System.out.print("The entered value requires at least " + BitSize + " bits");
        int missing = BitSize % 4;
        if (missing != 0)
            System.out.println(" (" + (BitSize + 4 - missing) + " bits for the Hexadecimal)");
        else
            System.out.println();


        String binaryStringValue = SignedToBinary(BitSize, Signed);

        int maxLength = binaryStringValue.length();
        int splitPoint = maxLength - 4;
        while (splitPoint > 0)
        {
            binaryStringValue =
                    binaryStringValue.substring(0, splitPoint) +
                            " " +
                            binaryStringValue.substring(splitPoint, maxLength);
            splitPoint -= 4;
            maxLength++;//compensating for new whitespace.
        }

        System.out.println(
                "Binary: "          + binaryStringValue +
                        ", Hexadecimal: "   + USignedToHexadecimal(BitSize, USigned) +
                        ", Unsigned: "      + USigned +
                        ", Signed: "        + Signed
        );
    }

    private static long HexToUSigned(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        long val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }

    //http://www.electronics.dit.ie/staff/tscarff/signed_numbers/signed_numbers.htm
    private static long USignedToSigned(int bitSize, long unSigned) {
        long maxValue = (long) Math.pow(2, bitSize);
        return unSigned > maxValue / 2 - 1 ? //is conversion necessary?
                unSigned - maxValue : //convert to negative signed value
                unSigned; //value is same as signed.
    }
    private static String SignedToBinary(int bitSize, long Signed) {//http://kias.dyndns.org/comath/11.html
        String binaryString = Long.toBinaryString(Signed);//converting
        if (binaryString.length() > bitSize)
            binaryString = binaryString.substring(binaryString.length() - bitSize, binaryString.length());//removing trailing 1's
        else if (binaryString.length() < bitSize) {
            for (int i = binaryString.length(); i < bitSize; i++) {
                binaryString = "0" + binaryString;
            }
        }
        return binaryString;
    }
    private static String USignedToHexadecimal(int bitSize, long USigned) {
        String hexaString = Long.toHexString(USigned);//converting
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
