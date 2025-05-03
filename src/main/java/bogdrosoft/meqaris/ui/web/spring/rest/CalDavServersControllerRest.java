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

package bogdrosoft.meqaris.ui.web.spring.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bogdrosoft.meqaris.ui.web.spring.controller.Chooser;
import bogdrosoft.meqaris.ui.web.spring.db.DbManager;
import bogdrosoft.meqaris.ui.web.spring.db.MeqCalDavServers;

/**
 * A ReST controller for the "/caldav_server(s)" URLs.
 * @author Bogdan Drozdowski
 */
@RestController
public class CalDavServersControllerRest {

	@GetMapping("/caldav_servers")
	public List<MeqCalDavServers> getCalDavServers(
			@RequestParam(name = "file", required = false, defaultValue = Chooser.DEF_CFG_FILE_NAME)
			String fileName
			) throws Exception {

		List<MeqCalDavServers> res = new ArrayList<>();
		DbManager db = new DbManager(fileName);
		List<Map<String, Object>> resv = db.getCalDavServers();
		if (resv != null) {
			int size = resv.size();
			for (int i = 0; i < size; i++) {

				Map<String, Object> row = resv.get(i);

				if (row == null) {
					continue;
				}
				res.add(MeqCalDavServers.buildFromMap(row));
			}
		}
		return res;
	}

	@GetMapping("/caldav_server/{id}")
	public HttpEntity<MeqCalDavServers> getCalDavServer(
			@RequestParam(name = "file", required = false, defaultValue = Chooser.DEF_CFG_FILE_NAME)
			String fileName,
			@PathVariable(name = "id", required = true)
			String id
			) throws Exception {

		DbManager db = new DbManager(fileName);
		List<Map<String, Object>> resv = db.getCalDavServerById(Long.valueOf(id));
		MeqCalDavServers ret = null;
		if (resv != null && ! resv.isEmpty()) {
			Map<String, Object> row = resv.get(0);
			if (row != null) {
				ret = MeqCalDavServers.buildFromMap(row);
			}
		}
		HttpStatus code = HttpStatus.OK;
		if (ret == null) {
			code = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<MeqCalDavServers>(ret, code);
	}
}
