/*
 * Copyright (C) 2025 Bogdan 'bogdro' Drozdowski, bogdro (at) users . sourceforge . net
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

import java.util.ArrayList;
import java.util.List;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * The class for a temporary container with a test database.
 * @author Bogdan Drozdowski
 */
public class TestDatabase extends PostgreSQLContainer<TestDatabase>
{
	private static final List<String> PORT_BINDINGS = new ArrayList<>(1);
	private static TestDatabase instance;

	static
	{
		PORT_BINDINGS.add("55432:5432");
	}

	public TestDatabase(DockerImageName imageName)
	{
		super(imageName);
	}

	/**
	 * Returns a new test container database instance,
	 *  creating one if necessary.
	 * @return a new test container database instance.
	 */
	public static TestDatabase getInstance()
	{
		if (instance == null)
		{
			instance = new TestDatabase(DockerImageName.parse("postgres:17"))
				.withUsername("meqaris")
				.withPassword("meqaris01")
				.withDatabaseName("meqaris-test")
				.withInitScripts(
					"sql/meqaris-full.pgsql",
					"sql/data.pgsql"
				);
			instance.setPortBindings(PORT_BINDINGS);
		}
		return instance;
	}
}
