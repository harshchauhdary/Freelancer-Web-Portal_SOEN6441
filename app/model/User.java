package model;

import java.util.List;

/**
 * @author Bhargav Bhutwala 40196468
 * This class is used in obtaining all the information about the user and their projects, which are in
 *  turn stored in JSon File and then parsed using json parser.
 */

public class User {

    /**
     * id of the user
     */
    private long id;
    /**
     * username of the user
     */
    private String username;
    /**
     * display name of the user
     */
    private String display_name;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String role;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    private List<Project> projects;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public User(long id, String username, String display_name,String role){
        this.id=id;
        this.username=username;
        this.display_name=display_name;
        this.role=role;
    }
}
