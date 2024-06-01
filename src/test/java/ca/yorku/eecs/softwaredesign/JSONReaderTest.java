package ca.yorku.eecs.softwaredesign;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class JSONReaderTest {
    private static Logger LOG = LogManager.getLogger();

    /**
     * Please keep in mind that JUnit 5 won't be used in this course. 
     * This is just to give you exposure before you enter the industry.
     * @param fileName
     */
    @ParameterizedTest(name = "{index} Attempt to read {0} into JSONObject")
    @ValueSource(strings = {"sample.json", "whatever.json"})
    void testRead(String fileName) {
        JSONObject read = JSONReader.readFile(fileName);
        assertNotNull(read);
        assertFalse(read.isEmpty());
        LOG.info("Successful read of JSONObject: {}", read);
    }
}