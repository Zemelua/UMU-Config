package io.github.zemelua.umu_config.config.value;

import io.github.zemelua.umu_config.config.IConfigValue;

public abstract class AbstractConfigValue<T> implements IConfigValue<T> {
	protected final String name;
	protected T value;
	protected final T defaultValue;

	protected AbstractConfigValue(String name, T defaultValue) {
		this.name = name;
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
	public String getName() {
		return this.name;
	}
}
