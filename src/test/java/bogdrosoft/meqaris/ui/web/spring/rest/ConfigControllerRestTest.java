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

package bogdrosoft.meqaris.ui.web.spring.rest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import bogdrosoft.meqaris.ui.web.spring.BaseIntegrationTest;
import bogdrosoft.meqaris.ui.web.spring.TestHelper;

/**
 * A test for the ReST controller for the "/config" URL.
 * @author Bogdan Drozdowski
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ConfigControllerRestTest extends BaseIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate cc;

	private List<Map<String, Object>> getConfigsFor(String file) {

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> cfgs = cc.getForObject(
				"http://localhost:" + port + "/config?file=" + file, List.class);
		return cfgs;
	}

	private static boolean findConfigValue(List<Map<String, Object>> cfgs, String name) {

		int size = cfgs.size();
		for (int i = 0; i < size; i++) {

			Map<String, Object> row = cfgs.get(i);
			if (name.equals(row.get("name"))) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void testFullConfig() {

		String dir = TestHelper.getFullPathFor("good.ini");
		List<Map<String, Object>> cfgs = getConfigsFor(dir);
		assertTrue(findConfigValue(cfgs, "db_version"), "The required configuration value 'db_version' not found");
	}

	@Test
	public void testVersion() {

		String dir = TestHelper.getFullPathFor("good.ini");
		List<Map<String, Object>> cfgs = getConfigsFor(dir + "&name=db_version");
		assertTrue(findConfigValue(cfgs, "db_version"), "The required configuration value 'db_version' not found");
	}

	@Test
	public void testMailServer() {

		String dir = TestHelper.getFullPathFor("good.ini");
		List<Map<String, Object>> cfgs = getConfigsFor(dir + "&name=mail_server");
		assertTrue(findConfigValue(cfgs, "mail_server"), "The required configuration value 'mail_server' not found");
	}

	@Test
	public void testUnknown() {

		String dir = TestHelper.getFullPathFor("good.ini");
		List<Map<String, Object>> cfgs = getConfigsFor(dir + "&name=blah");
		assertFalse(findConfigValue(cfgs, "blah"), "The configuration value 'blah' found, but shouldn't be");
	}
}
