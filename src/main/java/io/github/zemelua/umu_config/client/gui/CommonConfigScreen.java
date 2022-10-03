package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.client.ModClientConfigs;
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
	private static final Text APPLY_MULTIPLAY_TOOLTIP = Text.translatable("gui.apply_multiplay").formatted(Formatting.YELLOW);
	private static final Text HAVE_NOT_PERMISSION_TOOLTIP = Text.translatable("gui.have_not_permission").formatted(Formatting.YELLOW);

	public CommonConfigScreen(Screen parent, IConfigContainer config) {
		super(parent, config);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);

		if (ModUtils.isInMultiplayServer() && this.applyButton.isHovered()) {
			List<OrderedText> tooltip;
			if (this.readOnly) {
				tooltip = this.textRenderer.wrapLines(HAVE_NOT_PERMISSION_TOOLTIP, 200);
			} else {
				tooltip = this.textRenderer.wrapLines(APPLY_MULTIPLAY_TOOLTIP, 200);
			}
			this.renderOrderedTooltip(matrices, tooltip, mouseX, mouseY);
		}
	}

	@Override
	protected ClickableWidget createApplyButton() {
		int x = ModClientConfigs.reverseApplyButtons() ? this.width / 2 - 155 : this.width / 2 + 5;
		Text message = this.readOnly ? Text.translatable("gui.read_only") : Text.translatable("gui.send_to_server");

		return new ButtonWidget(x, this.height - 29, 150, 20, message, button -> {
			this.applyValues(button);
			MinecraftClient.getInstance().setScreen(this.parent);
		}) {{
			this.active = !CommonConfigScreen.this.readOnly;
		}};
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
