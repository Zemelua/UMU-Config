package io.github.zemelua.umu_config.api.client.gui;

import io.github.zemelua.umu_config.api.client.gui.entry.AbstractConfigEntry;
import net.fabricmc.api.Environment;

import static net.fabricmc.api.EnvType.CLIENT;

@FunctionalInterface
public interface ConfigEntryFactory {
	@Environment(CLIENT) AbstractConfigEntry createEntry(AbstractConfigScreen.ValueListWidget parent, int indent, boolean readOnly);
}
