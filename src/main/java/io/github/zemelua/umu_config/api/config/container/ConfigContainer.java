package io.github.zemelua.umu_config.api.config.container;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.api.config.IConfigElement;
import io.github.zemelua.umu_config.api.config.category.IConfigCategory;
import io.github.zemelua.umu_config.api.config.value.IConfigValue;
import io.github.zemelua.umu_config.config.ConfigFileManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigContainer implements IConfigContainer {
	private final Identifier id;
	private final ImmutableList<IConfigElement> elements;

	public ConfigContainer(Identifier id, IConfigElement... elements) {
		this.id = id;
		this.elements = ImmutableList.copyOf(elements);
	}

	@Override
	public List<IConfigElement> getElements() {
		return List.copyOf(this.elements);
	}

	@Override
	public Path getPath() {
		return ConfigFileManager.getConfigPath().resolve(this.id.getPath() + ".json");
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		this.elements.forEach(value -> value.saveTo(fileJson));

		UMUConfig.LOGGER.info("Saved config to file: " + this.id.toString());
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		this.elements.forEach(value -> value.loadFrom(fileJson));

		UMUConfig.LOGGER.info("Loaded config from file: " + this.id.toString());
	}

	@Override
	public void saveTo(NbtCompound sendingNBT) {
		this.elements.forEach(value -> value.saveTo(sendingNBT));
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		this.elements.forEach(value -> value.loadFrom(receivedNBT));
	}

	@Override
	public Identifier getID() {
		return this.id;
	}

	public static Builder builder(Identifier id) {
		return new Builder(id);
	}

	public static class Builder {
		private final Identifier id;
		private final List<IConfigElement> elements = new ArrayList<>();

		private Builder(Identifier id) {
			this.id = id;
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
			return new ConfigContainer(this.id, this.elements.toArray(new IConfigElement[0]));
		}
	}
}
