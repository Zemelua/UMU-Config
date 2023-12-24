package io.github.zemelua.umu_config.api.config;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.api.client.gui.AbstractConfigScreen;
import io.github.zemelua.umu_config.api.client.gui.entry.AbstractConfigEntry;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.fabricmc.api.EnvType.*;

public interface IConfigElement {
	void saveTo(JsonObject fileJson);
	void loadFrom(JsonObject fileJson);
	void saveTo(NbtCompound sendNBT);
	void loadFrom(NbtCompound receivedNBT);
	Identifier getID();

	@Environment(CLIENT) Text getName();

	@Environment(CLIENT) AbstractConfigEntry createEntry(AbstractConfigScreen.ValueListWidget parent, int indent, boolean readOnly);
}
