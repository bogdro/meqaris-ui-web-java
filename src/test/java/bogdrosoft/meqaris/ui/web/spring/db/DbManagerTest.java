package bogdrosoft.meqaris.ui.web.spring.db;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import bogdrosoft.meqaris.ui.web.spring.TestHelper;

public class DbManagerTest {

	@Test
	public void testNullFile() throws Exception {

		assertThrows(Exception.class, new DbTestExecutable(null));
	}

	@Test
	public void testFileNotFound() throws Exception {

		assertThrows(Exception.class, new DbTestExecutable("/blah"));
	}

	@Test
	public void testFileCantRead() throws Exception {

		final String dir = TestHelper.getFullPathFor("bad-cantread.ini");
		File f = new File(dir);
		f.setReadable(false);
		assertThrows(Exception.class, new DbTestExecutable(dir));
	}

	@Test
	public void testBadDbType() throws Exception {

		assertThrows(Exception.class,
				new DbTestExecutable(TestHelper.getFullPathFor("bad-dbtype.ini")));
	}

	private static class DbTestExecutable implements Executable {

		private final String filePath;

		DbTestExecutable(String path) {
			filePath = path;
		}

		@Override
		public void execute() throws Throwable {
			DbManager db = new DbManager(filePath);
			db.getConfig();
		}
	}
}
