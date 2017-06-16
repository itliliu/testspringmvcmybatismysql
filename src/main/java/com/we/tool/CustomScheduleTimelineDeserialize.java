package com.we.tool;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.bean.ScheduleTimeline;

public class CustomScheduleTimelineDeserialize extends JsonDeserializer<List<ScheduleTimeline>> {

	@Override
	public List<ScheduleTimeline> deserialize(JsonParser parser, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String data = parser.getText();
		List<ScheduleTimeline> scheduleTimelines = mapper.readValue(data, new TypeReference<List<ScheduleTimeline>>() {
		});
		return scheduleTimelines;
	}

}
