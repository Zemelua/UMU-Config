package io.github.zemelua.umu_config.api.config.value;

import io.github.zemelua.umu_config.api.config.IConfigElement;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import static net.fabricmc.api.EnvType.*;

public interface IConfigValue<T> extends IConfigElement {
	T getValue();
	void setValue(T value);
	T getDefaultValue();

	@Environment(CLIENT) Text getValueText(T value);

	@Environment(CLIENT)
	@Override
	default Text getName() {
		return Text.translatable(Util.createTranslationKey("config.value", this.getID()));
	}

	@Environment(CLIENT)
	default Text getTooltip() {
		return Text.translatable(Util.createTranslationKey("config.value.tooltip", this.getID()));
	}
}
