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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import bogdrosoft.meqaris.ui.web.spring.IntegrationTestBase;
import bogdrosoft.meqaris.ui.web.spring.TestHelper;
import bogdrosoft.meqaris.ui.web.spring.db.MeqResources;

/**
 * A test for the ReST controller for the "/resource(s)" URLs.
 * @author Bogdan Drozdowski
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ResourcesControllerRestTest extends IntegrationTestBase {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate cc;

	@Test
	public void testFullList() {

		String dir = TestHelper.getFullPathFor("good.ini");
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> r = cc.getForObject(
				"http://localhost:" + port + "/resources?file=" + dir, List.class);
		assertNotNull(r);
	}

	@Test
	public void testFirst() throws Exception {

		String dir = TestHelper.getFullPathFor("good.ini");
		// this is just to potentially get more coverage
		ResponseEntity<MeqResources> r = cc.getForEntity(
				new URI("http://localhost:" + port + "/resource/1?file=" + dir),
				MeqResources.class);
		assertNotNull(r);
	}

	@Test
	public void testMissing() throws Exception {

		String dir = TestHelper.getFullPathFor("good.ini");
		ResponseEntity<MeqResources> r = cc.getForEntity(
				new URI("http://localhost:" + port + "/resource/-1?file=" + dir),
				MeqResources.class);
		assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
		assertNull(r.getBody());
	}
}
