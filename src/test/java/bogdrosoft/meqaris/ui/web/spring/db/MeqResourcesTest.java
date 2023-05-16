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

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MeqResourcesTest {

	private static final Long ID = Long.valueOf(11111L);
	private static final String NAME_STR = "nnnnn";
	private static final String EMAIL_STR = "a@b.c";
	private static final String DESC_STR = "ddddd";
	private static final Boolean ENABLED = Boolean.TRUE;
	private static final Boolean DISABLED = Boolean.FALSE;

	private Map<String, Object> prepareInput() {

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("r_id", ID);
		input.put("r_name", NAME_STR);
		input.put("r_email", EMAIL_STR);
		input.put("r_description", DESC_STR);
		input.put("r_enabled", ENABLED);
		return input;
	}

	@Test
	public void testBuildFromMap() {

		Map<String, Object> input = prepareInput();
		MeqResources c = MeqResources.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(NAME_STR, c.getName());
		assertEquals(EMAIL_STR, c.getEmail());
		assertEquals(DESC_STR, c.getDescription());
		assertEquals(ENABLED, c.getEnabled());
	}

	@Test
	public void testBuildFromMapMissingId() {

		Map<String, Object> input = prepareInput();
		input.remove("r_id");
		MeqResources c = MeqResources.buildFromMap(input);
		assertEquals(NAME_STR, c.getName());
		assertEquals(EMAIL_STR, c.getEmail());
		assertEquals(DESC_STR, c.getDescription());
		assertEquals(ENABLED, c.getEnabled());
	}

	@Test
	public void testBuildFromMapMissingName() {

		Map<String, Object> input = prepareInput();
		input.remove("r_name");
		MeqResources c = MeqResources.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(EMAIL_STR, c.getEmail());
		assertEquals(DESC_STR, c.getDescription());
		assertEquals(ENABLED, c.getEnabled());
	}

	@Test
	public void testBuildFromMapMissingEmail() {

		Map<String, Object> input = prepareInput();
		input.remove("r_email");
		MeqResources c = MeqResources.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(NAME_STR, c.getName());
		assertEquals(DESC_STR, c.getDescription());
		assertEquals(ENABLED, c.getEnabled());
	}

	@Test
	public void testBuildFromMapMissingDesc() {

		Map<String, Object> input = prepareInput();
		input.remove("r_description");
		MeqResources c = MeqResources.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(NAME_STR, c.getName());
		assertEquals(EMAIL_STR, c.getEmail());
		assertEquals(ENABLED, c.getEnabled());
	}

	@Test
	public void testBuildFromMapMissingEnabled() {

		Map<String, Object> input = prepareInput();
		input.remove("r_enabled");
		MeqResources c = MeqResources.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(NAME_STR, c.getName());
		assertEquals(EMAIL_STR, c.getEmail());
		assertEquals(DESC_STR, c.getDescription());
	}

	@Test
	public void testEquals() {

		Map<String, Object> input = prepareInput();
		MeqResources c1 = MeqResources.buildFromMap(input);
		MeqResources c2 = MeqResources.buildFromMap(input);
		assertEquals(c1, c1);
		assertEquals(c1, c2);
		assertNotEquals(c1, "c1");
	}

	@Test
	public void testEqualsDiffId() {

		Map<String, Object> input = prepareInput();
		MeqResources c1 = MeqResources.buildFromMap(input);
		input.put("r_id", ID + 2);
		MeqResources c2 = MeqResources.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffName() {

		Map<String, Object> input = prepareInput();
		MeqResources c1 = MeqResources.buildFromMap(input);
		input.put("r_name", NAME_STR + "2");
		MeqResources c2 = MeqResources.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffDesc() {

		Map<String, Object> input = prepareInput();
		MeqResources c1 = MeqResources.buildFromMap(input);
		input.put("r_description", DESC_STR + "2");
		MeqResources c2 = MeqResources.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffEmail() {

		Map<String, Object> input = prepareInput();
		MeqResources c1 = MeqResources.buildFromMap(input);
		input.put("r_email", EMAIL_STR + ".com");
		MeqResources c2 = MeqResources.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffEnabled() {

		Map<String, Object> input = prepareInput();
		MeqResources c1 = MeqResources.buildFromMap(input);
		input.put("r_enabled", DISABLED);
		MeqResources c2 = MeqResources.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testHashcode() {

		Map<String, Object> input = prepareInput();
		MeqResources c1 = MeqResources.buildFromMap(input);
		MeqResources c2 = MeqResources.buildFromMap(input);
		assertEquals(c1.hashCode(), c2.hashCode());
	}

	@Test
	public void testToString() {

		MeqResources c = MeqResources.buildFromMap(null);
		assertNotNull(c.toString());

		Map<String, Object> input = prepareInput();
		c = MeqResources.buildFromMap(input);
		assertNotNull(c.toString());
		assertTrue(c.toString().contains(ID.toString()));
		assertTrue(c.toString().contains(NAME_STR));
		assertTrue(c.toString().contains(EMAIL_STR));
		assertTrue(c.toString().contains(DESC_STR));
	}
}
