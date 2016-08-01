package ml.lrss;

public class Main {

    public static void main(String[] args) {

        if (args.length > 1) {
            for (String argument : args) {
                System.out.println("You entered: " + argument);
            }
        } else {
            System.out.println("You need to type arguments");
        }
    }
}
