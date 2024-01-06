package io.github.zemelua.umu_config.api.config;

import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public interface IConfigElement {
	void saveTo(JsonObject fileJson);
	void loadFrom(JsonObject fileJson);
	void saveTo(NbtCompound sendingNBT);
	void loadFrom(NbtCompound receivedNBT);
	Identifier getID();
}
