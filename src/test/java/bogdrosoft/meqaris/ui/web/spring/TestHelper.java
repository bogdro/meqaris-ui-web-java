package bogdrosoft.meqaris.ui.web.spring;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;

public class TestHelper {

	public static String getFullPathFor(String file) {

		String dir = System.getProperty("test.rsrc.dir");
		if (dir != null) {
			// maven build
			dir += "/" + file;
		} else {
			// IDE unit test run
			URL u = ClassLoader.getSystemResource(file);
			dir = u.toString().replace("file:", "");
		}
		return dir;
	}

	public static void verifyStatus(Status s) {

		assertNotNull(s);
		assertNotNull(s.getCfgFileMsg());
		assertNotNull(s.getCfgMsg());
		assertNotNull(s.getDbConnMsg());
		assertNotNull(s.getLockDirMsg());
		assertNotNull(s.getLog4PerlFileMsg());
		assertNotNull(s.getSqlDirMsg());
	}
}
