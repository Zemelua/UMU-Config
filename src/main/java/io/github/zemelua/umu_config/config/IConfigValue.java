package io.github.zemelua.umu_config.config;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.client.gui.entry.AbstractConfigEntry;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import static net.fabricmc.api.EnvType.*;

public interface IConfigValue<T> {
	T getValue();

	void setValue(T value);

	T getDefaultValue();

	void saveTo(JsonObject fileJson);

	void loadFrom(JsonObject fileJson);

	void saveTo(NbtCompound sendNBT);

	void loadFrom(NbtCompound receivedNBT);

	Identifier getID();

	@Environment(CLIENT) AbstractConfigEntry<T, ? extends IConfigValue<T>> createEntry();

	@Environment(CLIENT) Text getValueText(T value);

	@Environment(CLIENT)
	default Text getName() {
		return Text.translatable(Util.createTranslationKey("config.value", this.getID()));
	}

	@Environment(CLIENT)
	default Text getTooltip() {
		return Text.translatable(Util.createTranslationKey("config.value.tooltip", this.getID()));
	}
}
