package io.github.zemelua.umu_config.config.container;

import com.google.gson.JsonObject;

import java.nio.file.Path;

public interface IConfigContainer {
	Path getPath();

	void saveTo(JsonObject fileJson);

	void loadFrom(JsonObject fileJson);
}
