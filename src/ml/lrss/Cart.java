package ml.lrss;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Scanner;

import static java.lang.System.console;

public class Cart {

    public static void main(String[] args) {
        if (args.length >= 1) {
            switch (args[0]) {
                case "calc": //Data representation calculator
                    System.out.println(
                            "Enter a letter prefix and then a value:\n" +
                                    "Prefixes:\n" +
                                    "\tBinary:\t\tB\n" +
                                    "\tHexadecimal:H\n" +
                                    "\tUnsigned:\tU (bit-size)[ex: U 8 255 ]\t\n" +
                                    "\tSigned:\t\tbit-size\t[ex: 8 -128]"
                    );
                    IntConvert.CliConvert(InputArgs());
                    break;
                case "asm":
                    //Todo: assembly translator.
                    throw new NotImplementedException();
                case "gate":
                    //Might be out of scope.
                    throw new NotImplementedException();
                case "pipe":
                    //Might be out of scope.
                    throw new NotImplementedException();
                case "cache":
                    //Todo: Cache accessing simplifier
                    throw new NotImplementedException();
                default:
                    System.out.println("\"" + args[0] + "\" is not a valid argument!");
                    PrintHelp();
                    break;
            }
        } else {
            PrintHelp();
        }
    }

    private static void PrintHelp() {
        System.out.println("You need to type an arguments (calc, asm, gate, pipe or cache).");
    }

    private static String[] InputArgs() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        input = input.toLowerCase();
        return input.split(" ");
    }
}
