package io.github.zemelua.umu_config.old.api.config.value;

public interface INumberConfigValue<T extends Number> {
	T convert(double value);

	double convert(T value);
}
