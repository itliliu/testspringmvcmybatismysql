package com.we.tool;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomListDeserialize extends JsonDeserializer<String> {

	@Override
	public String deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		StringBuffer buffer = new StringBuffer();
		ObjectMapper mapper = new ObjectMapper();
		String data = parser.getText();
		List<String> sources = mapper.readValue(data, new TypeReference<List<String>>() {
		});
		for (String source : sources) {
			if (source != null && source.trim().length() > 0) {
				buffer.append(source.trim());
				buffer.append(" &amp; ");
			}
		}
		if(buffer.lastIndexOf("&amp;") < 0){
			return buffer.toString();
		}
		return buffer.substring(0, buffer.lastIndexOf("&amp;")).toString();
	}

}
