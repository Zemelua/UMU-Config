package io.github.zemelua.umu_config.api.config.value;

import io.github.zemelua.umu_config.UMUConfig;
import net.minecraft.util.Identifier;

public abstract class AbstractConfigValue<T> implements IConfigValue<T> {
	protected final Identifier id;
	protected final T defaultValue;

	protected T value;

	public AbstractConfigValue(Identifier id, T defaultValue) {
		this.id = id;
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

		this.check();
	}

	@Override
	public T getDefaultValue() {
		return this.defaultValue;
	}

	@Override
	public boolean isValid(T value) {
		return true;
	}

	public final void check() {
		if (!this.isValid(this.value)) {
			UMUConfig.LOGGER.warn("The value is not valid!");
		}
	}

	@Override
	public Identifier getID() {
		return this.id;
	}

	protected String getKey() {
		return this.id.getPath();
	}
}
