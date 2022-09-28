package io.github.zemelua.umu_config.config.value;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.client.gui.entry.BooleanConfigEntry;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.fabricmc.api.EnvType.*;

public class BooleanConfigValue extends AbstractConfigValue<Boolean> implements IBooleanConfigValue {
	public BooleanConfigValue(Identifier ID, Boolean defaultValue) {
		super(ID, defaultValue);
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.ID.getPath(), this.value);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.ID.getPath())) {
			this.value = fileJson.get(this.ID.getPath()).getAsBoolean();
		}
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		sendNBT.putBoolean(this.ID.getPath(), this.value);
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.ID.getPath())) {
			this.value = receivedNBT.getBoolean(this.ID.getPath());
		}
	}

	@Environment(CLIENT)
	@Override
	public AbstractConfigEntry<Boolean, ? extends IConfigValue<Boolean>> createEntry() {
		return new BooleanConfigEntry<>(this);
	}

	@Environment(CLIENT)
	@Override
	public Text getValueText(Boolean value) {
		return null;
	}
}
