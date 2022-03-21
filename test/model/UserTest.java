package model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserTest extends TestCase {

    /**
     * test method for getRole method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetRole() {
        String role="employer";
        User user=new User();
        user.setRole(role);
        String result=user.getRole();
        assertEquals(role,result);
    }

    /**
     * test method for setRole method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetRole() {
        String role="employer";
        User user=new User();
        user.setRole(role);
        assertEquals(user.getRole(),role);
    }
    /**
     * test method for getProjects method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetProjects() {
        List<Project> projects=new ArrayList<>();
        List<Job> jobs=new ArrayList<>();
        Job job=new Job(1234,"abcd");
        Project p=new Project(1234,"abcd","xyz",new Date(),32145,jobs,"pqrs");
        projects.add(p);
        User user=new User();
        user.setProjects(projects);
        List<Project> projects1=user.getProjects();
        assertEquals(projects,projects1);
    }
    /**
     * test method for setProjects method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetProjects() {
        List<Project> projects=new ArrayList<>();
        List<Job> jobs=new ArrayList<>();
        Job job=new Job(1234,"abcd");
        Project p=new Project(1234,"abcd","xyz",new Date(),32145,jobs,"pqrs");
        projects.add(p);
        User user=new User();
        user.setProjects(projects);
        assertEquals(user.getProjects(),projects);
    }
    /**
     * test method for getId method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetId() {
        long id=32136579;
        User user=new User();
        user.setId(id);
        long result=user.getId();
        assertEquals(id,result);
    }
    /**
     * test method for setId method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetId() {
        long id=32136579;
        User user=new User();
        user.setId(id);
        assertEquals(user.getId(),id);
    }
    /**
     * test method for getUsername method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetUsername() {
        String uname="san6123";
        User user=new User();
        user.setUsername(uname);
        String result=user.getUsername();
        assertEquals(uname,result);

    }
    /**
     * test method for setUsername method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetUsername() {
        String uname="san6123";
        User user=new User();
        user.setUsername(uname);
        assertEquals(user.getUsername(),uname);
    }
    /**
     * test method for getDisplay_name method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetDisplay_name() {
        String dname="san6123";
        User user=new User();
        user.setDisplay_name(dname);
        String result=user.getDisplay_name();
        assertEquals(dname,result);
    }
    /**
     * test method for setDisplay_name method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetDisplay_name() {
        String dname="san6123";
        User user=new User();
        user.setDisplay_name(dname);
        assertEquals(user.getDisplay_name(),dname);
    }
    /**
     * test method for getReg_date method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetReg_date() {
        long reg_date=1542651206;
        User user=new User();
        user.setReg_date(reg_date);
        long result=user.getReg_date();
        assertEquals(reg_date,result);
    }
    /**
     * test method for setReg_date method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetReg_date() {
        long reg_date=1542651206;
        User user=new User();
        user.setReg_date(reg_date);
        assertEquals(user.getReg_date(),reg_date);
    }
    /**
     * test method for getLimited_account method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetLimited_account() {
        boolean acc=false;
        User user=new User();
        user.setLimited_account(acc);
        boolean result=user.getLimited_account();
        assertEquals(acc,result);
    }
    /**
     * test method for setLimited_Account method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetLimited_account() {
        boolean acc=false;
        User user=new User();
        user.setLimited_account(acc);
        assertEquals(user.getLimited_account(),acc);
    }
    /**
     * test method for getChosen_Role method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetChosen_role() {
        String role="employer";
        User user=new User();
        user.setChosen_role(role);
        String result=user.getChosen_role();
        assertEquals(role,result);
    }
    /**
     * test method for setChosen_Role method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetChosen_role() {
        String role="employer";
        User user=new User();
        user.setChosen_role(role);
        assertEquals(user.getChosen_role(),role);
    }
    /**
     * test method for getCountry method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetCountry() {
        String country="India";
        User user=new User();
        user.setCountry(country);
        String result=user.getCountry();
        assertEquals(country,result);
    }
    /**
     * test method for setCountry method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetCountry() {
        String country="India";
        User user=new User();
        user.setCountry(country);
        assertEquals(user.getCountry(),country);
    }
    /**
     * test method for getEmail_verified method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetEmail_verified() {
        boolean email=true;
        User user=new User();
        user.setEmail_verified(email);
        boolean result=user.getEmail_verified();
        assertEquals(email,result);
    }
    /**
     * test method for setEmail_verified method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetEmail_verified() {
        boolean email=true;
        User user=new User();
        user.setEmail_verified(email);
        assertEquals(user.getEmail_verified(),email);
    }
    /**
     * test method for getPrimary_Currency method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testGetPrimary_currency() {
        String currency="Rupee";
        User user=new User();
        user.setPrimary_currency(currency);
        String result=user.getPrimary_currency();
        assertEquals(currency,result);
    }
    /**
     * test method for setPrimary_currency method in User class
     * uses set method to pass an argument and then compares both the results using assertEquals method
     */

    public void testSetPrimary_currency() {
        String currency="Rupee";
        User user=new User();
        user.setPrimary_currency(currency);
        assertEquals(user.getPrimary_currency(),currency);
    }
    /**
     * test method for User method in User class
     * passes the values in argument and compares it using assertEquals method
     */
    public void testUser(){
        User user=new User(32136579,"san6123","san6123","employer",1542651206,false,"employer","India",true,"Rupee");
        assertEquals(32136579,user.getId());
        assertEquals("san6123",user.getUsername());
        assertEquals("san6123",user.getDisplay_name());
        assertEquals("employer",user.getRole());
        assertEquals(1542651206,user.getReg_date());
        assertEquals(false,user.getLimited_account());
        assertEquals("employer",user.getChosen_role());
        assertEquals("India",user.getCountry());
        assertEquals(true,user.getEmail_verified());
        assertEquals("Rupee",user.getPrimary_currency());
    }
}