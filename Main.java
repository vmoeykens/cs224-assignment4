import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main { 
    public static void main(String[] args) 
    { 
        // Create a new file object from the prompt. NOTE: We only check to see if the file exists, not if its a .txt or not
        File lecturesFile = promptFile();
        // Create an arraylist to hold the lectures
        ArrayList<Lecture> lectures;
        // Create a priority queue for the classrooms
        PriorityQueue<Classroom> classroomQueue = new PriorityQueue<Classroom>();
        try {
            // Try and parse the file and run the algorithm
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
        // Sort lectures by start time
        Collections.sort(lectures);
        // Set up counters for total number of classrooms
        int classCounter = 1;
        // Add an initial classroom with the lastFin time as the finish time of the first lecture
        Classroom class1 = new Classroom("Classroom " + classCounter, lectures.get(0).getEndTime());
        // Add the first lecture in the list 
        class1.addLecture(lectures.get(0));
        // Add the classroom object to the pqueue
        classroomQueue.add(class1);
        System.out.println(class1);
        // Increment the class counter
        classCounter++;
        for (int i = 1; i < lectures.size(); i++) {
            if (lectures.get(i).getStartTime() >= classroomQueue.peek().getLastFin()) {
                // The current lecture is compatible with the first classroom in the priority queue, add it
                classroomQueue.peek().addLecture(lectures.get(i));
                System.out.println(classroomQueue.peek().getClassroomName() + ": " + lectures.get(i));
            } else {
                // We need a new classroom, create one and add the lecture to it
                Classroom newClass = new Classroom("Classroom " + classCounter, lectures.get(i).getEndTime());
                newClass.addLecture(lectures.get(i));
                // Add the new classroom to the priority queue
                classroomQueue.add(newClass);
                System.out.println(newClass.getClassroomName() + ": " + lectures.get(i));
                classCounter++;
            }
        }
        // Print out all classrooms
        System.out.println("\nAll classrooms and associated lectures: ");
        while (classroomQueue.peek() != null) {
            System.out.println(classroomQueue.poll());
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
            //System.out.println("Adding lecture: " + lec.toString());
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
    }

    /**
     * Add a new lecture to the arraylist of Lectures for the classroom and update the last finish time
     * @param lec Lecture to add
     */
    void addLecture(Lecture lec) {
        lecs.add(lec);
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
class Lecture implements Comparable<Lecture>{
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

    /**
     * Implementation of compareTo method
     * @param oLecture other Lecture to compare to
     * @return difference between this Lecture and another lecture's starting time
     */
    @Override
    public int compareTo(Lecture oLecture) {
        return this.getStartTime() - oLecture.getStartTime();
    }
}
