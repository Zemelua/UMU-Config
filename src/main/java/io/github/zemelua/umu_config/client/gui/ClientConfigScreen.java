package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;

public final class ClientConfigScreen extends AbstractConfigScreen {
	public ClientConfigScreen(Screen parent, IConfigContainer config) {
		super(parent, config);
	}

	@Override
	protected void applyValues(ClickableWidget button) {
		this.valueListWidget.getConfigEntries().forEach(AbstractConfigEntry::applyValue);
		ConfigHandler.saveFrom(this.config);
	}
}
