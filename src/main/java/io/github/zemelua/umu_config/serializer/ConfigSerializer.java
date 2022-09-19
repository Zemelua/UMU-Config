package io.github.zemelua.umu_config.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.github.zemelua.umu_config.config.container.IConfigContainer;

import java.lang.reflect.Type;

public class ConfigSerializer implements JsonSerializer<IConfigContainer> {
	@Override
	public JsonElement serialize(IConfigContainer data, Type dataType, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		data.saveTo(jsonObject);

		return jsonObject;
	}
}
