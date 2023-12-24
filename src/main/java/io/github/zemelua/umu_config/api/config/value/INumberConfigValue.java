package io.github.zemelua.umu_config.api.config.value;

public interface INumberConfigValue<T extends Number> {
	T convert(double value);

	double convert(T value);
}
