package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import io.github.zemelua.umu_config.util.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Objects;

public final class CommonConfigScreen extends AbstractConfigScreen {
	private static final Text APPLY_MULTIPLAY_TOOLTIP = Text.translatable("gui.config.apply_multiplay.tooltip").formatted(Formatting.YELLOW);

	public CommonConfigScreen(Screen parent, IConfigContainer config) {
		super(parent, config);

		this.applyButton.active = MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().player.hasPermissionLevel(2);
	}

	@Override
	protected void init() {
		super.init();

		this.applyButton.active = MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().player.hasPermissionLevel(2);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		if (ModUtils.isInMultiplayServer() && this.applyButton.isHovered()) {
			List<OrderedText> tooltip = this.textRenderer.wrapLines(APPLY_MULTIPLAY_TOOLTIP, 200);
			this.renderOrderedTooltip(matrices, tooltip, mouseX, mouseY);
		}
	}

	@Override
	protected ClickableWidget createApplyButton() {
		return new ButtonWidget(this.width / 2 + 5, this.height - 29, 150, 20, Text.translatable("gui.send_to_server"), button -> {
			this.applyValues(button);
			MinecraftClient.getInstance().setScreen(this.parent);
		});
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
