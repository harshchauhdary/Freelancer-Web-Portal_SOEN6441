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

    /**
     *
     * @return the role of the user it can be employee,freelancer...
     */
    public String getRole() {
        return role;
    }

    /**
     * sets the value of role variable to the data obtained
     * @param role obtained from data stored in api
     */

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * role of the user
     */

    private String role;

    /**
     *
     * @return list of user's projects
     */

    public List<Project> getProjects() {
        return projects;
    }

    /**
     * stores the value obtained from api
     * @param projects list of projects obtained from data stored in api
     */

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * list of the projects performed by user
     * @see Project
     */

    private List<Project> projects;

    /**
     *
     * @return the id of user
     */

    public long getId() {
        return id;
    }

    /**
     * sets the value of id with the one obtained from api
     * @param id obtained from api
     */

    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return username of the user
     */

    public String getUsername() {
        return username;
    }

    /**
     *sets the value of username with the one obtained from api
     * @param username obtained from api
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return display name of the user
     */

    public String getDisplay_name() {
        return display_name;
    }

    /**
     *sets the value of display name with the one obtained from api
     * @param display_name obtained from api
     */

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public long getReg_date() {
        return reg_date;
    }

    public void setReg_date(long reg_date) {
        this.reg_date = reg_date;
    }

    private long reg_date;

    public boolean getLimited_account() {
        return limited_account;
    }

    public void setLimited_account(boolean limited_account) {
        this.limited_account = limited_account;
    }

    private boolean limited_account;

    public String getChosen_role() {
        return chosen_role;
    }

    public void setChosen_role(String chosen_role) {
        this.chosen_role = chosen_role;
    }

    private String chosen_role;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String country;

    public boolean getEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    private boolean email_verified;

    public String getPrimary_currency() {
        return primary_currency;
    }

    public void setPrimary_currency(String primary_currency) {
        this.primary_currency = primary_currency;
    }

    private String primary_currency;

    /**
     * Creating user based on obtained data
     * @param id id of user
     * @param username username of user
     * @param display_name display name of user
     * @param role role of the user
     */

    public User(long id, String username, String display_name,String role,long reg_date,boolean limited_account,String chosen_role,String country,boolean email_verified,String primary_currency){
        this.id=id;
        this.username=username;
        this.display_name=display_name;
        this.role=role;
        this.reg_date=reg_date;
        this.limited_account=limited_account;
        this.chosen_role=chosen_role;
        this.country=country;
        this.email_verified=email_verified;
        this.primary_currency=primary_currency;
    }
}
