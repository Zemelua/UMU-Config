package io.github.zemelua.umu_config.api.config.value.enternal;

import io.github.zemelua.umu_config.api.config.value.IConfigValue;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public interface IKeyBindConfigValue extends IConfigValue<InputUtil.Key> {
	KeyBinding getKeyBinding();
}
