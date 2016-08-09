package ml.lrss;

public class Main {

    public static void main(String[] args) {
        if (args.length >= 1) {
            if (args[0].equals("calc"))
                Convert.CliConvert();
            else
                System.out.println(args[0] + " Is not a valid argument");
        } else {
            System.out.println("You need to type an arguments");
        }
    }
}