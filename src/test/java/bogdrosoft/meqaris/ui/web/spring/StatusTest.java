package bogdrosoft.meqaris.ui.web.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StatusTest {

	@Test
	public void testStatusNullFile() {

		Status s = Status.checkStatus(null);
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgFileStatus());
	}
}
