package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.Objects;

public final class CommonConfigScreen extends AbstractConfigScreen {
	public CommonConfigScreen(Screen parent, IConfigContainer config) {
		super(parent, config);
	}

	@Override
	protected void applyValues(ClickableWidget button) {
		this.valueListWidget.getConfigEntries().forEach(AbstractConfigEntry::applyValue);

		if (Objects.requireNonNull(this.client).world == null) {
			ConfigHandler.saveFrom(this.config);
		} else {
			ConfigManager.sendToServer(this.config);
		}
	}
}
