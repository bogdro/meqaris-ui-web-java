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

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The Spring controller for the /error page.
 * @author Bogdan Drozdowski
 */
@Controller
public class MeqErrorController
implements ErrorController /* CRUCIAL! */ {

	/**
	 * The name of the model attribute with the exception.
	 */
	public static final String MODEL_ATTR_EX = "meq_err_exception";

	/**
	 * The name of the model attribute with the error message.
	 */
	public static final String MODEL_ATTR_MSG = "meq_err_message";

	/**
	 * The name of the model attribute with the status code.
	 */
	public static final String MODEL_ATTR_CODE = "meq_err_code";

	/**
	 * Serves the GET requests to show an error.
	 * @param req The request to process.
	 * @param model The Spring model.
	 * @return The name of the view to forward to.
	 */
	@GetMapping(path = "/error")
	public String getErrorView(
			HttpServletRequest req,
			Model model
			) {

		Throwable err = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		if (err != null) {
			StringWriter sw = new StringWriter();
			err.printStackTrace(new PrintWriter(sw));
			model.addAttribute(MODEL_ATTR_EX, sw.toString());
		}
		Object msg = req.getAttribute(RequestDispatcher.ERROR_MESSAGE);
		if (StringUtils.hasText(String.valueOf(msg))) {

			model.addAttribute(MODEL_ATTR_MSG, msg);
		} else {

			model.addAttribute(MODEL_ATTR_MSG, err);
		}
		model.addAttribute(MODEL_ATTR_CODE,
			req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		return "error";
	}
}
