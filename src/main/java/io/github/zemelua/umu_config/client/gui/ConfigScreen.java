package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.KeybindsScreen;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.annotation.Debug;
import org.jetbrains.annotations.NotNull;

public class ConfigScreen extends Screen {
	@NotNull private ConfigListWidget configListWidget;

	public ConfigScreen(Screen parent) {
		super(Text.literal("efgiry"));

		this.configListWidget = new ConfigListWidget();
	}

	@Override
	protected void init() {
		this.configListWidget = new ConfigListWidget();
		this.addSelectableChild(this.configListWidget);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		this.configListWidget.render(matrices, mouseX, mouseY, delta);
		KeybindsScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);

		super.render(matrices, mouseX, mouseY, delta);
	}

	public class ConfigListWidget extends ElementListWidget<AbstractConfigEntry<?>> {
		public ConfigListWidget() {
			super(ConfigScreen.this.client, ConfigScreen.this.width, ConfigScreen.this.height, 20, ConfigScreen.this.height - 32, 24);

			for (IConfigValue<?> value : UMUConfig.TEST_BASIC_CONFIG.getValues()) {
				this.addEntry(value.createEntry(this));
			}
		}

		@Override
		public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			super.render(matrices, mouseX, mouseY, delta);

			ConfigScreen.this.renderTooltip(matrices, Text.literal("tooltip test"), mouseX, mouseY);
		}

		@Override
		public int getRowWidth() {
			return this.width - 50;
		}

		@Override
		protected int getScrollbarPositionX() {
			return this.getRowRight() + 6;
		}

		@Debug
		public int left() {
			return this.left;
		}

		@Debug
		public int width() {
			return this.width;
		}
	}
}
