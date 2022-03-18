
package model;


import java.util.*;

/**
 * Represents the project entity abtained from the freelancer api
 *
 * @author Sahil Munj
 */
public class Project {
    /**
     * Id of project
     */
    private long id;
    /**
     * Description of project
     */
    private String description;
    /**
     * Readability Index of projects Description
     *
     * @see <a href="https://www.google.com/url?q=http://users.csc.calpoly.edu/~jdalbey/305/Projects/FleschReadabilityProject.html&sa=D&source=editors&ust=1647564305219750&usg=AOvVaw0IOgagGnM1UmYzi7T4jVRa">Flesch Readability Index</a>
     */
    private int readabilityIndex;
    /**
     * Education level required for user to understand the project description
     * @see <a href="https://www.google.com/url?q=http://users.csc.calpoly.edu/~jdalbey/305/Projects/FleschReadabilityProject.html&sa=D&source=editors&ust=1647564305219750&usg=AOvVaw0IOgagGnM1UmYzi7T4jVRa">Flesch Readability Index</a>
     */
    private String educationLevel;
    /**
     * List of skills required for the job
     * @see Job
     */
    private List<Job> skills;
    /**
     * Id of the owner who created the project
     */
    private long ownerID;
    /**
     * time when the project was submitted
     */
    private Date timeSubmitted;
    /**
     * FKGL index of the description of project
     * @see <a href="https://www.google.com/url?q=https://en.wikipedia.org/wiki/Flesch%25E2%2580%2593Kincaid_readability_tests&sa=D&source=editors&ust=1647564305226163&usg=AOvVaw3bwQ9Dl_E-VdqhapgkmnBC">FKGL</a>
     */
    private int fkglIndex;
    /**
     * Type of project
     */
    private String type;
    /**
     * Title of project
     */
    private String title;


    public int getFkglIndex() {
        return fkglIndex;
    }
    public void setFkglIndex(int fkglIndex) {
        this.fkglIndex = fkglIndex;
    }

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



    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }

    public Project() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Creating project based on the given details
     * @param id id of project
     * @param description description of project
     * @param title title of project
     * @param timeSubmitted time when the project was submitted
     * @param ownerID id of owner who created the project
     * @param skills skills required for the project
     * @param type type of project
     */
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
