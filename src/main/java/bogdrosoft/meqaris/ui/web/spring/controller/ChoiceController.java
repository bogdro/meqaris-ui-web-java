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

package bogdrosoft.meqaris.ui.web.spring.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bogdrosoft.meqaris.ui.web.spring.Status;
import bogdrosoft.meqaris.ui.web.spring.db.DbManager;

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
	private static final String MODEL_ATTR_STATUS = "status";

	protected static final String PARAM_CFG_NAME = "cfg";

	private DbManager db;

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

			List<Map<String, Object>> res = db.getConfig();
			model.addAttribute(Chooser.ATTR_CONFIG, res);

		}
		if (Chooser.FORM_PARAM_NAME_RESOURCES.equals(cfgName)
				|| Chooser.FORM_PARAM_NAME_RESERVATIONS.equals(cfgName)
				|| Chooser.FORM_PARAM_NAME_CALDAV_SERVERS_RES.equals(cfgName)) {
		
			List<Map<String, Object>> res = db.getResources();
			model.addAttribute(Chooser.ATTR_RES, res);

		}
		if (Chooser.FORM_PARAM_NAME_RESERVATIONS.equals(cfgName)) {

			List<Map<String, Object>> res = db.getResourceReservations();
			model.addAttribute(Chooser.ATTR_RES_RESERV, res);

		}
		if (Chooser.FORM_PARAM_NAME_EVENTS.equals(cfgName)
				|| Chooser.FORM_PARAM_NAME_RESERVATIONS.equals(cfgName)) {

			List<Map<String, Object>> res = db.getEvents();
			model.addAttribute(Chooser.ATTR_EVENTS, res);

		}
		if (Chooser.FORM_PARAM_NAME_CALDAV_SERVERS.equals(cfgName)
				|| Chooser.FORM_PARAM_NAME_CALDAV_SERVERS_RES.equals(cfgName)) {

			List<Map<String, Object>> res = db.getCalDavServers();
			model.addAttribute(Chooser.ATTR_CALDAV_SERVERS, res);

		}
		if (Chooser.FORM_PARAM_NAME_CALDAV_SERVERS_RES.equals(cfgName)) {

			List<Map<String, Object>> res = db.getCalDavServersResources();
			model.addAttribute(Chooser.ATTR_CALDAV_SERVERS_RES, res);
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
			BindingResult res,	// NOTE: this MUST be the next parameter after the Chooser!
			Model model
	) throws IOException {

		model.addAttribute(IndexController.MODEL_ATTR_CHOOSER, c);
		if (res.hasErrors()) {

			// don't redirect or the error is lost
			return IndexController.INDEX_VIEW_NAME;
		}

		String name = c.getFileName();
		try {
			db = new DbManager(name);
		}
		catch (Exception ex) {

			String msg = ex.getMessage();
			res.rejectValue("fileName", "fileName.error",
					(msg != null)? msg : "Check the configuration file. Exception: " + ex);
			// don't redirect or the error is lost
			return IndexController.INDEX_VIEW_NAME;
		}
		model.addAttribute(MODEL_ATTR_DB_CONN, db);
		model.addAttribute(MODEL_ATTR_STATUS, Status.checkStatus(name));

		return CHOICE_VIEW_NAME;
	}
}
