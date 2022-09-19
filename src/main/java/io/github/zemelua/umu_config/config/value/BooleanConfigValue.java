package io.github.zemelua.umu_config.config.value;

import com.google.gson.JsonObject;

public class BooleanConfigValue extends AbstractConfigValue<Boolean> {
	public BooleanConfigValue(String name, Boolean defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.name, this.value);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.name)) {
			this.value = fileJson.get(this.name).getAsBoolean();
		}
	}
}
