package io.github.zemelua.umu_config.old.api.config.category;

import io.github.zemelua.umu_config.api.client.gui.AbstractConfigScreen;
import io.github.zemelua.umu_config.old.api.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.old.api.client.gui.entry.ConfigCategoryEntry;
import io.github.zemelua.umu_config.old.api.config.OldIConfigElement;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.util.List;

import static net.fabricmc.api.EnvType.*;

public interface OldIConfigCategory extends OldIConfigElement {
	List<OldIConfigElement> getElements();

	@Environment(CLIENT)
	@Override
	default Text getName() {
		return Text.translatable(Util.createTranslationKey("config.category", this.getID()));
	}

	@Environment(CLIENT)
	@Override
	default AbstractConfigEntry createEntry(AbstractConfigScreen.ValueListWidget parent, int indent, boolean readOnly) {
		return new ConfigCategoryEntry(parent, this, indent, readOnly);
	}
}
