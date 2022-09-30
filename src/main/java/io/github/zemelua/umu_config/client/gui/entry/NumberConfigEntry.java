package io.github.zemelua.umu_config.client.gui.entry;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_config.config.value.IConfigValue;
import io.github.zemelua.umu_config.config.value.INumberConfigValue;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.List;

public class NumberConfigEntry<T extends Number, V extends IConfigValue<T> & INumberConfigValue<T>> extends AbstractConfigValueEntry<T, V> {
	private final SliderEditor editor = new SliderEditor(20);

	public NumberConfigEntry(V config, int indent) {
		super(config, indent);

		this.editor.setValue(this.modifyingValue);
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
	public List<? extends Element> children() {
		return ImmutableList.<Element>builder()
				.addAll(super.children())
				.add(this.editor)
				.build();
	}

	@Override
	public List<? extends Selectable> selectableChildren() {
		return ImmutableList.<Selectable>builder()
				.addAll(super.selectableChildren())
				.add(this.editor)
				.build();
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return super.mouseClicked(mouseX, mouseY, button) || this.editor.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return super.mouseReleased(mouseX, mouseY, button) || this.editor.mouseReleased(mouseX, mouseY, button);
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
