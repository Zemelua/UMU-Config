package io.github.zemelua.umu_config.api.client.gui;

import io.github.zemelua.umu_config.api.config.ConfigFileManager;
import io.github.zemelua.umu_config.api.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;

public class ClientConfigScreen extends AbstractConfigScreen {
	public ClientConfigScreen(Screen parent, IConfigContainer config) {
		super(parent, config, config.getName());
	}

	@Override
	protected void applyValues(ClickableWidget button) {
		this.valueListWidget.getConfigEntries().forEach(AbstractConfigEntry::applyValue);
		ConfigFileManager.saveFrom(this.config);

	}
}
