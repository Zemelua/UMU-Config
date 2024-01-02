package io.github.zemelua.umu_config.old.api.config.value.enternal;

import io.github.zemelua.umu_config.old.api.config.value.OldOldIConfigValue;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public interface OldOldIKeyBindConfigValue extends OldOldIConfigValue<InputUtil.Key> {
	KeyBinding getKeyBinding();
}
