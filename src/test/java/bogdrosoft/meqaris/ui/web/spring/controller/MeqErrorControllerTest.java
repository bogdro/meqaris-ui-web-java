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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.RequestDispatcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * A test for the Spring controller for the /error page.
 * @author Bogdan Drozdowski
 */
@WebMvcTest
public class MeqErrorControllerTest {

	private static final String TEXT = "test-exception";
	private static final String ERR_TEXT = "test-error-msg";
	private static final Integer ERR_CODE = Integer.valueOf(555);

	@Autowired
	private MockMvc mvc;

	@Test
	public void testErrorPageContent() throws Exception {

		mvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_EXCEPTION, new Exception(TEXT))
				.requestAttr(RequestDispatcher.ERROR_MESSAGE, ERR_TEXT)
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, ERR_CODE)
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Error - Meqaris Web UI")))
			.andExpect(content()
				.string(containsString(TEXT)))
			.andExpect(content()
				.string(containsString(ERR_TEXT)))
			.andExpect(content()
				.string(containsString(ERR_CODE.toString())))
			;
	}

	@Test
	public void testErrorPageContentMissingEx() throws Exception {

		mvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_MESSAGE, ERR_TEXT)
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, ERR_CODE)
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Error - Meqaris Web UI")))
			.andExpect(content()
				.string(containsString(ERR_TEXT)))
			.andExpect(content()
				.string(containsString(ERR_CODE.toString())))
			;
	}

	@Test
	public void testErrorPageContentMissingMsg() throws Exception {

		mvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_EXCEPTION, new Exception(TEXT))
				.requestAttr(RequestDispatcher.ERROR_MESSAGE, "")
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, ERR_CODE)
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Error - Meqaris Web UI")))
			.andExpect(content()
				.string(containsString(TEXT)))
			.andExpect(content()
				.string(containsString(ERR_CODE.toString())))
			;
	}
}
