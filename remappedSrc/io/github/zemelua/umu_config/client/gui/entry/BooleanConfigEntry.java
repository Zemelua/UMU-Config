package io.github.zemelua.umu_config.client.gui.entry;

import io.github.zemelua.umu_config.config.value.IBooleanConfigValue;
import io.github.zemelua.umu_config.config.value.IConfigValue;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class BooleanConfigEntry<V extends IConfigValue<Boolean> & IBooleanConfigValue> extends AbstractConfigValueEntry<Boolean, V> {
	private final ButtonWidget editor;

	public BooleanConfigEntry(V config, int indent, boolean readOnly) {
		super(config, indent, readOnly);

		this.editor = new ButtonWidget(0, 0, 0, 20, Text.empty(), (button) -> this.modifyingValue = !this.modifyingValue);
		this.children.add(this.editor);
		this.selectableChildren.add(this.editor);
		this.clickableWidgets.add(this.editor);
	}

	@Override
	protected void renderEditor(MatrixStack matrices, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		this.editor.x = x + entryWidth / 2;
		this.editor.setWidth(x + entryWidth - 65 - this.editor.x);
		this.editor.y = y + entryHeight / 2 - this.editor.getHeight() / 2;
		this.editor.setMessage(this.config.getValueText(BooleanConfigEntry.this.modifyingValue));
		this.editor.render(matrices, mouseX, mouseY, tickDelta);
	}
}
