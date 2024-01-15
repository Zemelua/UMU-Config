package io.github.zemelua.umu_config.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.zemelua.umu_config.ModConfigs;
import io.github.zemelua.umu_config.client.screen.ConfigSelectScreen;

public class ModMenuImpl implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> new ConfigSelectScreen(ModClientConfigs.asScreen(parent), ModConfigs.asScreen(parent));
	}
}
