package io.github.zemelua.umu_config.old.gui.entry;

import io.github.zemelua.umu_config.config.value.IConfigValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConfigValueEntry<T, V extends IConfigValue<T>> extends AbstractConfigEntry {
	protected final V config;
	protected final List<ClickableWidget> clickableWidgets = new ArrayList<>();
	protected final ButtonWidget resetButton;
	protected final boolean readOnly;

	protected T modifyingValue;

	public AbstractConfigValueEntry(V config, int indent, boolean readOnly) {
		super(indent);
		this.config = config;
		this.resetButton = new ButtonWidget.Builder(Text.translatable("controls.reset"), button -> {
			this.modifyingValue = this.config.getDefaultValue();
			this.onReset();
		}).dimensions(0, 0, 50, 20).build();
		this.readOnly = readOnly;

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
	public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		context.drawText(textRenderer, this.config.getName(), x + 10 + this.indent * 12, y + entryHeight / 2 - textRenderer.fontHeight / 2, 0xFFFFFF, false);
		context.drawText(textRenderer, this.config.getName(), x + 10 + this.indent * 12, y + entryHeight / 2 - textRenderer.fontHeight / 2, 0xFFFFFF, false);

		this.clickableWidgets.forEach(clickable -> clickable.active = !this.readOnly);

		this.resetButton.method_46421(x + entryWidth - 60);
		this.resetButton.method_46419(y + entryHeight / 2 - this.resetButton.getHeight() / 2);
		this.resetButton.active = !this.readOnly && !this.modifyingValue.equals(this.config.getDefaultValue());
		this.resetButton.render(context, mouseX, mouseY, tickDelta);

		this.renderEditor(context, x, y, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
	}

	protected abstract void renderEditor(DrawContext context, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta);

	@Override
	public Text getTooltip() {
		return this.config.getTooltip();
	}

	protected void onReset() {}
}
