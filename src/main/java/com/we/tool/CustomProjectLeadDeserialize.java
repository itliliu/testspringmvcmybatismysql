package com.we.tool;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.ProjectLead;

public class CustomProjectLeadDeserialize extends JsonDeserializer<ProjectLead> {

	@Override
	public ProjectLead deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String data = parser.getText();
		return mapper.readValue(data, ProjectLead.class);
	}

}
