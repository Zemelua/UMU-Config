package io.github.zemelua.umu_config.api.client.option;

import io.github.zemelua.umu_config.old.api.client.gui.OptionEntry;
import net.minecraft.text.Text;

public interface IOption<T> {
	T getValue();
	void setValue(T value);
	T getDefaultValue();
	boolean isEnable();
	Text getName();
	Text getDescription();
	Text valueFormat(T value);
	OptionEntry<T, ? extends IOption<T>> createEntry();

	default void reset() {
		this.setValue(this.getDefaultValue());
	}
}
