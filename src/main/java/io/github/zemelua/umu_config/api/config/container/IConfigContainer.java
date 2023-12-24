package io.github.zemelua.umu_config.api.config.container;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.api.config.IConfigElement;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.nio.file.Path;
import java.util.List;

import static net.fabricmc.api.EnvType.*;

public interface IConfigContainer {
	List<IConfigElement> getElements();
	Path getPath();
	void saveTo(JsonObject fileJson);
	void loadFrom(JsonObject fileJson);
	void saveTo(NbtCompound sendNBT);
	void loadFrom(NbtCompound receivedNBT);
	boolean canEdit(PlayerEntity player);
	Identifier getID();

	@Environment(CLIENT)
	default Text getName() {
		return Text.translatable(Util.createTranslationKey("config", this.getID()));
	}
}
