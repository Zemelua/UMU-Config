package io.github.zemelua.umu_config.config.container;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.minecraft.nbt.NbtCompound;

import java.nio.file.Path;
import java.util.List;

public interface IConfigContainer {
	String getName();

	List<IConfigValue<?>> getValues();

	Path getPath();

	void saveTo(JsonObject fileJson);

	void loadFrom(JsonObject fileJson);

	void saveTo(NbtCompound sendNBT);

	void loadFrom(NbtCompound receivedNBT);
}
