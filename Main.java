import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main { 
    public static void main(String[] args) 
    { 
        File lecturesFile = promptFile();
        try {
            parseFile(lecturesFile);
        } catch (Exception e) {
            System.out.println(e);
        }
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

    public static void parseFile(File lecturesFile) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(lecturesFile)); 
        Classroom newClass = new Classroom("Classroom 1");
        
        String line; 
        while ((line = br.readLine()) != null)  {
            Lecture lec = new Lecture(line);
            newClass.addLecture(lec);
            System.out.println("Adding lecture: " + lec.toString());
        } 
        System.out.println(newClass.toString());
        br.close();
    }

}

/**
 * Class representing a classroom
 */
class Classroom {
    int lastFin;
    ArrayList<Lecture> lecs;
    String classroomName;

    public Classroom(String classroomName) {
        this.classroomName = classroomName;
        this.lastFin = -1;
        this.lecs = new ArrayList<Lecture>(); 
    }

    public Classroom(String classroomName, ArrayList<Lecture> lecs) {
        this.classroomName = classroomName;
        this.lecs = lecs;   
        this.lastFin = -1;
    }

    int calculateLastFin() {
        return -1;
    }

    void addLecture(Lecture lec) {
        lecs.add(lec);
        this.lastFin = calculateLastFin();
    }    

    String printLectures() {
        String lectures = "";
        for(Lecture lec : this.lecs ){
            lectures += lec.toString() + " ";
        }
        return lectures;
    }

    public int getLastFin() {
        return this.lastFin;
    }

    public ArrayList<Lecture> getLecs() {
        return this.lecs;
    }

    public String getClassroomName() {
        return this.classroomName;
    }

    @Override
    public String toString() {
        return getClassroomName() + ": " + printLectures();
    }


}

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
        String strippedLine = fileLine.replaceAll("['('')']", "");
        ArrayList<String> lectureData = new ArrayList<>(Arrays.asList(strippedLine.split(",[ ]*")));
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Lecture)) {
            return false;
        }
        Lecture lecture = (Lecture) o;
        return Objects.equals(lectureName, lecture.lectureName) && startTime == lecture.startTime && endTime == lecture.endTime;
    }
}
