package io.github.zemelua.umu_config.api.config.value.enternal;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.api.client.gui.AbstractConfigScreen;
import io.github.zemelua.umu_config.api.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.api.client.gui.entry.KeyBindConfigEntryOld;
import io.github.zemelua.umu_config.mixin.AccessorKeyBinding;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class KeyBindConfigValue implements IKeyBindConfigValue {
	private final Identifier ID;
	private final Supplier<KeyBinding> keyBinding;

	public KeyBindConfigValue(Identifier ID, Supplier<KeyBinding> keyBinding) {
		this.ID = ID;
		this.keyBinding = keyBinding;
	}

	@Override
	public InputUtil.Key getValue() {
		return ((AccessorKeyBinding) this.getKeyBinding()).getBoundKey();
	}

	@Override
	public void setValue(InputUtil.Key value) {
		this.getKeyBinding().setBoundKey(value);
	}

	@Override
	public InputUtil.Key getDefaultValue() {
		return this.getKeyBinding().getDefaultKey();
	}

	@Override
	public KeyBinding getKeyBinding() {
		return this.keyBinding.get();
	}

	@Override
	public Identifier getID() {
		return this.ID;
	}

	@Override
	public AbstractConfigEntry createEntry(AbstractConfigScreen.ValueListWidget parent, int indent, boolean readOnly) {
		return new KeyBindConfigEntryOld<>(this, indent, readOnly);
	}

	@Override
	public Text getValueText(InputUtil.Key value) {
		return value.getLocalizedText();
	}

	@Override public void saveTo(JsonObject fileJson) {}
	@Override public void loadFrom(JsonObject fileJson) {}
	@Override public void saveTo(NbtCompound sendNBT) {}
	@Override public void loadFrom(NbtCompound receivedNBT) {}
}
