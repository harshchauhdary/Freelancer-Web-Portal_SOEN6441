package model;

import java.util.ArrayList;
import java.util.List;

/**
 *Represents the canva which contains the title ,average readability index and list of 10 projects based on it
 * @author Sahil
 */
public class Canva {
    /**
     * Title query which the user searched for
     */
    private String title;
    /**
     * Average Readability index of all 10 projects retrieved based on the title
     */
    private float averageIndex;
    /**
     * Latest active 10 projects retrieved from the api based on the title
     */
    private List<Project> projects;

    /**
     * List of all canvas searched by the user
     */
    private static List<Canva> canvas;

    static{
        canvas = new ArrayList<>();
    }

    /**
     * Gets the title of canva
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets list of all canvas searched by the user
     * @return
     */
    public static List<Canva> getCanvas() {
        return canvas;
    }

    /**
     * Adds the canva created to the static list of all Canvas
     * @param canva
     */
    public static void setCanvasinList(Canva canva) {
        canvas.add(canva);
    }

    /**
     * Clears the canvas list when the size of Canvas reaches 10
     */
    public static void clearCanvas(){
        canvas.clear();
    }

    /**
     * Sets the title of canva
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the averageRedability index of all the 10 canvas
     * @return
     */
    public float getAverageIndex() {
        return averageIndex;
    }
    /**
     * Sets the averageRedability index of all the 10 canvas
     * @param averageIndex
     */
    public void setAverageIndex(float averageIndex) {
        this.averageIndex = averageIndex;
    }

    /**
     * Gets the list of 10 active projects based on title
     * @return
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * Sets the list of 10 active projects in the canva based on the title
     * @param projects
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Constructs the canva
     * @param title query searched by the user
     * @param averageIndex average Readability index of all 10 projects
     * @param projects List of all active 10 projects based on the query
     */
    public Canva(String title, float averageIndex, List<Project> projects) {
        this.title = title;
        this.averageIndex = averageIndex;
        this.projects = projects;
    }


}
