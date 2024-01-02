package io.github.zemelua.umu_config.api.client.option;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_config.api.client.option.config.IConfigValue;

import java.util.ArrayList;
import java.util.Collection;

public class OptionContainer implements IOptionContainer {
	private final ImmutableList<IConfigValue<?>> configs;
	private final ImmutableList<IOption<?>> options;

	public OptionContainer(Collection<IOption<?>> options) {
		this.configs = ImmutableList.copyOf(options.stream()
				.filter(o -> o instanceof IConfigValue<?>)
				.map(o -> (IConfigValue<?>) o)
				.toList());
		this.options = ImmutableList.copyOf(options);
	}

	@Override
	public Collection<IConfigValue<?>> getAllConfigs() {
		return new ArrayList<>(this.configs);
	}

	@Override
	public Collection<IOption<?>> getAllOptions() {
		return new ArrayList<>(this.options);
	}
}
