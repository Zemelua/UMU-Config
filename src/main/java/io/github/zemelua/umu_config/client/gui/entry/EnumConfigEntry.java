package io.github.zemelua.umu_config.client.gui.entry;

import io.github.zemelua.umu_config.config.value.IConfigValue;
import io.github.zemelua.umu_config.config.value.IEnumConfigValue;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class EnumConfigEntry<T extends Enum<T>, V extends IConfigValue<T> & IEnumConfigValue<T>> extends AbstractConfigValueEntry<T, V> {
	private final ButtonWidget editor;

	public EnumConfigEntry(V config, int indent, boolean readOnly) {
		super(config, indent, readOnly);

		this.editor = new ButtonWidget(0, 0, 0, 20, Text.empty(), (button) -> this.modifyingValue = getNext(this.config.getEnumClass(), this.modifyingValue));
		this.children.add(this.editor);
		this.selectableChildren.add(this.editor);
		this.clickableWidgets.add(this.editor);
	}

	@Override
	protected void renderEditor(MatrixStack matrices, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		this.editor.x = x + entryWidth / 2;
		this.editor.setWidth(x + entryWidth - 65 - this.editor.x);
		this.editor.y = y;
		this.editor.setMessage(this.config.getValueText(this.modifyingValue));
		this.editor.render(matrices, mouseX, mouseY, tickDelta);
	}

	private static <T extends Enum<T>> T getNext(Class<T> clazz, T current) {
		T[] values = clazz.getEnumConstants();
		int index = current.ordinal() + 1;

		if (index >= values.length) {
			return values[0];
		}

		return values[index];
	}
}
