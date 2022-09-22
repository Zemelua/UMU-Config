package io.github.zemelua.umu_config.config;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.client.gui.AbstractConfigEntry;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import static net.fabricmc.api.EnvType.*;

public interface IConfigValue<T> {
	T getValue();

	void setValue(T value);

	T getDefaultValue();

	String getName();

	void saveTo(JsonObject fileJson);

	void loadFrom(JsonObject fileJson);

	void saveTo(NbtCompound sendNBT);

	void loadFrom(NbtCompound receivedNBT);

	@Environment(CLIENT) AbstractConfigEntry<T, ? extends IConfigValue<T>> createEntry();

	@Environment(CLIENT) Text getValueText(T value);
}
