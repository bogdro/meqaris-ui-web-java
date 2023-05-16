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

package bogdrosoft.meqaris.ui.web.spring.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bogdrosoft.meqaris.ui.web.spring.controller.Chooser;
import bogdrosoft.meqaris.ui.web.spring.db.DbManager;
import bogdrosoft.meqaris.ui.web.spring.db.MeqConfig;

@RestController
public class ConfigControllerRest {

	@GetMapping("/config")
	public List<MeqConfig> getConfig(
			@RequestParam(name = "file", required = false, defaultValue = Chooser.DEF_CFG_FILE_NAME)
			String fileName,
			@RequestParam(name = "name", required = false)
			String name
			) throws Exception {

		List<MeqConfig> res = new ArrayList<>();
		DbManager db = new DbManager(fileName);
		List<Map<String, Object>> config = db.getConfig();
		if (config != null) {

			int size = config.size();
			for (int i = 0; i < size; i++) {

				Map<String, Object> row = config.get(i);
				if (row == null) {
					continue;
				}
				if (name != null) {
					Object nameCol = row.get("c_name");
					if (nameCol != null && name.equals(nameCol.toString())) {

						res.add(MeqConfig.buildFromMap(row));
						break;
					}
				}
				else {
					res.add(MeqConfig.buildFromMap(row));
				}
			}
		}
		return res;
	}
}
