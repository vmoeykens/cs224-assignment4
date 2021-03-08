import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

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

/**
 * Class representing a classroom
 */
class Classroom {
    int lastFin;

/** 
 * Class representing a lecture
 */
class Lecture {
    String lectureName;
    int startTime;
    int endTime;

    /**
     * Lecture constructor
     * @param lectureName a String that is used to name the lecture
     * @param startTime the lecture start time as an integer
     * @param endTime the lecture end time as an integer
     */
    public Lecture(String lectureName, int startTime, int endTime) {
        this.lectureName = lectureName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Generate Lecture object from a string pulled from a file in the format:
     * '(lectureName, startTime, endTime)'
     * @param fileLine the string representing the file
     */
    public Lecture(String fileLine) {
        ArrayList<String> lectureData = new ArrayList<>(Arrays.asList(fileLine.split(",[ ]*")));
        this.lectureName = lectureData.get(0);
        this.startTime = Integer.parseInt(lectureData.get(1));
        this.endTime = Integer.parseInt(lectureData.get(2));
    }

    // Getters and setters
    public String getLectureName() {
        return this.lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "(" +
            getLectureName() + ", " +
            getStartTime() + ", " +
            getEndTime() +
            ")";
    }
}

}