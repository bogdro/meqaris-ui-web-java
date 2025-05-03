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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import bogdrosoft.meqaris.ui.web.spring.BaseIntegrationTest;
import bogdrosoft.meqaris.ui.web.spring.Status;
import bogdrosoft.meqaris.ui.web.spring.TestHelper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StatusControllerRestTest extends BaseIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate cc;

	private Status getStatusFor(String file) throws Exception {

		ResponseEntity<Status> res = cc.getForEntity(
				new URI("http://localhost:" + port + "/status?file=" + file),
				Status.class);
		return res.getBody();
	}

	@Test
	public void testStatus() throws Exception {

		Status s = getStatusFor(TestHelper.getFullPathFor("good.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.TRUE, s.getCfgFileStatus());
		assertEquals(Boolean.TRUE, s.getCfgStatus());
		assertEquals(Boolean.TRUE, s.getDbConnStatus());
		assertEquals(Boolean.TRUE, s.getLockDirStatus());
		assertEquals(Boolean.TRUE, s.getLog4PerlFileStatus());
		assertEquals(Boolean.TRUE, s.getSqlDirStatus());
	}

	private void checkBadCfgStatusFor(String file) throws Exception {

		Status s = getStatusFor(file);
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgStatus());
	}

	@Test
	public void testStatusMissingFile() throws Exception {

		Status s = getStatusFor("blah");
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgFileStatus());
	}

	@Test
	public void testStatusDirAsFile() throws Exception {

		Status s = getStatusFor(".");
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgFileStatus());
	}

	@Test
	public void testStatusBadFormat() throws Exception {

		checkBadCfgStatusFor(TestHelper.getFullPathFor("bad-format.ini"));
	}

	@Test
	public void testStatusBadCantRead() throws Exception {

		String dir = TestHelper.getFullPathFor("bad-cantread.ini");
		File f = new File(dir);
		f.setReadable(false);
		Status s = getStatusFor(dir);
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgFileStatus());
	}

	@Test
	public void testStatusMissingMainSetting() throws Exception {

		checkBadCfgStatusFor(TestHelper.getFullPathFor("bad-miss-main.ini"));
	}

	@Test
	public void testStatusMissingDbSetting() throws Exception {

		checkBadCfgStatusFor(TestHelper.getFullPathFor("bad-miss-db.ini"));
	}

	@Test
	public void testStatusBadDatadir() throws Exception {

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-datadir.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getSqlDirStatus());
	}

	@Test
	public void testStatusBadDatadirNotDir() throws Exception {

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-datadir-notdir.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getSqlDirStatus());
	}

	@Test
	public void testStatusBadDbType() throws Exception {

		checkBadCfgStatusFor(TestHelper.getFullPathFor("bad-dbtype.ini"));
	}

	@Test
	public void testStatusBadL4P() throws Exception {

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-l4p.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getLog4PerlFileStatus());
	}

	@Test
	public void testStatusBadL4PCantRead() throws Exception {

		String dir = TestHelper.getFullPathFor("bad-l4p-cantread.ini");
		File f = new File(TestHelper.getFullPathFor("bad-cantread.ini"));
		f.setReadable(false);
		Status s = getStatusFor(dir);
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getLog4PerlFileStatus());
	}

	@Test
	public void testStatusBadL4PIsDir() throws Exception {

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-l4p-dir.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getLog4PerlFileStatus());
	}

	@Test
	public void testStatusBadLockdir() throws Exception {

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-lockdir.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getLockDirStatus());
	}

	@Test
	public void testStatusBadLockdirNotDir() throws Exception {

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-lockdir-notdir.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getLockDirStatus());
	}

	@Test
	public void testStatusBadSection() throws Exception {

		checkBadCfgStatusFor(TestHelper.getFullPathFor("bad-section.ini"));
	}

	@Test
	public void testStatusBadDbUser() throws Exception {

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-username.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getDbConnStatus());
	}
}
