package io.github.zemelua.umu_config.old.api.config.value;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.api.client.gui.AbstractConfigScreen;
import io.github.zemelua.umu_config.old.api.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.old.api.client.gui.entry.BooleanConfigEntry;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.fabricmc.api.EnvType.*;

public class BooleanConfigValueOldOld extends AbstractConfigValueOldOld<Boolean> implements IBooleanConfigValue {
	public BooleanConfigValueOldOld(Identifier ID, Boolean defaultValue) {
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
	public AbstractConfigEntry createEntry(AbstractConfigScreen.ValueListWidget parent, int indent, boolean readOnly) {
		return new BooleanConfigEntry<>(this, indent, readOnly);
	}

	@Environment(CLIENT)
	@Override
	public Text getValueText(Boolean value) {
		return value ? ScreenTexts.ON : ScreenTexts.OFF;
	}

	public static class Builder {
		private final Identifier ID;
		private boolean defaultValue = true;

		public Builder(Identifier ID) {
			this.ID = ID;
		}

		public Builder defaultValue(boolean value) {
			this.defaultValue = value;

			return this;
		}

		public BooleanConfigValueOldOld build() {
			return new BooleanConfigValueOldOld(this.ID, this.defaultValue);
		}
	}
}
