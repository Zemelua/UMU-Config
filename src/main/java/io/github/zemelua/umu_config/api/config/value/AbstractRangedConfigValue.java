package io.github.zemelua.umu_config.api.config.value;

import net.minecraft.util.Identifier;

public abstract class AbstractRangedConfigValue<T extends Number> extends AbstractConfigValue<T> implements IRangedConfigValue<T> {
	protected final T minValue;
	protected final T maxValue;

	public AbstractRangedConfigValue(Identifier id, T defaultValue, T minValue, T maxValue) {
		super(id, defaultValue);

		this.minValue = minValue;
		this.maxValue = maxValue;

		this.check();
	}

	@Override
	public T getMinValue() {
		return this.minValue;
	}

	@Override
	public T getMaxValue() {
		return this.maxValue;
	}

	@Override
	public boolean isValid(T value) {
		if (this.minValue.doubleValue() > this.maxValue.doubleValue()) {
			return false;
		}

		if (this.value.doubleValue() > this.maxValue.doubleValue()) {
			return false;
		}

		return !(this.value.doubleValue() < this.minValue.doubleValue());
	}
}
