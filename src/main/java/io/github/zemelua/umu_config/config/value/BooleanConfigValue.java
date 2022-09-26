package io.github.zemelua.umu_config.config.value;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.client.gui.entry.BooleanConfigEntry;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

import static net.fabricmc.api.EnvType.*;

public class BooleanConfigValue extends AbstractConfigValue<Boolean> implements IBooleanConfigValue {
	public BooleanConfigValue(String name, Boolean defaultValue) {
		super(name, defaultValue);
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.name, this.value);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.name)) {
			this.value = fileJson.get(this.name).getAsBoolean();
		}
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		sendNBT.putBoolean(this.name, this.value);
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.name)) {
			this.value = receivedNBT.getBoolean(this.name);
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
