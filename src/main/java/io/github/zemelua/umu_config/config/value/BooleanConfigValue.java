package io.github.zemelua.umu_config.config.value;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.client.gui.AbstractConfigEntry;
import io.github.zemelua.umu_config.client.gui.BooleanConfigEntry;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.fabricmc.api.Environment;
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
