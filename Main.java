import java.io.File;
import java.util.Scanner;

public class Main { 
    public static void main(String[] args) 
    { 
        File lecturesFile = promptFile();
    }

    /**
     * Prompts the user for a file and will loop until a valid filename is inetered
     * @return File object from the filename the user entered
     */
    public static File promptFile() {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a filename for input file.");
        String fileName = in.nextLine();
        File lecturesFile = new File(fileName);
        
        while(!lecturesFile.exists()){
            System.out.println("Invalid file name! Try again.");
            fileName = in.nextLine();    
            lecturesFile = new File(fileName);
        }
        in.close();
        return lecturesFile;
    }
}