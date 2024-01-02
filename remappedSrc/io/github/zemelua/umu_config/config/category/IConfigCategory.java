package io.github.zemelua.umu_config.config.category;

import io.github.zemelua.umu_config.client.gui.AbstractConfigScreen;
import io.github.zemelua.umu_config.old.api.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.old.api.client.gui.entry.ConfigCategoryEntry;
import io.github.zemelua.umu_config.config.IConfigElement;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.util.List;

import static net.fabricmc.api.EnvType.*;

public interface IConfigCategory extends IConfigElement {
	List<IConfigElement> getElements();

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
