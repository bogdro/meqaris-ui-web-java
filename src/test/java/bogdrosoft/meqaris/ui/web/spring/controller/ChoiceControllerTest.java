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

package bogdrosoft.meqaris.ui.web.spring.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import bogdrosoft.meqaris.ui.web.spring.TestHelper;

@WebMvcTest
public class ChoiceControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testChoosePageContent() throws Exception {

		mvc.perform(post("/choose")
				.param("fileName", TestHelper.getFullPathFor("good.ini")))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
					.string(containsString("Select data to display")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=" + Chooser.FORM_PARAM_NAME_CONFIG))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Description")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=" + Chooser.FORM_PARAM_NAME_RESOURCES))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Bookable resources")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=" + Chooser.FORM_PARAM_NAME_RESERVATIONS))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Resource ID")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=" + Chooser.FORM_PARAM_NAME_EVENTS))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Event ID")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=" + Chooser.FORM_PARAM_NAME_CALDAV_SERVERS))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Realm")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=" + Chooser.FORM_PARAM_NAME_CALDAV_SERVERS_RES))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Resource ID")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=blah"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
					// expect to stay on the same page
				.string(containsString("Select data to display")));
	}

	@Test
	public void testChooseMissingFileParam() throws Exception {

		mvc.perform(post("/choose"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
					// expect to go back to the index page
				.string(containsString("Select a configuration file - Meqaris Web UI")));
	}

	@Test
	public void testChooseMissingFile() throws Exception {

		mvc.perform(post("/choose")
				.param("fileName", "blah"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
					// expect to go back to the index page
				.string(containsString("Select a configuration file - Meqaris Web UI")));
	}

	@Test
	public void testFileBadSection() throws Exception {

		mvc.perform(post("/choose")
				.param("fileName", TestHelper.getFullPathFor("bad-section.ini")))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
					// expect to go back to the index page
				.string(containsString("Select a configuration file - Meqaris Web UI")));
	}
}
