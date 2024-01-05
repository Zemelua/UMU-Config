package io.github.zemelua.umu_config.config.value;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.client.gui.AbstractConfigScreen;
import io.github.zemelua.umu_config.old.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.old.gui.entry.EnumConfigEntry;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

import static net.fabricmc.api.EnvType.*;

public class EnumConfigValue<T extends Enum<T>> extends AbstractConfigValue<T> implements IEnumConfigValue<T> {
	private final Class<T> clazz;

	public EnumConfigValue(Identifier ID, T defaultValue) {
		super(ID, defaultValue);

		this.clazz = defaultValue.getDeclaringClass();
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.ID.getPath(), this.value.ordinal());
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.ID.getPath())) {
			int ordinal = fileJson.get(this.ID.getPath()).getAsInt();
			this.value = this.clazz.getEnumConstants()[ordinal];
		}
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		sendNBT.putInt(this.ID.getPath(), this.value.ordinal());
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.ID.getPath())) {
			int ordinal = receivedNBT.getInt(this.ID.getPath());
			this.value = this.clazz.getEnumConstants()[ordinal];
		}
	}

	@Environment(CLIENT)
	@Override
	public AbstractConfigEntry createEntry(AbstractConfigScreen.ValueListWidget parent, int indent, boolean readOnly) {
		return new EnumConfigEntry<>(this, indent, readOnly);
	}

	@Environment(CLIENT)
	@Override
	public Text getValueText(T value) {
		return Text.translatable(Util.createTranslationKey("config.enum", this.ID) + "." + value.ordinal());
	}

	@Override
	public Class<T> getEnumClass() {
		return this.clazz;
	}

	public static class Builder<E extends Enum<E>> {
		private final Identifier ID;
		@NotNull private E defaultValue;

		public Builder(Identifier ID, Class<E> clazz) {
			this.ID = ID;
			this.defaultValue = clazz.getEnumConstants()[0];
		}

		public Builder<E> defaultValue(E value) {
			this.defaultValue = value;

			return this;
		}

		public EnumConfigValue<E> build() {
			return new EnumConfigValue<>(this.ID, this.defaultValue);
		}
	}
}
