
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

    /**
     *Getter Method to access the fkgl Index, of the type Int
     * @see <a href="https://www.google.com/url?q=https://en.wikipedia.org/wiki/Flesch%25E2%2580%2593Kincaid_readability_tests&sa=D&source=editors&ust=1647564305226163&usg=AOvVaw3bwQ9Dl_E-VdqhapgkmnBC">FKGL</a>
     *@return fkglIndex
     */
    public int getFkglIndex() {
        return fkglIndex;
    }

    /**
     * Setter Method, to assign the value to the fkgl Index.
     * @see <a href="https://www.google.com/url?q=https://en.wikipedia.org/wiki/Flesch%25E2%2580%2593Kincaid_readability_tests&sa=D&source=editors&ust=1647564305226163&usg=AOvVaw3bwQ9Dl_E-VdqhapgkmnBC">FKGL</a>
     * @param fkglIndex Integer value which shall be assigned to the fkglIndex
     */
    public void setFkglIndex(int fkglIndex) {
        this.fkglIndex = fkglIndex;
    }

    /**
     * Getter Method, used to obtain the Time at which the project was Submitted.
     * @return Submission Time of the project in the Date Format
     */

    public Date getTimeSubmitted() {
        return timeSubmitted;
    }

    /**
     * Setter Method used to assign the Time Submission value to the necessary parameter.
     * @param timeSubmitted Submission Time of the project, in Date Format
     */
    public void setTimeSubmitted(Date timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }

    /**
     * Getter Method, used to obtain the Title of a Project
     * @return Title of the project in String Format.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter Method, used to assign the Title of aProject.
     * @param title String containing the title value of the Project.
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter Method, used to obtain the List of Skills.
     * @return The list of Skills, of type Job
     */
    public List<Job> getSkills() {
        return skills;
    }

    /**
     * Setter Method, used to assign value to the Skills List
     * @param skills List of Skills, of type Job.
     */
    public void setSkills(List<Job> skills) {
        this.skills = skills;
    }


    /**
     * Getter Method, used to obtain the unique OwnerID of the Project Master.
     * @return Unique ID of the Project Master, of type Long
     */
    public long getOwnerID() {
        return ownerID;
    }

    /**
     * Setter Method, used to assign value to OwnerID
     * @param ownerID Unique ID of Project Master, of type Long
     */
    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * Blank Constructor of Project Class
     */
    public Project() {
    }

    /**
     * Getter Method, to obtain the type of the Project (Fixed/Hourly)
     * @return type of the project
     */

    public String getType() {
        return type;
    }

    /**
     * Setter Method, used to assign value to type of the Project
     * @param type value of type
     */

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

    /**
     * Getter Method, used to obtain the Unique ID of the Project
     * @return Unique ID of the project.
     */
    public long getId() {
        return this.id;
    } /**
     * Getter Method, used to obtain the Unique ID of the Project
     * @return Unique ID of the project.
     */
    public void setId(long id) {
         this.id = id;
    }
    /**
     * Getter Method, used to obtain the description of the project
     * @return Description of the Project
     */
    public String getDesc() {
        return this.description;
    }
    /**
     * Getter Method, used to obtain the description of the project
     * @return Description of the Project
     */
    public void setDesc(String desc) {
         this.description = desc;
    }
    /**
     * Getter Method, used to obtain the readability Indices of the project
     * @return Readability Index of the project
     * @see <a href="https://www.google.com/url?q=http://users.csc.calpoly.edu/~jdalbey/305/Projects/FleschReadabilityProject.html&sa=D&source=editors&ust=1647564305219750&usg=AOvVaw0IOgagGnM1UmYzi7T4jVRa">Flesch Readability Index</a>
     */
    public int getReadabilityIndex() {
        return this.readabilityIndex;
    }

    /**
     * Setter Method, used to assign the Readability Indices of the projects
     * @param index Readability Index of type int
     * @see <a href="https://www.google.com/url?q=http://users.csc.calpoly.edu/~jdalbey/305/Projects/FleschReadabilityProject.html&sa=D&source=editors&ust=1647564305219750&usg=AOvVaw0IOgagGnM1UmYzi7T4jVRa">Flesch Readability Index</a>
     */

    public void setReadabilityIndex(int index) {
        this.readabilityIndex = index;
    }

    /**
     * Setter Method, used to assign the education level required for the projects.
     * @param level Education level of type String
     */
    public void setEducationLevel(String level) {
        this.educationLevel = level;
    }

    /**
     * Getter Method, used to obtain the education level required for the projects.
     * @return Education level required for the project.
     */

    public String getEducationLevel() {
        return this.educationLevel;
    }


}
