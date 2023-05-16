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

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MeqCalDavServersTest {

	private static final Long ID = Long.valueOf(111L);
	private static final String NAME_STR = "CalDAV1";
	private static final String URL_STR = "http://cal_dav_1";
	private static final String USER_STR = "test-username";
	private static final String PASS_STR = "test-password";
	private static final String REALM_STR = "test-realm";

	private Map<String, Object> prepareInput() {

		Map<String, Object> input = new HashMap<String, Object>();
		input.put("cals_id", ID);
		input.put("cals_name", NAME_STR);
		input.put("cals_url", URL_STR);
		input.put("cals_username", USER_STR);
		input.put("cals_password", PASS_STR);
		input.put("cals_realm", REALM_STR);
		return input;
	}

	@Test
	public void testBuildFromMap() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServers c = MeqCalDavServers.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(NAME_STR, c.getName());
		assertEquals(URL_STR, c.getUrl());
		assertEquals(USER_STR, c.getUsername());
		assertEquals(PASS_STR, c.getPassword());
		assertEquals(REALM_STR, c.getRealm());
	}

	@Test
	public void testBuildFromMapMissingId() {

		Map<String, Object> input = prepareInput();
		input.remove("cals_id");
		MeqCalDavServers c = MeqCalDavServers.buildFromMap(input);
		assertEquals(NAME_STR, c.getName());
		assertEquals(URL_STR, c.getUrl());
		assertEquals(USER_STR, c.getUsername());
		assertEquals(PASS_STR, c.getPassword());
		assertEquals(REALM_STR, c.getRealm());
	}

	@Test
	public void testBuildFromMapMissingName() {

		Map<String, Object> input = prepareInput();
		input.remove("cals_name");
		MeqCalDavServers c = MeqCalDavServers.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(URL_STR, c.getUrl());
		assertEquals(USER_STR, c.getUsername());
		assertEquals(PASS_STR, c.getPassword());
		assertEquals(REALM_STR, c.getRealm());
	}

	@Test
	public void testBuildFromMapMissingUrl() {

		Map<String, Object> input = prepareInput();
		input.remove("cals_url");
		MeqCalDavServers c = MeqCalDavServers.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(NAME_STR, c.getName());
		assertEquals(USER_STR, c.getUsername());
		assertEquals(PASS_STR, c.getPassword());
		assertEquals(REALM_STR, c.getRealm());
	}

	@Test
	public void testBuildFromMapMissingUsername() {

		Map<String, Object> input = prepareInput();
		input.remove("cals_username");
		MeqCalDavServers c = MeqCalDavServers.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(NAME_STR, c.getName());
		assertEquals(URL_STR, c.getUrl());
		assertEquals(PASS_STR, c.getPassword());
		assertEquals(REALM_STR, c.getRealm());
	}

	@Test
	public void testBuildFromMapMissingPassword() {

		Map<String, Object> input = prepareInput();
		input.remove("cals_password");
		MeqCalDavServers c = MeqCalDavServers.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(NAME_STR, c.getName());
		assertEquals(URL_STR, c.getUrl());
		assertEquals(USER_STR, c.getUsername());
		assertEquals(REALM_STR, c.getRealm());
	}

	@Test
	public void testBuildFromMapMissingRealm() {

		Map<String, Object> input = prepareInput();
		input.remove("cals_realm");
		MeqCalDavServers c = MeqCalDavServers.buildFromMap(input);
		assertEquals(ID, c.getId());
		assertEquals(NAME_STR, c.getName());
		assertEquals(URL_STR, c.getUrl());
		assertEquals(USER_STR, c.getUsername());
		assertEquals(PASS_STR, c.getPassword());
	}

	@Test
	public void testEquals() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServers c1 = MeqCalDavServers.buildFromMap(input);
		MeqCalDavServers c2 = MeqCalDavServers.buildFromMap(input);
		assertEquals(c1, c1);
		assertEquals(c1, c2);
		assertNotEquals(c1, "c1");
	}

	@Test
	public void testEqualsDiffId() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServers c1 = MeqCalDavServers.buildFromMap(input);
		input.put("cals_id", ID + 1L);
		MeqCalDavServers c2 = MeqCalDavServers.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffName() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServers c1 = MeqCalDavServers.buildFromMap(input);
		input.put("cals_name", NAME_STR + ".com");
		MeqCalDavServers c2 = MeqCalDavServers.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffUrl() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServers c1 = MeqCalDavServers.buildFromMap(input);
		input.put("cals_url", URL_STR + "-test");
		MeqCalDavServers c2 = MeqCalDavServers.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffUsername() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServers c1 = MeqCalDavServers.buildFromMap(input);
		input.put("cals_username", USER_STR + "-test");
		MeqCalDavServers c2 = MeqCalDavServers.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffPassword() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServers c1 = MeqCalDavServers.buildFromMap(input);
		input.put("cals_password", PASS_STR + "0");
		MeqCalDavServers c2 = MeqCalDavServers.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testEqualsDiffRealm() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServers c1 = MeqCalDavServers.buildFromMap(input);
		input.put("cals_realm", REALM_STR + "-test");
		MeqCalDavServers c2 = MeqCalDavServers.buildFromMap(input);
		assertNotEquals(c1, c2);
	}

	@Test
	public void testHashcode() {

		Map<String, Object> input = prepareInput();
		MeqCalDavServers c1 = MeqCalDavServers.buildFromMap(input);
		MeqCalDavServers c2 = MeqCalDavServers.buildFromMap(input);
		assertEquals(c1.hashCode(), c2.hashCode());
	}

	@Test
	public void testToString() {

		MeqCalDavServers c = MeqCalDavServers.buildFromMap(null);
		assertNotNull(c.toString());

		Map<String, Object> input = prepareInput();
		c = MeqCalDavServers.buildFromMap(input);
		assertNotNull(c.toString());
		assertTrue(c.toString().contains(ID.toString()));
		assertTrue(c.toString().contains(NAME_STR));
		assertTrue(c.toString().contains(URL_STR));
		assertTrue(c.toString().contains(USER_STR));
		assertTrue(c.toString().contains(PASS_STR));
		assertTrue(c.toString().contains(REALM_STR));
	}
}
