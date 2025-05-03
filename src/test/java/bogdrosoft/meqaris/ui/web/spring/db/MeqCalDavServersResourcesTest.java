/*
 * Copyright (C) 2022-2025 Bogdan 'bogdro' Drozdowski, bogdro (at) users . sourceforge . net
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

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * A test for the entity class for the "meq_caldav_servers_resources" table.
 * @author Bogdan Drozdowski
 */
public class MeqCalDavServersResourcesTest {

	private static final Long SERVER_ID = Long.valueOf(1234L);
	private static final Long RES_ID = Long.valueOf(2345L);

	private Map<String, Object> prepareInput() {

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("calres_cals_id", SERVER_ID);
		input.put("calres_r_id", RES_ID);
		return input;
	}

	@Test
	public void testBuildFromMap() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServersResources c = MeqCalDavServersResources.buildFromMap(input);
		assertEquals(SERVER_ID, c.getServerId());
		assertEquals(RES_ID, c.getResourceId());
	}

	@Test
	public void testBuildFromMapMissingServerId() {

		Map<String, Object> input = prepareInput();
		input.remove("calres_cals_id");
		MeqCalDavServersResources c = MeqCalDavServersResources.buildFromMap(input);
		assertEquals(RES_ID, c.getResourceId());
	}

	@Test
	public void testBuildFromMapMissingResourceId() {

		Map<String, Object> input = prepareInput();
		input.remove("calres_r_id");
		MeqCalDavServersResources c = MeqCalDavServersResources.buildFromMap(input);
		assertEquals(SERVER_ID, c.getServerId());
	}

	@Test
	public void testEquals() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServersResources c1 = MeqCalDavServersResources.buildFromMap(input);
		MeqCalDavServersResources c2 = MeqCalDavServersResources.buildFromMap(input);
		assertEquals(c1, c1);
		assertEquals(c1, c2);
		assertNotEquals(c1, "c1");
	}

	@Test
	public void testEqualsDiffServerId() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServersResources c1 = MeqCalDavServersResources.buildFromMap(input);
		input.put("calres_cals_id", SERVER_ID + 1L);
		MeqCalDavServersResources c2 = MeqCalDavServersResources.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffResourceId() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServersResources c1 = MeqCalDavServersResources.buildFromMap(input);
		input.put("calres_r_id", RES_ID + 1L);
		MeqCalDavServersResources c2 = MeqCalDavServersResources.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testHashcode() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServersResources c1 = MeqCalDavServersResources.buildFromMap(input);
		MeqCalDavServersResources c2 = MeqCalDavServersResources.buildFromMap(input);
		assertEquals(c1.hashCode(), c2.hashCode());
	}

	@Test
	public void testToString() {

		MeqCalDavServersResources c = MeqCalDavServersResources.buildFromMap(null);
		assertNotNull(c.toString());

		Map<String, Object> input = prepareInput();
		c = MeqCalDavServersResources.buildFromMap(input);
		assertNotNull(c.toString());
		assertTrue(c.toString().contains(SERVER_ID.toString()));
		assertTrue(c.toString().contains(RES_ID.toString()));
	}
}
