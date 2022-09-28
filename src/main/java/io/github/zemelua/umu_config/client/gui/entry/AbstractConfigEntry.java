package io.github.zemelua.umu_config.client.gui.entry;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget.Entry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConfigEntry<T, V extends IConfigValue<T>> extends Entry<AbstractConfigEntry<?, ?>> {
	protected final V config;
	protected final ButtonWidget resetButton;
	protected final List<ClickableWidget> clickableWidgets = new ArrayList<>();

	protected T modifyingValue;
	private boolean canEdit = true;

	public AbstractConfigEntry(V config) {
		this.config = config;
		this.resetButton = new ButtonWidget(0, 0, 50, 20, Text.translatable("controls.reset"), button -> {
			this.modifyingValue = this.config.getDefaultValue();
			this.onReset();
		});

		this.modifyingValue = this.config.getValue();
		this.clickableWidgets.add(this.resetButton);
	}

	public void applyValue() {
		this.config.setValue(this.modifyingValue);
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

		this.clickableWidgets.forEach(clickable -> clickable.active = this.canEdit);

		if (hovered) {
			DrawableHelper.fill(matrices, x, y, x + entryWidth, y + entryHeight, 0x24FFFFFF);
		}

		textRenderer.draw(matrices, this.config.getName(), x + 10, (float)(y + entryHeight / 2 - textRenderer.fontHeight / 2), 0xFFFFFF);

		this.resetButton.x = x + entryWidth - 60;
		this.resetButton.y = y + entryHeight / 2 - this.resetButton.getHeight() / 2;
		this.resetButton.active = this.canEdit && this.modifyingValue != this.config.getDefaultValue();
		this.resetButton.render(matrices, mouseX, mouseY, tickDelta);

		this.renderEditor(matrices, x, y, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
	}

	protected abstract void renderEditor(MatrixStack matrices, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta);

	public void setCanEdit(boolean value) {
		this.canEdit = value;
	}

	public Text getTooltip() {
		return this.config.getTooltip();
	}

	@Override
	public List<? extends Element> children() {
		return ImmutableList.of(this.resetButton);
	}

	@Override
	public List<? extends Selectable> selectableChildren() {
		return ImmutableList.of(this.resetButton);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return this.resetButton.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return this.resetButton.mouseReleased(mouseX, mouseY, button);
	}

	protected void onReset() {}
}
