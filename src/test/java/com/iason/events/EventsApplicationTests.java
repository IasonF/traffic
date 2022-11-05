package com.iason.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.iason.events.domain.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventsApplicationTests {

	@Autowired
	protected MockMvc mvc;

	@Test
	void createEventTest() throws Exception {
		mvc.perform(post("/events")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\n" +
								"\t\"type\": \"SPEED\", \n" +
								"\t\"licensePlate\": \"123ABC\",\n" +
								"\t\"speed\": \"100\",\n" +
								"\t\"limit\": \"80\",\n" +
								"\t\"unit\": \"km/h\"\n" +
								"}")
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.licensePlate", equalTo("123ABC")))
				.andExpect(jsonPath("$.limit", equalTo(80.0)));
	}

	@Test
	void getViolationsTest() throws Exception {
		UUID eventId = createAnEventThatIsViolation();
		System.out.println(eventId);
		MvcResult mvcResult = mvc.perform((get("/events/violations")))
				.andExpect(status().isOk())
				.andReturn();
		assertTrue(mvcResult.getResponse().getContentAsString().length() >= UUID.randomUUID().toString().length());
	}

	@Test
	void getSummaryTest() throws Exception {
		MvcResult response = mvc.perform((get("/events/violations/summary")))
				.andExpect(status().isOk())
				.andReturn();
		assertTrue(response.getResponse().getContentAsString().contains("TotalPaid"));
		assertTrue(response.getResponse().getContentAsString().contains("NotPaidYet"));
	}
	
	private UUID createAnEventThatIsViolation() throws Exception {
		MockHttpServletResponse mvcResult = mvc.perform(post("/events")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\n" +
							"\t\"type\": \"SPEED\", \n" +
							"\t\"licensePlate\": \"123ABC\",\n" +
							"\t\"speed\": \"100\",\n" +
							"\t\"limit\": \"80\",\n" +
							"\t\"unit\": \"km/h\"\n" +
							"}")
			)
			.andReturn()
			.getResponse();
		String json = mvcResult.getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		Event createdEvent = mapper.readValue(json, Event.class);
		return createdEvent.getId();
	}

}
