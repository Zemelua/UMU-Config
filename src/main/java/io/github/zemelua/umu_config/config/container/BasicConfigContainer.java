package io.github.zemelua.umu_config.config.container;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.config.IConfigValue;

import java.nio.file.Path;
import java.util.List;

public class BasicConfigContainer implements IConfigContainer {
	private final String name;
	private final ImmutableList<IConfigValue<?>> values;

	public BasicConfigContainer(String name, IConfigValue<?>... values) {
		this.name = name;
		this.values = ImmutableList.copyOf(values);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<IConfigValue<?>> getValues() {
		return this.values;
	}

	@Override
	public Path getPath() {
		return ConfigHandler.getConfigPath().resolve(this.name + ".json");
	}

	@Override
	public void insertIfAbsent(JsonObject fileJson) {
		this.values.stream()
				.filter(value -> !fileJson.has(value.getName()))
				.forEach(value -> value.saveTo(fileJson));
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		this.values.forEach(value -> value.saveTo(fileJson));

		UMUConfig.LOGGER.info("Saved config: " + this.name);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		this.values.forEach(value -> value.loadFrom(fileJson));

		UMUConfig.LOGGER.info("Loaded config: " + this.name);
	}

	public static class Builder {
		private final String name;

		public Builder(String name) {
			this.name = name;
		}

		public BasicConfigContainer build() {
			return new BasicConfigContainer(this.name);
		}
	}
}
