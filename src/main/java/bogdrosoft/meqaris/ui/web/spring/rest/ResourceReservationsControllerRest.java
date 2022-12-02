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
import bogdrosoft.meqaris.ui.web.spring.db.MeqResourceReservations;

@RestController
public class ResourceReservationsControllerRest {

	@GetMapping("/reservations")
	public List<MeqResourceReservations> getResourceReservations(
			@RequestParam(name = "file", required = false, defaultValue = Chooser.DEF_CFG_FILE_NAME)
			String fileName
			) throws Exception {

		List<MeqResourceReservations> res = new ArrayList<>();
		DbManager db = new DbManager(fileName);
		List<Map<String, Object>> resv = db.getResourceReservations();
		if (resv != null) {
			for (Map<String, Object> row : resv) {

				if (row == null) {
					continue;
				}
				res.add(MeqResourceReservations.buildFromMap(row));
			}
		}
		return res;
	}
	
	@GetMapping("/reservation/{id}")
	public HttpEntity<MeqResourceReservations> getResourceReservation(
			@RequestParam(name = "file", required = false, defaultValue = Chooser.DEF_CFG_FILE_NAME)
			String fileName,
			@PathVariable(name = "id", required = true)
			String id
			) throws Exception {

		DbManager db = new DbManager(fileName);
		List<Map<String, Object>> resv = db.getResourceReservationById(Long.valueOf(id));
		MeqResourceReservations ret = null;
		if (resv != null && ! resv.isEmpty()) {
			Map<String, Object> row = resv.get(0);
			if (row != null) {
				ret = MeqResourceReservations.buildFromMap(row);
			}
		}
		HttpStatus code = HttpStatus.OK;
		if (ret == null) {
			code = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<MeqResourceReservations>(ret, code);
	}
}
