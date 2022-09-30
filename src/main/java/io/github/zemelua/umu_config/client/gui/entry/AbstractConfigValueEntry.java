package io.github.zemelua.umu_config.client.gui.entry;

import io.github.zemelua.umu_config.config.value.IConfigValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConfigValueEntry<T, V extends IConfigValue<T>> extends AbstractConfigEntry {
	protected final V config;
	protected final List<ClickableWidget> clickableWidgets = new ArrayList<>();
	protected final ButtonWidget resetButton;
	protected final boolean readOnly;

	protected T modifyingValue;
	private boolean canEdit = true;

	public AbstractConfigValueEntry(V config, int indent) {
		super(indent);
		this.config = config;
		this.resetButton = new ButtonWidget(0, 0, 50, 20, Text.translatable("controls.reset"), button -> {
			this.modifyingValue = this.config.getDefaultValue();
			this.onReset();
		});
		this.readOnly = MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().player.hasPermissionLevel(2);

		this.modifyingValue = this.config.getValue();
		this.children.add(this.resetButton);
		this.selectableChildren.add(this.resetButton);
		this.clickableWidgets.add(this.resetButton);
	}

	@Override
	public void applyValue() {
		this.config.setValue(this.modifyingValue);
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		textRenderer.draw(matrices, this.config.getName(), x + 10 + this.indent * 12, (float)(y + entryHeight / 2 - textRenderer.fontHeight / 2), 0xFFFFFF);

		this.clickableWidgets.forEach(clickable -> clickable.active = this.canEdit);

		this.resetButton.x = x + entryWidth - 60;
		this.resetButton.y = y + entryHeight / 2 - this.resetButton.getHeight() / 2;
		this.resetButton.active = this.canEdit && !this.modifyingValue.equals(this.config.getDefaultValue());
		this.resetButton.render(matrices, mouseX, mouseY, tickDelta);

		this.renderEditor(matrices, x, y, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
	}

	protected abstract void renderEditor(MatrixStack matrices, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta);

	public void setCanEdit(boolean value) {
		this.canEdit = value;
	}

	@Override
	public Text getTooltip() {
		return this.config.getTooltip();
	}

	protected void onReset() {}
}
