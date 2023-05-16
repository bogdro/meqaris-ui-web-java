/*
 * Copyright (C) 2022-2023 Bogdan 'bogdro' Drozdowski, bogdro (at) users . sourceforge . net
 *
 * This file is part of Meqaris (Meeting Equipment and Room Invitation System),
 *  software that allows booking meeting rooms and other resources using
 *  e-mail invitations.
 * Meqaris homepage: https://meqaris.sourceforge.io/
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

public class MeqEventsTest {

	private static final Long ID = Long.valueOf(55L);
	private static final String ORGANISER_STR = "a@b.c";
	private static final String SUMMARY_STR = "sssss";
	private static final String UID_STR = "test-uid";
	private static final String DATA_STR = "test-data";
	private static final Integer SEQ = Integer.valueOf(111);

	private static final String DTSTAMP_STR = "2022-04-28 22:02:23.0";
	private static Object dtstamp;
	private static final String ENTRY_STR = "2023-01-01 01:02:03.0";
	private static Object entryDate;

	@BeforeAll
	public static void prepare() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(DTSTAMP_STR));
		dtstamp = c;
		c = Calendar.getInstance();
		c.setTime(sdf.parse(ENTRY_STR));
		entryDate = c;
	}

	private Map<String, Object> prepareInput() {

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("e_id", ID);
		input.put("e_entry_date", ENTRY_STR);
		input.put("e_organiser", ORGANISER_STR);
		input.put("e_summary", SUMMARY_STR);
		input.put("e_dtstamp", DTSTAMP_STR);
		input.put("e_uid", UID_STR);
		input.put("e_seq", SEQ);
		input.put("e_data", DATA_STR);
		return input;
	}

	@Test
	public void testBuildFromMap() {

		Map<String, Object> input = prepareInput();
		MeqEvents c = MeqEvents.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(entryDate, c.getEntryDate());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingId() {

		Map<String, Object> input = prepareInput();
		input.remove("e_id");
		MeqEvents c = MeqEvents.buildFromMap(input);
		assertEquals(entryDate, c.getEntryDate());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingEntryDate() {

		Map<String, Object> input = prepareInput();
		input.remove("e_entry_date");
		MeqEvents c = MeqEvents.buildFromMap(input);
		assertEquals(ID, c.getId());
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
		input.remove("e_organiser");
		MeqEvents c = MeqEvents.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(entryDate, c.getEntryDate());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingSummary() {

		Map<String, Object> input = prepareInput();
		input.remove("e_summary");
		MeqEvents c = MeqEvents.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(entryDate, c.getEntryDate());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingDtStamp() {

		Map<String, Object> input = prepareInput();
		input.remove("e_dtstamp");
		MeqEvents c = MeqEvents.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(entryDate, c.getEntryDate());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingUid() {

		Map<String, Object> input = prepareInput();
		input.remove("e_uid");
		MeqEvents c = MeqEvents.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(entryDate, c.getEntryDate());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(SEQ, c.getSeq());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingSeq() {

		Map<String, Object> input = prepareInput();
		input.remove("e_seq");
		MeqEvents c = MeqEvents.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(entryDate, c.getEntryDate());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(DATA_STR, c.getData());
	}

	@Test
	public void testBuildFromMapMissingData() {

		Map<String, Object> input = prepareInput();
		input.remove("e_data");
		MeqEvents c = MeqEvents.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(entryDate, c.getEntryDate());
		assertEquals(ORGANISER_STR, c.getOrganiser());
		assertEquals(SUMMARY_STR, c.getSummary());
		assertEquals(dtstamp, c.getDateTimestamp());
		assertEquals(UID_STR, c.getUid());
		assertEquals(SEQ, c.getSeq());
	}

	@Test
	public void testEquals() {

		Map<String, Object> input = prepareInput();
		MeqEvents c1 = MeqEvents.buildFromMap(input);
		MeqEvents c2 = MeqEvents.buildFromMap(input);
		assertEquals(c1, c1);
		assertEquals(c1, c2);
		assertNotEquals(c1, "c1");
	}

	@Test
	public void testEqualsDiffId() {

		Map<String, Object> input = prepareInput();
		MeqEvents c1 = MeqEvents.buildFromMap(input);
		input.put("e_id", ID + 1L);
		MeqEvents c2 = MeqEvents.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffEntryDate() {

		Map<String, Object> input = prepareInput();
		MeqEvents c1 = MeqEvents.buildFromMap(input);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0);
		input.put("e_entry_date", c);
		MeqEvents c2 = MeqEvents.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffOrganiser() {

		Map<String, Object> input = prepareInput();
		MeqEvents c1 = MeqEvents.buildFromMap(input);
		input.put("e_organiser", ORGANISER_STR + ".com");
		MeqEvents c2 = MeqEvents.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffSummary() {

		Map<String, Object> input = prepareInput();
		MeqEvents c1 = MeqEvents.buildFromMap(input);
		input.put("e_summary", SUMMARY_STR + "-test");
		MeqEvents c2 = MeqEvents.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffDtStamp() {

		Map<String, Object> input = prepareInput();
		MeqEvents c1 = MeqEvents.buildFromMap(input);
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(0);
		input.put("e_dtstamp", c);
		MeqEvents c2 = MeqEvents.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffUid() {

		Map<String, Object> input = prepareInput();
		MeqEvents c1 = MeqEvents.buildFromMap(input);
		input.put("e_uid", UID_STR + "-test");
		MeqEvents c2 = MeqEvents.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffSeq() {

		Map<String, Object> input = prepareInput();
		MeqEvents c1 = MeqEvents.buildFromMap(input);
		input.put("e_seq", SEQ + 1);
		MeqEvents c2 = MeqEvents.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffData() {

		Map<String, Object> input = prepareInput();
		MeqEvents c1 = MeqEvents.buildFromMap(input);
		input.put("e_data", DATA_STR + "-test");
		MeqEvents c2 = MeqEvents.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testHashcode() {

		Map<String, Object> input = prepareInput();
		MeqEvents c1 = MeqEvents.buildFromMap(input);
		MeqEvents c2 = MeqEvents.buildFromMap(input);
		assertEquals(c1.hashCode(), c2.hashCode());
	}

	@Test
	public void testToString() {

		MeqEvents c = MeqEvents.buildFromMap(null);
		assertNotNull(c.toString());

		Map<String, Object> input = prepareInput();
		c = MeqEvents.buildFromMap(input);
		assertNotNull(c.toString());
		assertTrue(c.toString().contains(ID.toString()));
		assertTrue(c.toString().contains(ORGANISER_STR));
		assertTrue(c.toString().contains(SUMMARY_STR));
		assertTrue(c.toString().contains(UID_STR));
		assertTrue(c.toString().contains(SEQ.toString()));
		assertTrue(c.toString().contains(DATA_STR));
	}
}
