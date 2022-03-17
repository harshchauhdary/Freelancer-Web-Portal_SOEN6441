
package model;


import java.util.*;

public class Project {
    private long id;
    private String description;
    private int readabilityIndex;
    private String educationLevel;
    private List<Job> skills;

    public int getFkglIndex() {
        return fkglIndex;
    }

    public void setFkglIndex(int fkglIndex) {
        this.fkglIndex = fkglIndex;
    }

    private long ownerID;
    private Date timeSubmitted;
    private int fkglIndex;

    public Date getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(Date timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Job> getSkills() {
        return skills;
    }

    public void setSkills(List<Job> skills) {
        this.skills = skills;
    }

    private String type;
    private String title;

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }


    private String name;

    public Project() {
    }

    public Project(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Project(long id, String description, String title, Date timeSubmitted, long ownerID, List<Job> skills, String type) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.timeSubmitted = timeSubmitted;
        this.skills = skills;
        this.ownerID = ownerID;
        this.type = type;
    }

    public long getId() {
        return this.id;
    }

    public String getDesc() {
        return this.description;
    }

    public int getReadabilityIndex() {
        return this.readabilityIndex;
    }

    public void setReadabilityIndex(int index) {
        this.readabilityIndex = index;
    }

    public void setEducationLevel(String level) {
        this.educationLevel = level;
    }

    public String getEducationLevel() {
        return this.educationLevel;
    }


}
