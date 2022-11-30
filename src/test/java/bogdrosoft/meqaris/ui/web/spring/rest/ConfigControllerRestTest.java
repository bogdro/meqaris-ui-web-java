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

	@Test
	public void testFullConfig() throws Exception {

		String dir = TestHelper.getFullPathFor("good.ini");
		List<Map<String, Object>> cfgs = getConfigsFor(dir);
		int size = cfgs.size();
		for (int i = 0; i < size; i++) {

			Map<String, Object> row = cfgs.get(i);
			if ("db_version".equals(row.get("name"))) {
				return;
			}
		}
		fail("The required 'db_version' configuration not found");
	}

	@Test
	public void testVersion() throws Exception {

		String dir = TestHelper.getFullPathFor("good.ini");
		List<Map<String, Object>> cfgs = getConfigsFor(dir + "&name=db_version");
		int size = cfgs.size();
		for (int i = 0; i < size; i++) {

			Map<String, Object> row = cfgs.get(i);
			if ("db_version".equals(row.get("name"))) {
				return;
			}
		}
		fail("The required 'db_version' configuration not found");
	}
}
