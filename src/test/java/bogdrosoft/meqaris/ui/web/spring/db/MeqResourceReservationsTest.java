package bogdrosoft.meqaris.ui.web.spring.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MeqResourceReservationsTest {

	private static final Long ID = Long.valueOf(55L);
	private static final Long RES_ID = Long.valueOf(77L);
	private static final Long EV_ID = Long.valueOf(99L);

	private static final String INTERVAL_STR = "[\"2022-04-28 01:00:00+02\",\"2022-04-28 02:00:00+02\")";

	private Map<String, Object> prepareInput() {

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("rr_id", ID);
		input.put("rr_r_id", RES_ID);
		input.put("rr_interval", INTERVAL_STR);
		input.put("rr_e_id", EV_ID);
		return input;
	}

	@Test
	public void testBuildFromMap() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(INTERVAL_STR, c.getInterval());
		assertEquals(EV_ID, c.getEventId());
	}

	@Test
	public void testBuildFromMapMissingId() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_id");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(INTERVAL_STR, c.getInterval());
		assertEquals(EV_ID, c.getEventId());
	}

	@Test
	public void testBuildFromMapMissingResId() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_r_id");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(INTERVAL_STR, c.getInterval());
		assertEquals(EV_ID, c.getEventId());
	}

	@Test
	public void testBuildFromMapMissingInterval() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_interval");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(EV_ID, c.getEventId());
	}

	@Test
	public void testBuildFromMapMissingEventId() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_e_id");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(INTERVAL_STR, c.getInterval());
	}

	@Test
	public void testEquals() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertEquals(c1, c1);
		assertEquals(c1, c2);
		assertNotEquals(c1, "c1");
	}

	@Test
	public void testEqualsDiffId() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		input.put("rr_id", ID + 1L);
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffResId() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		input.put("rr_r_id", RES_ID + 2L);
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffInterval() throws Exception {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		//input.put("rr_interval", new org.postgresql.util.PGInterval("[\"2012-04-28 01:00:00+02\",\"2012-04-28 02:00:00+02\")"));
		input.put("rr_interval", "[\"2012-04-28 01:00:00+02\",\"2012-04-28 02:00:00+02\")");
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffEventId() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		input.put("rr_e_id", EV_ID + 3L);
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testHashcode() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertEquals(c1.hashCode(), c2.hashCode());
	}

	@Test
	public void testToString() {

		MeqResourceReservations c = MeqResourceReservations.buildFromMap(null);
		assertNotNull(c.toString());

		Map<String, Object> input = prepareInput();
		c = MeqResourceReservations.buildFromMap(input);
		assertNotNull(c.toString());
		assertTrue(c.toString().contains(ID.toString()));
		assertTrue(c.toString().contains(EV_ID.toString()));
		assertTrue(c.toString().contains(RES_ID.toString()));
	}
}
