package io.github.zemelua.umu_config.config;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.client.gui.AbstractConfigEntry;
import io.github.zemelua.umu_config.client.gui.ConfigScreen;

public interface IConfigValue<T> {
	T getValue();

	T getDefaultValue();

	String getName();

	void saveTo(JsonObject fileJson);

	void loadFrom(JsonObject fileJson);

	AbstractConfigEntry<T> createEntry(ConfigScreen.ConfigListWidget parent);
}
