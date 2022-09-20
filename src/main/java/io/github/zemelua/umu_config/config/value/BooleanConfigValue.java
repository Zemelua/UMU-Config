package io.github.zemelua.umu_config.config.value;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.client.gui.AbstractConfigEntry;
import io.github.zemelua.umu_config.client.gui.BooleanConfigEntry;
import io.github.zemelua.umu_config.client.gui.ConfigScreen;

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

	@Override
	public AbstractConfigEntry<Boolean> createEntry(ConfigScreen.ConfigListWidget parent) {
		return new BooleanConfigEntry(parent, this);
	}
}
