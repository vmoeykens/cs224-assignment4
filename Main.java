import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;

public class Main { 
    public static void main(String[] args) 
    { 
        File lecturesFile = promptFile();
        ArrayList<Lecture> lectures;
        PriorityQueue<Classroom> classroomQueue = new PriorityQueue<Classroom>();
        try {
            lectures = parseFile(lecturesFile);
            runIntervalPartitioning(classroomQueue, lectures);

        } catch (Exception e) {
            System.out.println(e);
        }        
    }

    /**
     * Run the interval paritioning algorithm on the ArrayList of lectures and create Classroom objects to contain them. 
     * Adds these Classroom objects to the priority queue
     * @param classroomQueue
     * @param lectures
     */
    public static void runIntervalPartitioning(PriorityQueue<Classroom> classroomQueue, ArrayList<Lecture> lectures) {

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

    /**
     * Parses the input file and outputs an arraylist of all the Lecture objects created from the file
     * @param lecturesFile file containing list of lectures in format (name, startTime, endTime)
     * @return ArrayList of Lecture objects
     * @throws Exception if lecturesFile does not excist (already checked in promptFile)
     */
    public static ArrayList<Lecture> parseFile(File lecturesFile) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(lecturesFile)); 
        ArrayList<Lecture> outLecs = new ArrayList<Lecture>();
        String line; 
        while ((line = br.readLine()) != null)  {
            Lecture lec = new Lecture(line);
            outLecs.add(lec);
            System.out.println("Adding lecture: " + lec.toString());
        } 
        br.close();
        return outLecs;
    }

}

/**
 * Class representing a classroom
 */
class Classroom implements Comparable<Classroom>{
    int lastFin;
    ArrayList<Lecture> lecs;
    String classroomName;

    /**
     * Classroom constructor; just name, default lastFin to -1 and empty lectures arraylist
     * @param classroomName name of the classroom
     */
    public Classroom(String classroomName) {
        this.classroomName = classroomName;
        this.lastFin = -1;
        this.lecs = new ArrayList<Lecture>(); 
    }

    /**
     * Classroom constructor; name and lastFin (this is mainly for testing pqueue)
     * @param classroomName name of the classroom
     * @param lastFin starting value for lastFin
     */
    public Classroom(String classroomName, int lastFin) {
        this.classroomName = classroomName;
        this.lastFin = lastFin;
        this.lecs = new ArrayList<Lecture>(); 
    }

    /**
     * Classroom constructor; name and list of lectures, lastFin will be calculated from lecture list
     * @param classroomName name of the classroom
     * @param lecs ArrayList of lectures to add to the classroom
     */
    public Classroom(String classroomName, ArrayList<Lecture> lecs) {
        this.classroomName = classroomName;
        this.lecs = lecs;   
        this.lastFin = calculateLastFin();
    }

    /**
     * Calculate the current last finish time from the local arraylist of lectures
     * @return
     */
    int calculateLastFin() {
        return -1;
    }

    /**
     * Add a new lecture to the arraylist of Lectures for the classroom and update the last finish time
     * @param lec Lecture to add
     */
    void addLecture(Lecture lec) {
        lecs.add(lec);
        this.lastFin = calculateLastFin();
    }    

    /**
     * Print all lectures in the classroom
     * @return string with all lectures listed
     */
    String printLectures() {
        String lectures = "";
        for(Lecture lec : this.lecs ){
            lectures += lec.toString() + " ";
        }
        return lectures;
    }

    // Getters and setters

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

    /**
     * Implementation of compareTo method to all for usuage in a Java priority queue
     * @param oClassroom other Classroom to compare to
     * @return difference between this Classroom's and another Classroom's lastFin value
     */
    @Override
    public int compareTo(Classroom oClassroom) {
        return this.getLastFin() - oClassroom.getLastFin();
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
