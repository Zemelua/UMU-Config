package io.github.zemelua.umu_config.config.value;

import io.github.zemelua.umu_config.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.client.gui.entry.NumberConfigEntry;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static net.fabricmc.api.EnvType.*;

public abstract class AbstractNumberConfigValue<T extends Number> extends AbstractConfigValue<T> implements INumberConfigValue<T> {
	protected final T maxValue;
	protected final T minValue;
	protected final Function<T, Text> textGenerator;

	protected AbstractNumberConfigValue(Identifier ID, T defaultValue, T maxValue, T minValue, Function<T, Text> textGenerator) {
		super(ID, defaultValue);

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
