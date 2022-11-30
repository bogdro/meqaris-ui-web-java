package bogdrosoft.meqaris.ui.web.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StarterTest {

	@Autowired
	private Starter starter;

	@Test
	public void contextLoads() throws Exception {
		assertThat(starter).isNotNull();
	}
}
