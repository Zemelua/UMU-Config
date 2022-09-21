package io.github.zemelua.umu_config.config.container;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.config.IConfigValue;

import java.nio.file.Path;
import java.util.List;

public interface IConfigContainer {
	String getName();

	List<IConfigValue<?>> getValues();

	Path getPath();

	void insertIfAbsent(JsonObject fileJson);

	void saveTo(JsonObject fileJson);

	void loadFrom(JsonObject fileJson);
}
