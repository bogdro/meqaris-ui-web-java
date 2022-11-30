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

import bogdrosoft.meqaris.ui.web.spring.Status;
import bogdrosoft.meqaris.ui.web.spring.TestHelper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StatusControllerRestTest {

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

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-format.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgStatus());
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

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-miss-main.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgStatus());
	}

	@Test
	public void testStatusMissingDbSetting() throws Exception {

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-miss-db.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgStatus());
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

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-dbtype.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgStatus());
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

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-section.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgStatus());
	}

	@Test
	public void testStatusBadDbUser() throws Exception {

		Status s = getStatusFor(TestHelper.getFullPathFor("bad-username.ini"));
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getDbConnStatus());
	}
}
