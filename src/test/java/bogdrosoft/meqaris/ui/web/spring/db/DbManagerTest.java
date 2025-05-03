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

package bogdrosoft.meqaris.ui.web.spring.db;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import bogdrosoft.meqaris.ui.web.spring.TestHelper;

/**
 * A test for the database access class.
 * @author Bogdan Drozdowski
 */
public class DbManagerTest {

	@Test
	public void testNullFile() {

		assertThrows(Exception.class, new DbTestExecutable(null));
	}

	@Test
	public void testFileNotFound() {

		assertThrows(Exception.class, new DbTestExecutable("/blah"));
	}

	@Test
	public void testFileCantRead() {

		final String dir = TestHelper.getFullPathFor("bad-cantread.ini");
		File f = new File(dir);
		f.setReadable(false);
		assertThrows(Exception.class, new DbTestExecutable(dir));
	}

	@Test
	public void testBadDbType() {

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
