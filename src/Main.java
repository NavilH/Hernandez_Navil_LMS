
public class Main {
    public static void main(String[] args) {
        boolean isTest = false;
        if(args.length != 0) {
            for(String arg: args) {
                if(!arg.equals("-t") && !arg.equals("--test")) {
                    System.out.println(String.format("Invalid Argument: %s", arg));
                    return;
                }
                isTest = true;
            }
        }
        if(isTest) {
            System.out.println("TEST MODE");
            return;
        }
        LMS librarySystem = new LMS();
        ConsoleManagement consoleManagement = new ConsoleManagement(librarySystem);
        librarySystem.loadBooksFromFile("C:/Users/navil/.jdks/corretto-17.0.8.1/LMS/src/library.txt");
        consoleManagement.run();

    }
}