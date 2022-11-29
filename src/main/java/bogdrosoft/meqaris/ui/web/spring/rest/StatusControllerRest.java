package bogdrosoft.meqaris.ui.web.spring.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bogdrosoft.meqaris.ui.web.spring.Status;
import bogdrosoft.meqaris.ui.web.spring.controller.Chooser;

@RestController
public class StatusControllerRest {

	@GetMapping("/status")
	public Status getConfig(
			@RequestParam(name = "file", required = false, defaultValue = Chooser.DEF_CFG_FILE_NAME)
			String fileName
			) throws Exception {

		return Status.checkStatus(fileName);
	}
}
