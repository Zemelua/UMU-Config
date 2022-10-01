package io.github.zemelua.umu_config.config.value;

public interface IEnumConfigValue<T extends Enum<T>> {
	Class<T> getEnumClass();
}
