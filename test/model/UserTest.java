package model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserTest extends TestCase {
    public void testGetRole() {
        String role="employer";
        User user=new User();
        user.setRole(role);
        String result=user.getRole();
        assertEquals(role,result);
    }

    public void testSetRole() {
        String role="employer";
        User user=new User();
        user.setRole(role);
        assertEquals(user.getRole(),role);
    }

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

    public void testGetId() {
        long id=32136579;
        User user=new User();
        user.setId(id);
        long result=user.getId();
        assertEquals(id,result);
    }

    public void testSetId() {
        long id=32136579;
        User user=new User();
        user.setId(id);
        assertEquals(user.getId(),id);
    }

    public void testGetUsername() {
        String uname="san6123";
        User user=new User();
        user.setUsername(uname);
        String result=user.getUsername();
        assertEquals(uname,result);

    }

    public void testSetUsername() {
        String uname="san6123";
        User user=new User();
        user.setUsername(uname);
        assertEquals(user.getUsername(),uname);
    }

    public void testGetDisplay_name() {
        String dname="san6123";
        User user=new User();
        user.setDisplay_name(dname);
        String result=user.getDisplay_name();
        assertEquals(dname,result);
    }

    public void testSetDisplay_name() {
        String dname="san6123";
        User user=new User();
        user.setDisplay_name(dname);
        assertEquals(user.getDisplay_name(),dname);
    }

    public void testGetReg_date() {
        long reg_date=1542651206;
        User user=new User();
        user.setReg_date(reg_date);
        long result=user.getReg_date();
        assertEquals(reg_date,result);
    }

    public void testSetReg_date() {
        long reg_date=1542651206;
        User user=new User();
        user.setReg_date(reg_date);
        assertEquals(user.getReg_date(),reg_date);
    }

    public void testGetLimited_account() {
        boolean acc=false;
        User user=new User();
        user.setLimited_account(acc);
        boolean result=user.getLimited_account();
        assertEquals(acc,result);
    }

    public void testSetLimited_account() {
        boolean acc=false;
        User user=new User();
        user.setLimited_account(acc);
        assertEquals(user.getLimited_account(),acc);
    }

    public void testGetChosen_role() {
        String role="employer";
        User user=new User();
        user.setChosen_role(role);
        String result=user.getChosen_role();
        assertEquals(role,result);
    }

    public void testSetChosen_role() {
        String role="employer";
        User user=new User();
        user.setChosen_role(role);
        assertEquals(user.getChosen_role(),role);
    }

    public void testGetCountry() {
        String country="India";
        User user=new User();
        user.setCountry(country);
        String result=user.getCountry();
        assertEquals(country,result);
    }

    public void testSetCountry() {
        String country="India";
        User user=new User();
        user.setCountry(country);
        assertEquals(user.getCountry(),country);
    }

    public void testGetEmail_verified() {
        boolean email=true;
        User user=new User();
        user.setEmail_verified(email);
        boolean result=user.getEmail_verified();
        assertEquals(email,result);
    }

    public void testSetEmail_verified() {
        boolean email=true;
        User user=new User();
        user.setEmail_verified(email);
        assertEquals(user.getEmail_verified(),email);
    }

    public void testGetPrimary_currency() {
        String currency="Rupee";
        User user=new User();
        user.setPrimary_currency(currency);
        String result=user.getPrimary_currency();
        assertEquals(currency,result);
    }

    public void testSetPrimary_currency() {
        String currency="Rupee";
        User user=new User();
        user.setPrimary_currency(currency);
        assertEquals(user.getPrimary_currency(),currency);
    }
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