package io.github.zemelua.umu_config.config;

import com.google.gson.JsonObject;

public interface IConfigValue<T> {
	T getValue();

	void saveTo(JsonObject fileJson);

	void loadFrom(JsonObject fileJson);
}
