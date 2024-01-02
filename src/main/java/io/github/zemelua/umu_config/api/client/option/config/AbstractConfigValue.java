package io.github.zemelua.umu_config.api.client.option.config;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public abstract class AbstractConfigValue<T> implements IConfigValue<T> {
	private final Identifier id;
	private final T defaultValue;
	private T value;

	public AbstractConfigValue(Identifier id, T defaultValue) {
		this.id = id;
		this.defaultValue = defaultValue;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public T getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public Identifier getID() {
		return this.id;
	}

	@Override
	public boolean isEnable() {
		return true;
	}

	@Override
	public Text getName() {
		return Text.translatable(Util.createTranslationKey("config.value", this.getID()));
	}

	@Override
	public Text getDescription() {
		return Text.translatable(Util.createTranslationKey("config.value.tooltip", this.getID()));
	}

	@Override
	public Text valueFormat(T value) {
		return Text.literal(value.toString());
	}
}
