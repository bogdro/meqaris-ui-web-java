/*
 * Copyright (C) 2022-2023 Bogdan 'bogdro' Drozdowski, bogdro (at) users . sourceforge . net
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

import java.io.File;
import java.util.List;
import java.util.Map;

import org.ini4j.Ini;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DbManager {

	private JdbcTemplate jdbc;

	public DbManager(String fileName) throws Exception {

		if (fileName == null) {

			throw new IllegalArgumentException("Configuration file name not provided.");
		}

		File cfgFile = new File(fileName);
		if (! cfgFile.exists() || ! cfgFile.canRead()) {

			throw new IllegalArgumentException("Cannot read '" + fileName + "'");
		}

		Ini ini = new Ini(cfgFile);
		String dbType = ini.get("meqaris").get("dbtype");
		if (! "postgresql".equalsIgnoreCase(dbType)) {

			throw new IllegalArgumentException("Database type '"
					+ dbType + "' is not PostgreSQL");
		}
		Ini.Section dbSection = ini.get(dbType);

		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setUser(dbSection.get("username"));
		ds.setPassword(dbSection.get("password"));
		ds.setDatabaseName(dbSection.get("dbname"));
		ds.setServerNames(new String[] {dbSection.get("host")});
		ds.setPortNumbers(new int[] {Integer.parseInt(dbSection.get("port"))});
		ds.setConnectTimeout(Integer.parseInt(dbSection.get("connect_timeout", "30")));
		/*ds.setUrl("jdbc:postgresql://" + dbSection.get("host")
			+ ":" + dbSection.get("port") + "/meqaris");*/
		jdbc = new JdbcTemplate(ds);
	}

	public List<Map<String, Object>> getConfig() {

		return jdbc.queryForList(
				"select c_name, c_value, c_description"
				+ " from meq_config order by c_name");
	}

	public List<Map<String, Object>> getResources() {

		return jdbc.queryForList(
				"select r_id, r_name, r_email, r_description, r_enabled"
				+ " from meq_resources order by r_id");
	}

	public List<Map<String, Object>> getResourceById(Long id) {

		return jdbc.queryForList(
				"select r_id, r_name, r_email, r_description, r_enabled"
				+ " from meq_resources where r_id = ?", id);
	}

	public List<Map<String, Object>> getResourceReservations() {

		return jdbc.queryForList(
				"select rr_id, rr_r_id, rr_interval, rr_e_id"
				+ " from meq_resource_reservations"
				+ " order by lower(rr_interval) desc, rr_id desc");
	}

	public List<Map<String, Object>> getResourceReservationById(Long id) {

		return jdbc.queryForList(
				"select rr_id, rr_r_id, rr_interval, rr_e_id"
				+ " from meq_resource_reservations"
				+ " where rr_id = ?", id);
	}

	public List<Map<String, Object>> getEvents() {

		return jdbc.queryForList(
				"select e_id, e_entry_date, e_organiser, e_summary,"
				+ " e_dtstamp, e_uid, e_seq, e_data"
				+ " from meq_events"
				+ " order by e_id");
	}

	public List<Map<String, Object>> getEventById(Long id) {

		return jdbc.queryForList(
				"select e_id, e_entry_date, e_organiser, e_summary,"
				+ " e_dtstamp, e_uid, e_seq, e_data"
				+ " from meq_events"
				+ " where e_id = ?", id);
	}

	public List<Map<String, Object>> getCalDavServers() {

		return jdbc.queryForList(
				"select cals_id, cals_name, cals_url, cals_username,"
				+ " cals_password, cals_realm"
				+ " from meq_caldav_servers"
				+ " order by cals_id");
	}

	public List<Map<String, Object>> getCalDavServerById(Long id) {

		return jdbc.queryForList(
				"select cals_id, cals_name, cals_url, cals_username,"
				+ " cals_password, cals_realm"
				+ " from meq_caldav_servers"
				+ " where cals_id = ?", id);
	}

	public List<Map<String, Object>> getCalDavServersResources() {

		return jdbc.queryForList(
				"select calres_cals_id, calres_r_id"
				+ " from meq_caldav_servers_resources"
				+ " order by calres_cals_id");
	}

	public List<Map<String, Object>> getCalDavServerResourcesByServerId(Long id) {

		return jdbc.queryForList(
				"select calres_cals_id, calres_r_id"
				+ " from meq_caldav_servers_resources"
				+ " where calres_cals_id = ?", id);
	}
}
