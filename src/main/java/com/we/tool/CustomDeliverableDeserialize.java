package com.we.tool;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.Deliverable;

/**
 * Deserializer Deliverable
 * 
 * @author hongfengma
 */
public class CustomDeliverableDeserialize extends JsonDeserializer<List<Deliverable>> {

	@Override
	public List<Deliverable> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String data = p.getText();
		ObjectMapper mapper = new ObjectMapper();
		List<Deliverable> deliverables = mapper.readValue(data, new TypeReference<List<Deliverable>>() {
		});
		return deliverables;
	}

}
