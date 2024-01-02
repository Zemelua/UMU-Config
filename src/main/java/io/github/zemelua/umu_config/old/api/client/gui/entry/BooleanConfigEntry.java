package io.github.zemelua.umu_config.old.api.client.gui.entry;

import io.github.zemelua.umu_config.old.api.config.value.IBooleanConfigValue;
import io.github.zemelua.umu_config.old.api.config.value.OldOldIConfigValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BooleanConfigEntry<V extends OldOldIConfigValue<Boolean> & IBooleanConfigValue> extends AbstractConfigValueEntry<Boolean, V> {
	private final ButtonWidget editor;

	public BooleanConfigEntry(V config, int indent, boolean readOnly) {
		super(config, indent, readOnly);

		this.editor = new ButtonWidget.Builder(Text.empty(), button -> this.modifyingValue = !this.modifyingValue)
				.dimensions(0, 0, 0, 20)
				.build();
		this.children.add(this.editor);
		this.selectableChildren.add(this.editor);
		this.clickableWidgets.add(this.editor);
	}

	@Override
	protected void renderEditor(DrawContext context, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		this.editor.setX(x + entryWidth / 2);
		this.editor.setWidth(x + entryWidth - 65 - this.editor.getX());
		this.editor.setY(y + entryHeight / 2 - this.editor.getHeight() / 2);
		this.editor.setMessage(this.config.getValueText(BooleanConfigEntry.this.modifyingValue));
		this.editor.render(context, mouseX, mouseY, tickDelta);
	}
}
