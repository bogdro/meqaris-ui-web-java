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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The Spring controller for the root/index page.
 * @author Bogdan Drozdowski
 */
@Controller
public class IndexController {

	/**
	 * The name of the index/root view.
	 */
	public static final String INDEX_VIEW_NAME = "index";

	/**
	 * The name of the chooser bean.
	 */
	public static final String MODEL_ATTR_CHOOSER = "chooser";

	/**
	 * Serves the root page.
	 * @param model The Spring model.
	 * @return The name of the view to forward to.
	 */
	@GetMapping("/")
	public String indexPage(Model model) {

		Chooser c = new Chooser();
		c.setFileName("/etc/meqaris.ini");
		model.addAttribute(MODEL_ATTR_CHOOSER, c);
		return INDEX_VIEW_NAME;
	}
}
