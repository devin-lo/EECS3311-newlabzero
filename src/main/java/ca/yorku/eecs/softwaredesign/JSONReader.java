package ca.yorku.eecs.softwaredesign;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONReader {
    private static Logger LOG = LogManager.getLogger();
    
    public static JSONObject readFile(String fileName) {
        JSONObject content = null;

        // the ClassLoader class can be used to get the resource as an InputStream
        ClassLoader classLoader = JSONObject.class.getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(fileName);

        try {
            // Read the InputStream into a String using Apache Commons IO (wow, we still managed to use that in the new Lab 0!)
            String jsonString = IOUtils.toString(resource, StandardCharsets.UTF_8);
            // Parse the String to JSONObject
            content = new JSONObject(jsonString);
        } catch (IOException e) {
            LOG.error("The file named {} can't be read", fileName);
        } catch (JSONException e) {
            LOG.error("The file named {} was read, but could not be parsed into a JSONObject", fileName);
        } catch (Exception e) {
            LOG.error("Unhandled exception occurred", e);
        }

        return content;
    }
}
