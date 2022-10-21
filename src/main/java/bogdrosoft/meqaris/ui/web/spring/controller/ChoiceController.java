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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.ini4j.Ini;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The Spring controller for the /choose page.
 * @author Bogdan Drozdowski
 */
@Controller
public class ChoiceController {

	/**
	 * The name of the chooser view.
	 */
	public static final String CHOICE_VIEW_NAME = "choose";

	private static final String MODEL_ATTR_DB_CONN = "db_conn";
	private static final String PARAM_CFG_NAME = "cfg";

	private JdbcTemplate jdbc;

	/**
	 * Serves the GET requests to get the data.
	 * @param cfgName The name of the configuration to display.
	 * @param c The Chooser to pass between pages.
	 * @param model The Spring model.
	 * @return The name of the view to forward to.
	 */
	@GetMapping("/" + CHOICE_VIEW_NAME)
	public String choicePageGet(
			@RequestParam(name = PARAM_CFG_NAME) String cfgName,
			@ModelAttribute(name = IndexController.MODEL_ATTR_CHOOSER) Chooser c,
			Model model
		) {

		model.addAttribute(IndexController.MODEL_ATTR_CHOOSER, c);
		if (Chooser.FORM_PARAM_NAME_CONFIG.equals(cfgName)) {

			List<Map<String, Object>> res = jdbc.queryForList(
					"select c_name, c_value, c_description from meq_config order by c_name");
			model.addAttribute(Chooser.ATTR_CONFIG, res);

		} else if (Chooser.FORM_PARAM_NAME_RESOURCES.equals(cfgName)) {
		
			List<Map<String, Object>> res = jdbc.queryForList(
					"select r_id, r_name, r_email, r_description, r_enabled from meq_resources order by r_id");
			model.addAttribute(Chooser.ATTR_RES, res);

		} else if (Chooser.FORM_PARAM_NAME_RESERVATIONS.equals(cfgName)) {

			List<Map<String, Object>> res = jdbc.queryForList(
					"select rr_r_id, rr_interval, rr_organiser, rr_summary from meq_resource_reservations order by rr_id desc");
			model.addAttribute(Chooser.ATTR_RES_RESERV, res);
		}
		return CHOICE_VIEW_NAME;
	}

	/**
	 * Serves the POST requests to access the given file and the database.
	 * @param c The Chooser to pass between pages.
	 * @param model The Spring model.
	 * @return The name of the view to forward to.
	 * @throws IOException when the file cannot be read.
	 */
	@PostMapping("/" + CHOICE_VIEW_NAME)
	public String choicePagePost(
			@ModelAttribute(name = IndexController.MODEL_ATTR_CHOOSER) @Valid Chooser c,
			BindingResult res,	// NOTE: this MUST be the second parameter in the method!
			Model model
		) throws IOException {

		if (res.hasErrors()) {

			return IndexController.INDEX_VIEW_NAME;
		}

		model.addAttribute(IndexController.MODEL_ATTR_CHOOSER, c);
		String name = c.getFileName();
		if (name == null) {
			return IndexController.INDEX_VIEW_NAME;
		}

		File cfgFile = new File(name);
		if (! cfgFile.exists() || ! cfgFile.canRead()) {
			return IndexController.INDEX_VIEW_NAME;
		}

		Ini ini = new Ini(cfgFile);
		Ini.Section dbSection = ini.get(ini.get("meqaris").get("dbtype"));

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
		model.addAttribute(MODEL_ATTR_DB_CONN, jdbc);

		return CHOICE_VIEW_NAME;
	}
}
