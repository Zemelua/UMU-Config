package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.client.ModClientConfigs;
import io.github.zemelua.umu_config.old.api.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import io.github.zemelua.umu_config.util.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Objects;

public class CommonConfigScreen extends AbstractConfigScreen {
	private static final Text APPLY_MULTIPLAY_TOOLTIP = Text.translatable("gui.tooltip.apply_multiplay").formatted(Formatting.YELLOW);
	private static final Text NOT_HAVE_PERMISSION_TOOLTIP = Text.translatable("gui.tooltip.not_have_permission").formatted(Formatting.YELLOW);

	public CommonConfigScreen(Screen parent, IConfigContainer config) {
		super(parent, config, ModUtils.isInMultiplayServer() && !config.canEdit(MinecraftClient.getInstance().player)
				? config.getName().copy().append(" (").append(Text.translatable("gui.read_only")).append(")")
				: config.getName());
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		if (ModUtils.isInMultiplayServer() && this.applyButton.isHovered()) {
			List<OrderedText> tooltip = this.readOnly
					? this.textRenderer.wrapLines(NOT_HAVE_PERMISSION_TOOLTIP, 200)
					: this.textRenderer.wrapLines(APPLY_MULTIPLAY_TOOLTIP, 200);
			context.drawOrderedTooltip(this.textRenderer, tooltip, mouseX, mouseY);
		}
	}

	@Override
	protected ClickableWidget createApplyButton() {
		int x = ModClientConfigs.reverseApplyButtons() ? this.width / 2 - 155 : this.width / 2 + 5;
		Text message = Text.translatable("gui.ok");
		if (ModUtils.isInMultiplayServer()) {
			message = this.readOnly ? Text.translatable("gui.read_only") : Text.translatable("gui.send_to_server");
		}

		ClickableWidget widget = new ButtonWidget.Builder(message, button -> {
			this.applyValues(button);
			MinecraftClient.getInstance().setScreen(this.parent);
		}).dimensions(x, this.height - 29, 150, 20).build();
		widget.active = !CommonConfigScreen.this.readOnly;

		return widget;
	}

	@Override
	protected void applyValues(ClickableWidget button) {
		this.valueListWidget.getConfigEntries().forEach(AbstractConfigEntry::applyValue);

		if (Objects.requireNonNull(this.client).world == null) {
			ConfigFileManager.saveFrom(this.config);
		} else {
			ConfigManager.sendToServer(this.config);
		}
	}
}
