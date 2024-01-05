package io.github.zemelua.umu_config.api.config.value;

public interface IEnumConfigValue<T extends Enum<T>> extends IConfigValue<T> {
	Class<T> getEnumClass();
}
