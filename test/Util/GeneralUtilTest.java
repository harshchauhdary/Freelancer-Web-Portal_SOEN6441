package Util;

import junit.framework.TestCase;
import model.Project;
import org.junit.Before;
import org.junit.Test;
import play.libs.ws.WSClient;
import play.test.WithApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GeneralUtilTest extends TestCase {

    public void testGetExecutor() {

    }

    public void testGetJsonResponseFromUrl() {

    }

    public void testGetProjectsFromJson() throws IOException {

    }

    @Test
    public void testGetDescriptionFromJson() throws Exception{
        String json = getJsonFileAsString(File.separator + "test" + File.separator + "resources" + File.separator + "projects.json");
        List<String> l = new ArrayList<>();
        l.add("description one");
        l.add("description two");
        assertEquals(GeneralUtil.getDescriptionFromJson(json), l);
    }

    public void testGetUserFromJson() {
    }

    public void testGenerateId() {
    }

    public static String getJsonFileAsString(String path) throws IOException {
        String filePath = new File("").getAbsolutePath();
        byte[] encoded = Files.readAllBytes(Paths.get(filePath.concat(path)));

        return new String(encoded, "UTF-8");
    }
}