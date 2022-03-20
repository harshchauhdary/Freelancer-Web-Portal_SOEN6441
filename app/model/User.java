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

    /**
     *
     * @return registration date of user
     */

    public long getReg_date() {
        return reg_date;
    }

    /**
     * sets the value of registration date with the one obtained from api
     * @param reg_date obtained from api
     */
    public void setReg_date(long reg_date) {
        this.reg_date = reg_date;
    }

    /**
     * registration date of user
     */

    private long reg_date;

    /**
     *
     * @return limited account info of user
     */
    public boolean getLimited_account() {
        return limited_account;
    }

    /**
     * sets the value of limited account with the one obtained from api
     * @param limited_account obtained from api
     */

    public void setLimited_account(boolean limited_account) {
        this.limited_account = limited_account;
    }

    /**
     * limited account of user
     */

    private boolean limited_account;

    /**
     *
     * @return chosen role of user
     */

    public String getChosen_role() {
        return chosen_role;
    }

    /**
     * sets the value of chosen role with the one obtained from api
     * @param chosen_role obtained from api
     */

    public void setChosen_role(String chosen_role) {
        this.chosen_role = chosen_role;
    }

    /**
     * chosen role of user
     */

    private String chosen_role;

    /**
     *
     * @return country of user
     */

    public String getCountry() {
        return country;
    }

    /**
     * sets the value of country with the one obtained from api
     * @param country value obtained from api
     */

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * country of the user
     */

    private String country;

    /**
     *
     * @return email verified status of user
     */

    public boolean getEmail_verified() {
        return email_verified;
    }

    /**
     * sets the email verified status with the one obtained from api
     * @param email_verified value obtained from api
     */

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    /**
     * email verified status of user
     */

    private boolean email_verified;

    /**
     *
     * @return primary currency value
     */

    public String getPrimary_currency() {
        return primary_currency;
    }

    /**
     * sets the value of primary currency with the one obtained from api
     * @param primary_currency obtained from api
     */

    public void setPrimary_currency(String primary_currency) {
        this.primary_currency = primary_currency;
    }

    /**
     * primary currency value of user
     */

    private String primary_currency;

    /**
     * Creating user based on obtained data
     * @param id id of user
     * @param username username of user
     * @param display_name display name of user
     * @param role role of the user
     * @param reg_date registration date of user
     * @param limited_account limited account info of user
     * @param chosen_role role chosen by user
     * @param country country of user
     * @param email_verified email verified status of user
     * @param primary_currency currency value of user
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

    /**
     * Constructor for test case
     */
    public User(){}
}
