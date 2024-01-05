package io.github.zemelua.umu_config.api.config.value;

import io.github.zemelua.umu_config.api.config.IConfigElement;

public interface IConfigValue<T> extends IConfigElement {
	T getValue();
	void setValue(T value);
	T getDefaultValue();
	boolean isValid(T value);
}
