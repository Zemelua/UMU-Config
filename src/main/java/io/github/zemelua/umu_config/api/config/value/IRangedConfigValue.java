package io.github.zemelua.umu_config.api.config.value;

public interface IRangedConfigValue<T extends Number> extends IConfigValue<T> {
	T getMinValue();
	T getMaxValue();
}
