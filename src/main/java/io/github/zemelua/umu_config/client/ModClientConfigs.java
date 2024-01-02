package io.github.zemelua.umu_config.client;

import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.api.config.IConfigProvider;
import io.github.zemelua.umu_config.api.config.container.ConfigContainer;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import io.github.zemelua.umu_config.api.config.value.BooleanConfigValue;
import io.github.zemelua.umu_config.api.config.value.EnumConfigValue;
import io.github.zemelua.umu_config.api.config.value.IConfigValue;
import io.github.zemelua.umu_config.api.config.value.IntegerConfigValue;
import io.github.zemelua.umu_config.api.config.value.enternal.IKeyBindConfigValue;
import io.github.zemelua.umu_config.api.config.value.enternal.KeyBindConfigValue;
import io.github.zemelua.umu_config.api.config.value.reference.ResourcePackConfigValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.List;

public class ModClientConfigs implements IConfigProvider {
	private static final IConfigValue<Boolean> REVERSE_APPLY_BUTTONS;
	private static final IConfigValue<Integer> ENTRY_SPACING;
	private static final IConfigValue<EnumEntryBar> ENUM_ENTRY_BAR;
	private static final IConfigValue<Boolean> RESOURCE_PACK_TEST;
	private static final IKeyBindConfigValue KEY_BIND;
	private static final IConfigContainer CLIENT_CONFIG;

	@Override
	public List<IConfigContainer> getConfigs() {
		return List.of(CLIENT_CONFIG);
	}

	public static boolean reverseApplyButtons() {
		return REVERSE_APPLY_BUTTONS.getValue();
	}

	public static int getEntrySpacing() {
		return 20 + ENTRY_SPACING.getValue();
	}

	public static boolean drawEnumEntryBar(ClickableWidget editor) {
		return switch (ENUM_ENTRY_BAR.getValue()) {
			case OFF -> false;
			case CONSTANTLY -> true;
			case ON_HOVERED -> editor.isHovered();
		};
	}

	static {
		REVERSE_APPLY_BUTTONS = new BooleanConfigValue.Builder(UMUConfig.identifier("reverse_apply_buttons"))
				.defaultValue(false)
				.build();
		ENTRY_SPACING = new IntegerConfigValue.Builder(UMUConfig.identifier("entry_spacing"))
				.minValue(0)
				.maxValue(9)
				.defaultValue(4)
				.build();
		ENUM_ENTRY_BAR = new EnumConfigValue.Builder<>(UMUConfig.identifier("enum_entry_bar"), EnumEntryBar.class)
				.defaultValue(EnumEntryBar.OFF)
				.build();
		RESOURCE_PACK_TEST = new ResourcePackConfigValue(UMUConfig.identifier("resource_pack_test"), UMUConfig.identifier("test").toString());
		KEY_BIND = new KeyBindConfigValue(UMUConfig.identifier("key"), () -> MinecraftClient.getInstance().options.dropKey);
		CLIENT_CONFIG = new ConfigContainer.Builder(UMUConfig.identifier("umu_config_client"))
				.addValue(REVERSE_APPLY_BUTTONS)
				.addValue(ENTRY_SPACING)
				.addValue(ENUM_ENTRY_BAR)
				.addValue(RESOURCE_PACK_TEST)
				.addValue(KEY_BIND)
				.build();
	}

	public enum EnumEntryBar {
		CONSTANTLY,
		ON_HOVERED,
		OFF
	}

	@Deprecated public ModClientConfigs() {}
}
