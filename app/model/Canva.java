package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Canva {

    private String title;
    private float averageIndex;
    private List<Project> projects;
    private static List<Canva> canvas;

    static{
        canvas = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public static List<Canva> getCanvas() {
        return canvas;
    }

    public static void setCanvasinList(Canva canva) {
        canvas.add(canva);
    }
    public static void clearCanvas(){
        canvas.clear();
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public float getAverageIndex() {
        return averageIndex;
    }

    public void setAverageIndex(float averageIndex) {
        this.averageIndex = averageIndex;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Canva(String title, float averageIndex, List<Project> projects) {
        this.title = title;
        this.averageIndex = averageIndex;
        this.projects = projects;
    }


}
