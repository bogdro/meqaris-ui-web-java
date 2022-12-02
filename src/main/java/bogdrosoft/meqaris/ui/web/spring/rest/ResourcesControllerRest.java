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
import bogdrosoft.meqaris.ui.web.spring.db.MeqResources;

@RestController
public class ResourcesControllerRest {

	@GetMapping("/resources")
	public List<MeqResources> getResources(
			@RequestParam(name = "file", required = false, defaultValue = Chooser.DEF_CFG_FILE_NAME)
			String fileName
			) throws Exception {

		List<MeqResources> res = new ArrayList<>();
		DbManager db = new DbManager(fileName);
		List<Map<String, Object>> rsrc = db.getResources();
		if (rsrc != null) {
			for (Map<String, Object> row : rsrc) {

				if (row == null) {
					continue;
				}
				res.add(MeqResources.buildFromMap(row));
			}
		}
		return res;
	}
	
	@GetMapping("/resource/{id}")
	public HttpEntity<MeqResources> getResource(
			@RequestParam(name = "file", required = false, defaultValue = Chooser.DEF_CFG_FILE_NAME)
			String fileName,
			@PathVariable(name = "id", required = true)
			String id
			) throws Exception {

		DbManager db = new DbManager(fileName);
		List<Map<String, Object>> rsrc = db.getResourceById(Long.valueOf(id));
		MeqResources ret = null;
		if (rsrc != null && ! rsrc.isEmpty()) {
			Map<String, Object> row = rsrc.get(0);
			if (row != null) {
				ret = MeqResources.buildFromMap(row);
			}
		}
		HttpStatus code = HttpStatus.OK;
		if (ret == null) {
			code = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<MeqResources>(ret, code);
	}
}
