package model;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CanvaTest extends TestCase {
    public void testGetTitle() {
        String title="abcd";
        Canva canva=new Canva();
        canva.setTitle(title);
        String result=canva.getTitle();
        assertEquals(title,result);
    }

    public void testSetTitle() {
        String title="abcd";
        Canva canva=new Canva();
        canva.setTitle(title);
        assertEquals(canva.getTitle(),title);
    }

    public void testGetAverageIndex() {
        double index=3.0;
        Canva canva=new Canva();
        canva.setAverageIndex(index);
        double result=canva.getAverageIndex();
        assertEquals(index,result);
    }

    public void testSetAverageIndex() {
        double index=3.0;
        Canva canva=new Canva();
        canva.setAverageIndex(index);
        assertEquals(canva.getAverageIndex(),index);
    }

    public void testGetProjects() {
        List<Project> projects=new ArrayList<>();
        List<Job> jobs=new ArrayList<>();
        Job job=new Job(1234,"abcd");
        Project p=new Project(1234,"abcd","xyz",new Date(),32145,jobs,"pqrs");
        projects.add(p);
        Canva canva=new Canva();
        canva.setProjects(projects);
        List<Project> projects1=canva.getProjects();
        assertEquals(projects,projects1);
    }

    public void testSetProjects() {
        List<Project> projects=new ArrayList<>();
        List<Job> jobs=new ArrayList<>();
        Job job=new Job(1234,"abcd");
        Project p=new Project(1234,"abcd","xyz",new Date(),32145,jobs,"pqrs");
        projects.add(p);
        Canva canva=new Canva();
        canva.setProjects(projects);
        assertEquals(canva.getProjects(),projects);
    }

    public void testCanva(){
        Canva canva=new Canva("abcd",3.0,new ArrayList<>());
        assertEquals("abcd",canva.getTitle());
        assertEquals(3.0,canva.getAverageIndex());

    }

}