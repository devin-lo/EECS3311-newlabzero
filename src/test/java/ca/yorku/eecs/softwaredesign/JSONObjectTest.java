package ca.yorku.eecs.softwaredesign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * This test class was created to demonstrate ParameterizedTest and RepeatedTest in JUnit 5
 * This course won't use JUnit 5, but we are giving you exposure before you enter the industry.
 */

public class JSONObjectTest {
    private static Logger LOG = LogManager.getLogger();

    // instead of repeating similar code, we can use ParameterizedTest
	// to specify a list of parameters we could hand to a test method to run several times

	// ValueSource allows you to specify several different values of certain types (mostly primitives)
    // refer to the JUnit documentation for more information: https://junit.org/junit5/docs/current/api/org.junit.jupiter.params/org/junit/jupiter/params/provider/ValueSource.html
    @ParameterizedTest
	@ValueSource(ints = {1, -3})
	void testWithValueSource(int insertVal) {
		JSONObject tester = new JSONObject();
        assertTrue(tester.isEmpty());
        tester.put("int", insertVal);
        assertFalse(tester.isEmpty());
        assertEquals(1, tester.length());
        assertInstanceOf(Integer.class, tester.getInt("int"));
	}

    // if you need to pass multiple parameters, a simple way is to use CsvSource
	// it is able to do multiple parameters, even of mixed PRIMITIVE type
    @ParameterizedTest
    @CsvSource(value = {"1:billion:5:TRUE", "-1:-3:fanum tax:TRUE"}, delimiter = ':')
    void testWithCsvSource(int first, String second, String third, boolean fourth) {
		JSONObject tester = new JSONObject();
        tester.put(second, first);
        tester.put(third, fourth);
        assertEquals(2, tester.length());
        assertTrue(tester.getBoolean(third));
        try {
            tester.getBoolean(second);
            fail(); // this is something that you can do to check whether an operation throws an exception as intended
        } catch (JSONException e) {
            LOG.info("JSONException encountered as expected");
        } // other exceptions will cause this to error
	}

    // you can use the RepeatedTest annotation to automatically run a test many times
	// great for automating random testing
    @RepeatedTest(20)
    void testRepeatedRandom() {
        Random rd = new Random();
        int maxElems = 100 + rd.nextInt(50);

        JSONObject tester = new JSONObject();

        assertTrue(tester.isEmpty());
        assertEquals(0, tester.length());
        for (int i = 0; i < maxElems; i++) {
            int value = rd.nextInt();
            tester.put(Integer.toString(i), value);
            assertFalse(tester.isEmpty());
            assertEquals(i + 1, tester.length());
            assertEquals(value, tester.getInt(Integer.toString(i)));
        }
        assertEquals(maxElems, tester.length());
    }

    // Using MethodSource allows you to pass complex objects as parameters
    // you define a STATIC method that returns an object of type Stream<Arguments>
    // where Arguments is a type from Junit 5
    // then, the parameter of MethodSource is the name of that method that returns the Stream
    private static int[] INT_STREAM_ONE = new int[] {1, 2, 3};
    private static int[] INT_STREAM_TWO = new int[] {4, 3, 2, 1};
    private static Stream<Arguments> intArrayParams() {
        // https://stackoverflow.com/a/23548085
        // this is how you can convert int[] into List<Integer>
        return Stream.of(
			Arguments.of("ascendingInt", Arrays.stream(INT_STREAM_ONE).boxed().collect(Collectors.toList())),
			Arguments.of("descendingInt", Arrays.stream(INT_STREAM_TWO).boxed().collect(Collectors.toList()))
        );
    }

    @ParameterizedTest
    @MethodSource("intArrayParams")
    void testMethodSourceIntArray(String keyName, List<Integer> intArray) {
        JSONObject tester = new JSONObject();

        for (Integer i : intArray) {
            tester.put(keyName, i);
        }
        assertFalse(tester.isEmpty());

        // check your understanding of JSON - why will all of these assertions pass?
        assertNotEquals(intArray.size(), tester.length());
        assertEquals(1, tester.length());
        assertEquals(intArray.get(intArray.size() - 1), tester.getInt(keyName));
    }

    private static String[] SONG_ONE = new String[] {"go", "ahead", "and", "cry", "little", "girl"};
    private static String[] SONG_TWO = new String[] {"when", "I", "feel", "heavy", "metal"};
    private static Stream<Arguments> stringArrayParams() {
		return Stream.of(
			Arguments.of("songOne", Arrays.asList(SONG_ONE)),
            Arguments.of("songTwo", Arrays.asList(SONG_TWO))
		);
	}

    @ParameterizedTest
    @MethodSource("stringArrayParams")
    void testMethodSourceStringArray(String keyName, List<String> stringArray) {
        Map<String, String> tempMap = new HashMap<>();
        for (int i = 0; i < stringArray.size(); i++) {
            tempMap.put(keyName + i, stringArray.get(i));
        }

        // you can instantiate a JSONObject based on a map!
        JSONObject tester = new JSONObject(tempMap);

        assertFalse(tester.isEmpty());
        assertEquals(tempMap.size(), tester.length());
        assertEquals(stringArray.size(), tester.length());

        for (int i = 0; i < stringArray.size(); i++) {
            assertEquals(stringArray.get(i), tester.getString(keyName + i));
        }
    }

    private static Stream<Arguments> allArrayParams() {
		return Stream.concat(intArrayParams(), stringArrayParams());
	}

    @ParameterizedTest
    @MethodSource("allArrayParams")
    void testJSONArray(String keyName, List<Integer> intArray) {
        JSONObject tester = new JSONObject();
        tester.put(keyName, intArray);  // inserting a list as a value into a JSONObject turns it into a JSONArray within the JSONObject
        assertFalse(tester.isEmpty());

        JSONArray array = tester.getJSONArray(keyName);
        assertFalse(array.isEmpty());
        assertEquals(intArray.size(), array.length());
        // JSONArray preserves ordering of insertion (like List), whereas JSONObject doesn't (like Map)
        for (int i = 0; i < intArray.size(); i++) {
            assertEquals(intArray.get(i), array.get(i));
        }
    }
}
