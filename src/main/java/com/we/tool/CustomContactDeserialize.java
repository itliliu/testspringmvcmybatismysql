package com.we.tool;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.Contact;

/**
 * Deserializer Contact
 * 
 * @author hongfengma
 */
public class CustomContactDeserialize extends JsonDeserializer<List<Contact>> {

	@Override
	public List<Contact> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String data = p.getText();
		ObjectMapper mapper = new ObjectMapper();
		List<Contact> contact = mapper.readValue(data, new TypeReference<List<Contact>>() {
		});
		return contact;
	}

}
