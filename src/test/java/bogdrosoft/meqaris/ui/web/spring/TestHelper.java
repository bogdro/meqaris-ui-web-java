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

package bogdrosoft.meqaris.ui.web.spring;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URL;

/**
 * A helper class for test classes.
 * @author Bogdan Drozdowski
 */
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
