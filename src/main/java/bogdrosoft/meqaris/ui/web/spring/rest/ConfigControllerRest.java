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
			for (Map<String, Object> row : config) {

				if (row == null) {
					continue;
				}
				if (name != null) {
					Object name_col = row.get("c_name");
					if (name_col != null && name.equals(name_col.toString())) {

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
