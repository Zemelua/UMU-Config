package io.github.zemelua.umu_config.config.container;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.List;

public class ConfigContainer implements IConfigContainer {
	private final Identifier ID;
	private final ImmutableList<IConfigValue<?>> values;

	public ConfigContainer(Identifier ID, IConfigValue<?>... values) {
		this.ID = ID;
		this.values = ImmutableList.copyOf(values);
	}

	@Override
	public Identifier getID() {
		return this.ID;
	}

	@Override
	public List<IConfigValue<?>> getValues() {
		return this.values;
	}

	@Override
	public Path getPath() {
		return ConfigHandler.getConfigPath().resolve(this.ID.getPath() + ".json");
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		this.values.forEach(value -> value.saveTo(fileJson));

		UMUConfig.LOGGER.info("Saved config to file: " + this.ID.toString());
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		this.values.forEach(value -> value.loadFrom(fileJson));

		UMUConfig.LOGGER.info("Loaded config from file: " + this.ID.toString());
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		this.values.forEach(value -> value.saveTo(sendNBT));

		UMUConfig.LOGGER.info("Saved config to packet: " + this.ID.toString());
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		this.values.forEach(value -> value.loadFrom(receivedNBT));

		UMUConfig.LOGGER.info("Loaded config from packet: " + this.ID.toString());
	}
}
