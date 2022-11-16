/*
 * Copyright (C) 2022 Bogdan 'bogdro' Drozdowski, bogdro (at) users . sourceforge . net
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

package bogdrosoft.meqaris.ui.web.spring.controller;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The "chooser" bean for web pages.
 * @author Bogdan Drozdowski
 */
public class Chooser {

	/**
	 * The name of the "configuration" data to get.
	 */
	public static final String FORM_PARAM_NAME_CONFIG = "meq_config";

	/**
	 * The name of the "resources" data to get.
	 */
	public static final String FORM_PARAM_NAME_RESOURCES = "meq_res";

	/**
	 * The name of the "resource reservations" data to get.
	 */
	public static final String FORM_PARAM_NAME_RESERVATIONS = "meq_res_reserv";

	/**
	 * The name of the bean that holds the configuration data.
	 */
	public static final String ATTR_CONFIG = "meq_cfg";

	/**
	 * The name of the bean that holds the resource data.
	 */
	public static final String ATTR_RES = "meq_resources";

	/**
	 * The name of the bean that holds the resource reservation data.
	 */
	public static final String ATTR_RES_RESERV = "meq_resources_reserv";

	/**
	 * The default name of the configuration file.
	 */
	public static final String DEF_CFG_FILE_NAME = "/etc/meqaris.ini";

	@NotNull
	@Size(min = 1)
	private String fileName;

	/**
	 * Gets the filename.
	 * @return the current filename.
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the filename.
	 * @param fileName The filename to set.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
