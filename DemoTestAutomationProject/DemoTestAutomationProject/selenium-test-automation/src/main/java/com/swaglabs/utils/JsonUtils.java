package com.swaglabs.utils;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

/**
 * JsonUtils
 *
 * Utility class used for reading test data from JSON files.
 * Supports JsonPath expressions for flexible data retrieval.
 */
public class JsonUtils {

    // Base path for JSON test data files
    private static final String JSON_FILE_PATH = "src/test/resources/";

    private String jsonReader;
    private String jsonFileName;

    /**
     * Loads JSON file into memory.
     *
     * @param jsonFileName file name without extension
     */
    public JsonUtils(String jsonFileName) {
        this.jsonFileName = jsonFileName;
        try {
            JSONObject data = (JSONObject) new JSONParser()
                    .parse(new FileReader(JSON_FILE_PATH + jsonFileName + ".json"));
            jsonReader = data.toJSONString();
        } catch (Exception e) {
            LogsUtil.error(e.getMessage());
        }
    }

    /**
     * Retrieves value from JSON using JsonPath.
     *
     * @param jsonPath JsonPath expression
     * @return value from JSON
     */
    public String getJsonData(String jsonPath) {
        String testData = "";
        try {
            testData = JsonPath.read(jsonReader, jsonPath);
        } catch (Exception e) {
            LogsUtil.error("No value for json path: " + jsonPath);
        }
        return testData;
    }
}
