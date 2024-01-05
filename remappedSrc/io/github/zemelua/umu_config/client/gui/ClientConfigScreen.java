package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.old.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
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
