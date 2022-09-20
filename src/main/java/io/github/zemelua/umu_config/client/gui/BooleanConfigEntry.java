package io.github.zemelua.umu_config.client.gui;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

import java.util.List;

public class BooleanConfigEntry extends AbstractConfigEntry<Boolean> {
	private final ClickableWidget editor;

	public BooleanConfigEntry(ConfigScreen.ConfigListWidget parent, IConfigValue<Boolean> config) {
		super(parent, config);

		this.editor = new ButtonWidget(0, 0, 0, 20, Text.empty(), (button) -> this.modifyingValue = !this.modifyingValue);
	}

	@Override
	protected void renderEditor(MatrixStack matrices, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		this.editor.x = x + entryWidth / 2;
		this.editor.setWidth(x + entryWidth - 65 - this.editor.x);
		this.editor.y = y;
		this.editor.setMessage(this.modifyingValue ? ScreenTexts.ON : ScreenTexts.OFF);
		this.editor.render(matrices, mouseX, mouseY, tickDelta);
	}

	@Override
	public List<? extends Element> children() {
		return ImmutableList.<Element>builder().addAll(super.children()).add(this.editor).build();
	}

	@Override
	public List<? extends Selectable> selectableChildren() {
		return ImmutableList.<Selectable>builder().addAll(super.selectableChildren()).add(this.editor).build();
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return super.mouseClicked(mouseX, mouseY, button) || this.editor.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return super.mouseReleased(mouseX, mouseY, button) || this.editor.mouseReleased(mouseX, mouseY, button);
	}
}
