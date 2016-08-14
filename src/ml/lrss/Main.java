package ml.lrss;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Main {

    public static void main(String[] args) {
        if (args.length >= 1) {
            switch (args[0]) {
                case "calc":
                    //representation calculator
                    Convert.CliConvert();
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
                    System.out.println(
                            "\"" + args[0] + "\" is not a valid argument!");
                    break;
            }
        } else {
            System.out.println(
                    "You need to type an arguments (calc, asm, gate, pipe or cache).");
        }
    }
}