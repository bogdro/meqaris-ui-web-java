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
		if (rsrc != null) {
			for (Map<String, Object> row : rsrc) {

				if (row == null) {
					continue;
				}
				return new ResponseEntity<MeqResources>(
						MeqResources.buildFromMap(row), HttpStatus.OK);
			}
		}
		return new ResponseEntity<MeqResources>(
				(MeqResources)null, HttpStatus.NOT_FOUND);
	}
}
