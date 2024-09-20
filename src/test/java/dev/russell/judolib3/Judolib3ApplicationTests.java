package dev.russell.judolib3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class Judolib3ApplicationTests {
	@Autowired
	private MockMvc mockServlet;

	@Test
	void contextLoads() {
	}

	@Test
	void checkIndex() throws Exception {
		mockServlet.perform(get(URI.create("/v1/")))
				.andExpect(status().isOk());
	}

	@Test
	void getVideo() throws Exception {
		var response = mockServlet
				.perform(get(
						URI.create("/v1/watch/seoi-nage"))
				)
				.andExpect(status().isOk())
				.andReturn();
	}
}
