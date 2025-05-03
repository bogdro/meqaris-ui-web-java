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

import java.io.File;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import bogdrosoft.meqaris.ui.web.spring.db.DbManager;

/**
 * The class to represent the status of the Meqaris configuration.
 * @author Bogdan Drozdowski
 */
public class Status {

	private String cfgFileMsg;
	private String cfgMsg;
	private String log4PerlFileMsg;
	private String sqlDirMsg;
	private String lockDirMsg;
	private String dbConnMsg;

	private Boolean cfgFileStatus;
	private Boolean cfgStatus;
	private Boolean log4PerlFileStatus;
	private Boolean sqlDirStatus;
	private Boolean lockDirStatus;
	private Boolean dbConnStatus;

	public String getCfgFileMsg() {
		return cfgFileMsg;
	}

	public void setCfgFileMsg(String cfgFileMsg) {
		this.cfgFileMsg = cfgFileMsg;
	}

	public String getCfgMsg() {
		return cfgMsg;
	}

	public void setCfgMsg(String cfgMsg) {
		this.cfgMsg = cfgMsg;
	}

	public String getLog4PerlFileMsg() {
		return log4PerlFileMsg;
	}

	public void setLog4PerlFileMsg(String log4PerlFileMsg) {
		this.log4PerlFileMsg = log4PerlFileMsg;
	}

	public String getSqlDirMsg() {
		return sqlDirMsg;
	}

	public void setSqlDirMsg(String sqlDirMsg) {
		this.sqlDirMsg = sqlDirMsg;
	}

	public String getLockDirMsg() {
		return lockDirMsg;
	}

	public void setLockDirMsg(String lockDirMsg) {
		this.lockDirMsg = lockDirMsg;
	}

	public String getDbConnMsg() {
		return dbConnMsg;
	}

	public void setDbConnMsg(String dbConnMsg) {
		this.dbConnMsg = dbConnMsg;
	}

	public Boolean getCfgFileStatus() {
		return cfgFileStatus;
	}

	public void setCfgFileStatus(Boolean cfgFileStatus) {
		this.cfgFileStatus = cfgFileStatus;
	}

	public Boolean getCfgStatus() {
		return cfgStatus;
	}

	public void setCfgStatus(Boolean cfgStatus) {
		this.cfgStatus = cfgStatus;
	}

	public Boolean getLog4PerlFileStatus() {
		return log4PerlFileStatus;
	}

	public void setLog4PerlFileStatus(Boolean log4PerlFileStatus) {
		this.log4PerlFileStatus = log4PerlFileStatus;
	}

	public Boolean getSqlDirStatus() {
		return sqlDirStatus;
	}

	public void setSqlDirStatus(Boolean sqlDirStatus) {
		this.sqlDirStatus = sqlDirStatus;
	}

	public Boolean getLockDirStatus() {
		return lockDirStatus;
	}

	public void setLockDirStatus(Boolean lockDirStatus) {
		this.lockDirStatus = lockDirStatus;
	}

	public Boolean getDbConnStatus() {
		return dbConnStatus;
	}

	public void setDbConnStatus(Boolean dbConnStatus) {
		this.dbConnStatus = dbConnStatus;
	}

	public static Status checkStatus(String filePath) {

		Status s = new Status();

		s.setCfgFileMsg("Main configuration file: Not checked");
		s.setCfgMsg("Configuration: Not checked");
		s.setLog4PerlFileMsg("Log4perl configuration file: Not checked");
		s.setSqlDirMsg("SQL directory: Not checked");
		s.setLockDirMsg("Lock file directory: Not checked");
		s.setDbConnMsg("Database connection: Not checked");

		String dataErrMsg = "Configuration data: file '";
		if (filePath == null) {

			s.setCfgFileMsg("Main configuration file: file path not provided.");
			s.setCfgFileStatus(Boolean.FALSE);
			return s;
		}

		File mainCfg = new File(filePath);
		if (! mainCfg.exists() || ! mainCfg.canRead() || ! mainCfg.isFile()) {

			s.setCfgFileMsg("Main configuration file: '"
					+ filePath + "' doesn't exist, is not a file or cannot be read.");
			s.setCfgFileStatus(Boolean.FALSE);
			return s;
		}
		s.setCfgFileMsg("Main configuration file: OK");
		s.setCfgFileStatus(Boolean.TRUE);

		Ini ini;
		try {
			ini = new Ini(mainCfg);
		} catch (Exception e) {

			s.setCfgMsg("Main configuration file: file '"
					+ filePath + "' has invalid format.");
			s.setCfgStatus(Boolean.FALSE);
			return s;
		}

		Section meqarisSection;
		try {
			meqarisSection = ini.get("meqaris");
		} catch (Exception e) {

			s.setCfgMsg(dataErrMsg
					+ filePath + "' has invalid format: section 'meqaris' is missing.");
			s.setCfgStatus(Boolean.FALSE);
			return s;
		}

		if (meqarisSection == null) {

			s.setCfgMsg(dataErrMsg
					+ filePath + "' has invalid format: section 'meqaris' is missing.");
			s.setCfgStatus(Boolean.FALSE);
			return s;
		}

		String[] mainSettings = new String[]
				{"dbtype", "datadir", "log4perl_config_location", "lock_dir"};
		for (String ms : mainSettings) {

			if (! meqarisSection.containsKey(ms)) {

				s.setCfgMsg("Configuration data: "
						+ " section 'meqaris' is missing the setting '" + ms + "'.");
				s.setCfgStatus(Boolean.FALSE);
				return s;
			}
		}

		Ini.Section dbSection = ini.get(meqarisSection.get("dbtype"));
		if (dbSection == null) {

			s.setCfgMsg(dataErrMsg
					+ filePath + "' has invalid format: the database section is missing.");
			s.setCfgStatus(Boolean.FALSE);
			return s;
		}

		String[] dbSettings = new String[]
				{"username", "password", "dbname", "host", "port", "connect_timeout"};
		for (String ds : dbSettings) {

			if (! dbSection.containsKey(ds)) {

				s.setCfgMsg("Configuration data: "
						+ " database section is missing the setting '" + ds + "'.");
				s.setCfgStatus(Boolean.FALSE);
				return s;
			}
		}
		s.setCfgMsg("Configuration data: OK");
		s.setCfgStatus(Boolean.TRUE);

		String l4pPath = meqarisSection.get("log4perl_config_location");
		File log4perlCfg = new File(l4pPath);
		if (! log4perlCfg.exists() || ! log4perlCfg.canRead() || ! log4perlCfg.isFile()) {

			s.setLog4PerlFileMsg("Log4perl configuration file: file '"
					+ l4pPath + "' doesn't exist, is not a file or cannot be read.");
			s.setLog4PerlFileStatus(Boolean.FALSE);
			return s;
		}
		s.setLog4PerlFileMsg("Log4perl configuration file: OK");
		s.setLog4PerlFileStatus(Boolean.TRUE);

		String sqlPath = meqarisSection.get("datadir") + "/sql";
		File sqlDir = new File(sqlPath);
		if (! sqlDir.exists() || ! sqlDir.isDirectory()) {

			s.setSqlDirMsg("SQL directory: '"
					+ sqlPath + "' doesn't exist or is not a directory.");
			s.setSqlDirStatus(Boolean.FALSE);
			return s;
		}
		s.setSqlDirMsg("SQL directory: OK");
		s.setSqlDirStatus(Boolean.TRUE);

		String lockPath = meqarisSection.get("lock_dir");
		File lockDir = new File(lockPath);
		if (! lockDir.exists() || ! lockDir.isDirectory()) {

			s.setLockDirMsg("Lock file directory: '"
					+ lockPath + "' doesn't exist or is not a directory.");
			s.setLockDirStatus(Boolean.FALSE);
			return s;
		}
		s.setLockDirMsg("Lock file directory: OK");
		s.setLockDirStatus(Boolean.TRUE);

		DbManager db;
		try {
			db = new DbManager(filePath);
			db.getConfig();
		} catch (Exception e) {

			s.setDbConnMsg("Database connection: ERROR: '"
					+ e.getMessage() + "'.");
			s.setDbConnStatus(Boolean.FALSE);
			return s;
		}
		s.setDbConnMsg("Database connection: OK");
		s.setDbConnStatus(Boolean.TRUE);

		return s;
	}
}
