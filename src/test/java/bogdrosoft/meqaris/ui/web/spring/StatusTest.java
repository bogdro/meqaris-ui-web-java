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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * A test for the class to represent the status of the Meqaris configuration.
 * @author Bogdan Drozdowski
 */
public class StatusTest {

	@Test
	public void testStatusNullFile() throws IOException {

		Status s = Status.checkStatus(null);
		TestHelper.verifyStatus(s);
		assertEquals(Boolean.FALSE, s.getCfgFileStatus());
	}
}
