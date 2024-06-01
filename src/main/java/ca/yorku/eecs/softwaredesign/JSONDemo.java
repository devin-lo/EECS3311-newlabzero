package ca.yorku.eecs.softwaredesign;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class JSONDemo {
    private static Logger LOG = LogManager.getLogger();
    public static void main(String[] args) {
        LOG.trace("You can't see this message");    // due to the logging level in log4j2.xml, this line won't be visible in the output
        String fileName = null;
        if (args.length > 0) {
            fileName = args[0];
        } else {
            fileName = "sample.json";
        }
        LOG.info("Filename given was {}", fileName);
        JSONObject parsed = JSONReader.readFile(fileName);
        if (parsed != null) {
            LOG.info("Parsed JSONObject was {}", parsed);
        }
    }
}