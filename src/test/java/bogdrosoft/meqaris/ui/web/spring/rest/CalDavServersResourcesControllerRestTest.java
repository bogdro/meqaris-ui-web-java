package bogdrosoft.meqaris.ui.web.spring.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import bogdrosoft.meqaris.ui.web.spring.TestHelper;
import bogdrosoft.meqaris.ui.web.spring.db.MeqCalDavServersResources;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CalDavServersResourcesControllerRestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate cc;

	@Test
	public void testFullList() throws Exception {

		String dir = TestHelper.getFullPathFor("good.ini");
		@SuppressWarnings("unchecked")
		List<MeqCalDavServersResources> r = cc.getForObject(
				"http://localhost:" + port + "/caldav_servers_res?file=" + dir, List.class);
		assertNotNull(r);
	}

	@Test
	public void testFirst() throws Exception {

		String dir = TestHelper.getFullPathFor("good.ini");
		cc.getForEntity(
				new URI("http://localhost:" + port + "/caldav_server_res/1?file=" + dir),
				MeqCalDavServersResources.class);
		// this is just to potentially get more coverage
	}

	@Test
	public void testMissing() throws Exception {

		String dir = TestHelper.getFullPathFor("good.ini");
		ResponseEntity<MeqCalDavServersResources> r = cc.getForEntity(
				new URI("http://localhost:" + port + "/caldav_server_res/-1?file=" + dir),
				MeqCalDavServersResources.class);
		assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
		assertNull(r.getBody());
	}
}
