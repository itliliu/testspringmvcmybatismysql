package com.we.tool;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.Pricing;

/**
 * Deserializer Pricing
 * 
 * @author hongfengma
 */
public class CustomPricingDeserialize extends JsonDeserializer<Pricing> {

	@Override
	public Pricing deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String data = p.getText();
		ObjectMapper mapper = new ObjectMapper();
		Pricing pricing = mapper.readValue(data, new TypeReference<Pricing>() {
		});
		return pricing;
	}

}
