package bogdrosoft.meqaris.ui.web.spring.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MeqResourceReservationsTest {

	private static final Long ID = Long.valueOf(55L);
	private static final Long RES_ID = Long.valueOf(77L);
	private static final String ORGANISER_STR = "a@b.c";
	private static final String SUMMARY_STR = "sssss";
	private static final String UID_STR = "test-uid";
	private static final String DATA_STR = "test-data";
	private static final Integer SEQ = Integer.valueOf(111);

	private static final String INTERVAL_STR = "[\"2022-04-28 01:00:00+02\",\"2022-04-28 02:00:00+02\")";
	private static Object interval;

	private static final String DTSTAMP_STR = "20220428T220223Z+0000";
	private static Object dtstamp;

	@BeforeAll
	public static void prepare() throws Exception {

		interval = INTERVAL_STR /*new org.postgresql.util.PGInterval(INTERVAL_STR)*/;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'X");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(DTSTAMP_STR));
		dtstamp = c;
	}

	private Map<String, Object> prepareInput() {

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("rr_id", ID);
		input.put("rr_r_id", RES_ID);
		input.put("rr_interval", INTERVAL_STR);
		input.put("rr_organiser", ORGANISER_STR);
		input.put("rr_summary", SUMMARY_STR);
		input.put("rr_dtstamp", DTSTAMP_STR);
		input.put("rr_uid", UID_STR);
		input.put("rr_seq", SEQ);
		input.put("rr_data", DATA_STR);
		return input;
	}

	@Test
	public void testBuildFromMap() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(interval, c.getInterval());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingId() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_id");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(interval, c.getInterval());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingResId() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_r_id");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(interval, c.getInterval());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingInterval() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_interval");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingOrganiser() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_organiser");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(interval, c.getInterval());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingSummary() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_summary");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(interval, c.getInterval());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingDtStamp() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_dtstamp");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(interval, c.getInterval());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingUid() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_uid");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(interval, c.getInterval());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingSeq() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_seq");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(interval, c.getInterval());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingData() {

		Map<String, Object> input = prepareInput();
		input.remove("rr_data");
		MeqResourceReservations c = MeqResourceReservations.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(RES_ID, c.getResourceId());
		assertEquals(interval, c.getInterval());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
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
	public void testEqualsDiffOrganiser() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		input.put("rr_organiser", ORGANISER_STR + ".com");
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffSummary() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		input.put("rr_summary", SUMMARY_STR + "-test");
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffDtStamp() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0);
		input.put("rr_dtstamp", c);
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffUid() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		input.put("rr_uid", UID_STR + "-test");
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffSeq() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		input.put("rr_seq", SEQ + 1);
		MeqResourceReservations c2 = MeqResourceReservations.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffData() {

		Map<String, Object> input = prepareInput();
		MeqResourceReservations c1 = MeqResourceReservations.buildFromMap(input);
		input.put("rr_data", DATA_STR + "-test");
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
		assertTrue(c.toString().contains(RES_ID.toString()));
		assertTrue(c.toString().contains(ORGANISER_STR));
		assertTrue(c.toString().contains(SUMMARY_STR));
		assertTrue(c.toString().contains(UID_STR));
		assertTrue(c.toString().contains(SEQ.toString()));
		assertTrue(c.toString().contains(DATA_STR));
	}
}
