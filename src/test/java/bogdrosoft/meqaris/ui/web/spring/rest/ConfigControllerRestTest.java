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

package bogdrosoft.meqaris.ui.web.spring.rest;

import static org.assertj.core.api.Assertions.fail;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import bogdrosoft.meqaris.ui.web.spring.TestHelper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ConfigControllerRestTest {

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

	private void findConfigValue(List<Map<String, Object>> cfgs, String name) {

		int size = cfgs.size();
		for (int i = 0; i < size; i++) {

			Map<String, Object> row = cfgs.get(i);
			if (name.equals(row.get("name"))) {
				return;
			}
		}
		fail("The required configuration '" + name + "' not found");
	}

	@Test
	public void testFullConfig() throws Exception {

		String dir = TestHelper.getFullPathFor("good.ini");
		List<Map<String, Object>> cfgs = getConfigsFor(dir);
		findConfigValue (cfgs, "db_version");
	}

	@Test
	public void testVersion() throws Exception {

		String dir = TestHelper.getFullPathFor("good.ini");
		List<Map<String, Object>> cfgs = getConfigsFor(dir + "&name=db_version");
		findConfigValue (cfgs, "db_version");
	}

	@Test
	public void testMailServer() throws Exception {

		String dir = TestHelper.getFullPathFor("good.ini");
		List<Map<String, Object>> cfgs = getConfigsFor(dir + "&name=mail_server");
		findConfigValue (cfgs, "mail_server");
	}
}
