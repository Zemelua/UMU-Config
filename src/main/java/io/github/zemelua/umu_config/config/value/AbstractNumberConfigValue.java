package io.github.zemelua.umu_config.config.value;

import io.github.zemelua.umu_config.client.gui.AbstractConfigEntry;
import io.github.zemelua.umu_config.client.gui.NumberConfigEntry;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;

import java.util.function.Function;

import static net.fabricmc.api.EnvType.*;

public abstract class AbstractNumberConfigValue<T extends Number> extends AbstractConfigValue<T> implements INumberConfigValue<T> {
	protected final T maxValue;
	protected final T minValue;
	protected final Function<T, Text> textGenerator;

	protected AbstractNumberConfigValue(String name, T defaultValue, T maxValue, T minValue, Function<T, Text> textGenerator) {
		super(name, defaultValue);

		this.maxValue = maxValue;
		this.minValue = minValue;
		this.textGenerator = textGenerator;
	}

	@Environment(CLIENT)
	@Override
	public AbstractConfigEntry<T, ? extends IConfigValue<T>> createEntry() {
		return new NumberConfigEntry<>(this);
	}

	@Environment(CLIENT)
	@Override
	public Text getValueText(T value) {
		return this.textGenerator.apply(value);
	}
}
