package io.github.zemelua.umu_config.old.api.config.container;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.old.api.config.OldIConfigElement;
import io.github.zemelua.umu_config.old.api.config.category.OldIConfigCategory;
import io.github.zemelua.umu_config.old.api.config.value.OldOldIConfigValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ConfigContainer implements IConfigContainer {
	private final Identifier ID;
	private final List<OldIConfigElement> elements;
	private final Predicate<PlayerEntity> canEditTester;

	public ConfigContainer(Identifier ID, Predicate<PlayerEntity> canEditTester, OldIConfigElement... elements) {
		this.ID = ID;
		this.canEditTester = canEditTester;
		this.elements = ImmutableList.copyOf(elements);
	}

	@Override
	public List<OldIConfigElement> getElements() {
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

	@Override
	public Identifier getID() {
		return this.ID;
	}

	@Override
	public boolean canEdit(PlayerEntity player) {
		return this.canEditTester.test(player);
	}

	public static class Builder {
		private final Identifier ID;
		private final List<OldIConfigElement> elements = new ArrayList<>();
		private Predicate<PlayerEntity> canEditTester = player -> player.hasPermissionLevel(4);

		public Builder(Identifier ID) {
			this.ID = ID;
		}

		public Builder addValue(OldOldIConfigValue<?> value) {
			this.elements.add(value);

			return this;
		}

		public Builder addCategory(OldIConfigCategory value) {
			this.elements.add(value);

			return this;
		}

		public Builder canEditTester(Predicate<PlayerEntity> value) {
			this.canEditTester = value;

			return this;
		}

		public ConfigContainer build() {
			return new ConfigContainer(this.ID, this.canEditTester, this.elements.toArray(new OldIConfigElement[0]));
		}
	}
}
