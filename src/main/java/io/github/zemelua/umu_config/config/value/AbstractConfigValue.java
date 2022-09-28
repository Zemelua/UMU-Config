package io.github.zemelua.umu_config.config.value;

import io.github.zemelua.umu_config.config.IConfigValue;
import net.minecraft.util.Identifier;

public abstract class AbstractConfigValue<T> implements IConfigValue<T> {
	protected final Identifier ID;
	protected T value;
	protected final T defaultValue;

	protected AbstractConfigValue(Identifier ID, T defaultValue) {
		this.ID = ID;
		this.defaultValue = defaultValue;

		this.value = this.defaultValue;
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
		return this.ID;
	}
}
