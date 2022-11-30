package bogdrosoft.meqaris.ui.web.spring.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.RequestDispatcher;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class MeqErrorControllerTest {

	private static final String TEXT = "test-exception";
	private static final String ERR_TEXT = "test-error-msg";
	private static final Integer ERR_CODE = Integer.valueOf(555);

	@Autowired
	private MockMvc mvc;

	@Test
	public void testErrorPageContent() throws Exception {

		mvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_EXCEPTION, new Exception(TEXT))
				.requestAttr(RequestDispatcher.ERROR_MESSAGE, ERR_TEXT)
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, ERR_CODE)
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Error - Meqaris Web UI")))
			.andExpect(content()
				.string(containsString(TEXT)))
			.andExpect(content()
				.string(containsString(ERR_TEXT)))
			.andExpect(content()
				.string(containsString(ERR_CODE.toString())))
			;
	}

	@Test
	public void testErrorPageContentMissingEx() throws Exception {

		mvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_MESSAGE, ERR_TEXT)
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, ERR_CODE)
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Error - Meqaris Web UI")))
			.andExpect(content()
				.string(containsString(ERR_TEXT)))
			.andExpect(content()
				.string(containsString(ERR_CODE.toString())))
			;
	}

	@Test
	public void testErrorPageContentMissingMsg() throws Exception {

		mvc.perform(get("/error")
				.requestAttr(RequestDispatcher.ERROR_EXCEPTION, new Exception(TEXT))
				.requestAttr(RequestDispatcher.ERROR_MESSAGE, "")
				.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, ERR_CODE)
				)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string(containsString("Error - Meqaris Web UI")))
			.andExpect(content()
				.string(containsString(TEXT)))
			.andExpect(content()
				.string(containsString(ERR_CODE.toString())))
			;
	}
}
