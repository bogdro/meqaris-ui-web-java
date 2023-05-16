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
import bogdrosoft.meqaris.ui.web.spring.db.MeqCalDavServersResources;

@RestController
public class CalDavServersResourcesControllerRest {

	@GetMapping("/caldav_servers_res")
	public List<MeqCalDavServersResources> getCalDavServersResources(
			@RequestParam(name = "file", required = false, defaultValue = Chooser.DEF_CFG_FILE_NAME)
			String fileName
			) throws Exception {

		List<MeqCalDavServersResources> res = new ArrayList<>();
		DbManager db = new DbManager(fileName);
		List<Map<String, Object>> resv = db.getCalDavServersResources();
		if (resv != null) {
			int size = resv.size();
			for (int i = 0; i < size; i++) {

				Map<String, Object> row = resv.get(i);

				if (row == null) {
					continue;
				}
				res.add(MeqCalDavServersResources.buildFromMap(row));
			}
		}
		return res;
	}

	@GetMapping("/caldav_server_res/{id}")
	public HttpEntity<MeqCalDavServersResources> getCalDavServerResourcesByServerId(
			@RequestParam(name = "file", required = false, defaultValue = Chooser.DEF_CFG_FILE_NAME)
			String fileName,
			@PathVariable(name = "id", required = true)
			String id
			) throws Exception {

		DbManager db = new DbManager(fileName);
		List<Map<String, Object>> resv = db.getCalDavServerResourcesByServerId(Long.valueOf(id));
		MeqCalDavServersResources ret = null;
		if (resv != null && ! resv.isEmpty()) {
			Map<String, Object> row = resv.get(0);
			if (row != null) {
				ret = MeqCalDavServersResources.buildFromMap(row);
			}
		}
		HttpStatus code = HttpStatus.OK;
		if (ret == null) {
			code = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<MeqCalDavServersResources>(ret, code);
	}
}
