package bogdrosoft.meqaris.ui.web.spring.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MeqConfigTest {

	private static final String NAME_STR = "nnnnn";
	private static final String VALUE_STR = "vvvvv";
	private static final String DESC_STR = "ddddd";

	private Map<String, Object> prepareInput() {

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("c_name", NAME_STR);
		input.put("c_value", VALUE_STR);
		input.put("c_description", DESC_STR);
		return input;
	}

	@Test
	public void testBuildFromMap() {

		Map<String, Object> input = prepareInput();
		MeqConfig c = MeqConfig.buildFromMap(input);
		assertEquals(NAME_STR, c.getName());
		assertEquals(VALUE_STR, c.getValue());
		assertEquals(DESC_STR, c.getDescription());
	}

	@Test
	public void testBuildFromMapMissingName() {

		Map<String, Object> input = prepareInput();
		input.remove("c_name");
		MeqConfig c = MeqConfig.buildFromMap(input);
		assertEquals(VALUE_STR, c.getValue());
		assertEquals(DESC_STR, c.getDescription());
	}

	@Test
	public void testBuildFromMapMissingValue() {

		Map<String, Object> input = prepareInput();
		input.remove("c_value");
		MeqConfig c = MeqConfig.buildFromMap(input);
		assertEquals(NAME_STR, c.getName());
		assertEquals(DESC_STR, c.getDescription());
	}

	@Test
	public void testBuildFromMapMissingDesc() {

		Map<String, Object> input = prepareInput();
		input.remove("c_description");
		MeqConfig c = MeqConfig.buildFromMap(input);
		assertEquals(NAME_STR, c.getName());
		assertEquals(VALUE_STR, c.getValue());
	}

	@Test
	public void testEquals() {

		Map<String, Object> input = prepareInput();
		MeqConfig c1 = MeqConfig.buildFromMap(input);
		MeqConfig c2 = MeqConfig.buildFromMap(input);
		assertEquals(c1, c1);
		assertEquals(c1, c2);
		assertNotEquals(c1, "c1");
	}

	@Test
	public void testEqualsDiffName() {

		Map<String, Object> input = prepareInput();
		MeqConfig c1 = MeqConfig.buildFromMap(input);
		input.put("c_name", NAME_STR + "2");
		MeqConfig c2 = MeqConfig.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffValue() {

		Map<String, Object> input = prepareInput();
		MeqConfig c1 = MeqConfig.buildFromMap(input);
		input.put("c_value", VALUE_STR + "2");
		MeqConfig c2 = MeqConfig.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffDesc() {

		Map<String, Object> input = prepareInput();
		MeqConfig c1 = MeqConfig.buildFromMap(input);
		input.put("c_description", DESC_STR + "2");
		MeqConfig c2 = MeqConfig.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testHashcode() {

		Map<String, Object> input = prepareInput();
		MeqConfig c1 = MeqConfig.buildFromMap(input);
		MeqConfig c2 = MeqConfig.buildFromMap(input);
		assertEquals(c1.hashCode(), c2.hashCode());
	}

	@Test
	public void testToString() {

		MeqConfig c = MeqConfig.buildFromMap(null);
		assertNotNull(c.toString());

		Map<String, Object> input = prepareInput();
		c = MeqConfig.buildFromMap(input);
		assertNotNull(c.toString());
		assertTrue(c.toString().contains(NAME_STR));
		assertTrue(c.toString().contains(VALUE_STR));
		assertTrue(c.toString().contains(DESC_STR));
	}
}
