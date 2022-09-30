package io.github.zemelua.umu_config.client.gui.entry;

import io.github.zemelua.umu_config.config.value.IConfigValue;
import io.github.zemelua.umu_config.config.value.INumberConfigValue;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class NumberConfigEntry<T extends Number, V extends IConfigValue<T> & INumberConfigValue<T>> extends AbstractConfigValueEntry<T, V> {
	private final SliderEditor editor = this.new SliderEditor(20);

	public NumberConfigEntry(V config, int indent, boolean readOnly) {
		super(config, indent, readOnly);

		this.editor.setValue(this.modifyingValue);
		this.children.add(this.editor);
		this.selectableChildren.add(this.editor);
		this.clickableWidgets.add(this.editor);
	}

	@Override
	protected void renderEditor(MatrixStack matrices, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		this.editor.x = x + entryWidth / 2;
		this.editor.setWidth(x + entryWidth - 65 - this.editor.x);
		this.editor.y = y;
		this.editor.applyValue();
		this.editor.updateMessage();
		this.editor.render(matrices, mouseX, mouseY, tickDelta);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY) || this.editor.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	protected void onReset() {
		this.editor.setValue(this.modifyingValue);
	}

	private class SliderEditor extends SliderWidget {
		public SliderEditor(int height) {
			super(0, 0, 0, height, Text.empty(), 0.0D);
		}

		@Override
		protected void updateMessage() {
			this.setMessage(NumberConfigEntry.this.config.getValueText(this.getConvertedValue()));
		}

		@Override
		protected void applyValue() {
			NumberConfigEntry.this.modifyingValue = this.getConvertedValue();
		}

		private void setValue(T value) {
			this.value = MathHelper.clamp(NumberConfigEntry.this.config.convert(value), 0.0D, 1.0D);
			this.updateMessage();
		}

		private T getConvertedValue() {
			return NumberConfigEntry.this.config.convert(this.value);
		}
	}
}
