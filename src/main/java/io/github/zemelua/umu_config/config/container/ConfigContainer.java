package io.github.zemelua.umu_config.config.container;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.config.IConfigElement;
import io.github.zemelua.umu_config.config.category.IConfigCategory;
import io.github.zemelua.umu_config.config.value.IConfigValue;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigContainer implements IConfigContainer {
	private final Identifier ID;
	private final List<IConfigElement> elements;

	public ConfigContainer(Identifier ID, IConfigElement... elements) {
		this.ID = ID;
		this.elements = ImmutableList.copyOf(elements);
	}

	@Override
	public Identifier getID() {
		return this.ID;
	}

	@Override
	public List<IConfigElement> getElements() {
		return this.elements;
	}

	@Override
	public Path getPath() {
		return ConfigFileManager.getConfigPath().resolve(this.ID.getPath() + ".json");
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		this.elements.forEach(value -> value.saveTo(fileJson));

		UMUConfig.LOGGER.info("Saved config to file: " + this.ID.toString());
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		this.elements.forEach(value -> value.loadFrom(fileJson));

		UMUConfig.LOGGER.info("Loaded config from file: " + this.ID.toString());
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		this.elements.forEach(value -> value.saveTo(sendNBT));

		UMUConfig.LOGGER.info("Saved config to packet: " + this.ID.toString());
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		this.elements.forEach(value -> value.loadFrom(receivedNBT));

		UMUConfig.LOGGER.info("Loaded config from packet: " + this.ID.toString());
	}

	public static class Builder {
		private final Identifier ID;
		private final List<IConfigElement> elements = new ArrayList<>();

		public Builder(Identifier ID) {
			this.ID = ID;
		}

		public Builder addValue(IConfigValue<?> value) {
			this.elements.add(value);

			return this;
		}

		public Builder addCategory(IConfigCategory category) {
			this.elements.add(category);

			return this;
		}

		public ConfigContainer build() {
			return new ConfigContainer(this.ID, this.elements.toArray(new IConfigElement[0]));
		}
	}
}
