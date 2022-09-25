package io.github.zemelua.umu_config.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.config.ConfigManager;

public class ModMenuImpl implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> ConfigManager.openConfigScreen(parent, UMUConfig.MOD_ID).orElse(null);
	}
}
