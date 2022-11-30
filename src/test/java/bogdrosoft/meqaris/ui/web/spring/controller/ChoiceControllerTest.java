package bogdrosoft.meqaris.ui.web.spring.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import bogdrosoft.meqaris.ui.web.spring.TestHelper;

@WebMvcTest
public class ChoiceControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testChoosePageContent() throws Exception {

		mvc.perform(post("/choose")
				.param("fileName", TestHelper.getFullPathFor("good.ini")))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
					.string(containsString("Select data to display")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=" + Chooser.FORM_PARAM_NAME_CONFIG))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Configuration")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=" + Chooser.FORM_PARAM_NAME_RESOURCES))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Bookable resources")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=" + Chooser.FORM_PARAM_NAME_RESERVATIONS))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Resource reservations")));

		mvc.perform(get("/choose?" + ChoiceController.PARAM_CFG_NAME + "=blah"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
					// expect to stay on the same page
				.string(containsString("Select data to display")));
	}

	@Test
	public void testChooseMissingFileParam() throws Exception {

		mvc.perform(post("/choose"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
					// expect to go back to the index page
				.string(containsString("Select a configuration file - Meqaris Web UI")));
	}

	@Test
	public void testChooseMissingFile() throws Exception {

		mvc.perform(post("/choose")
				.param("fileName", "blah"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
					// expect to go back to the index page
				.string(containsString("Select a configuration file - Meqaris Web UI")));
	}

	@Test
	public void testFileBadSection() throws Exception {

		mvc.perform(post("/choose")
				.param("fileName", TestHelper.getFullPathFor("bad-section.ini")))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
					// expect to go back to the index page
				.string(containsString("Select a configuration file - Meqaris Web UI")));
	}
}
