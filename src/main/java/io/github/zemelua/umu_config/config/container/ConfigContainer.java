package io.github.zemelua.umu_config.config.container;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.minecraft.nbt.NbtCompound;

import java.nio.file.Path;
import java.util.List;

public class ConfigContainer implements IConfigContainer {
	private final String name;
	private final ImmutableList<IConfigValue<?>> values;

	public ConfigContainer(String name, IConfigValue<?>... values) {
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
	public void saveTo(JsonObject fileJson) {
		this.values.forEach(value -> value.saveTo(fileJson));

		UMUConfig.LOGGER.info("Saved config from file: " + this.name);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		this.values.forEach(value -> value.loadFrom(fileJson));

		UMUConfig.LOGGER.info("Loaded config from file: " + this.name);
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		this.values.forEach(value -> value.saveTo(sendNBT));

		UMUConfig.LOGGER.info("Saved config from packet: " + this.name);
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		this.values.forEach(value -> value.loadFrom(receivedNBT));

		UMUConfig.LOGGER.info("Loaded config from packet: " + this.name);
	}

	public static class Builder {
		private final String name;

		public Builder(String name) {
			this.name = name;
		}

		public ConfigContainer build() {
			return new ConfigContainer(this.name);
		}
	}
}
