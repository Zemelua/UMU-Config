package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.config.IConfigValue;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.KeybindsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.annotation.Debug;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConfigScreen extends Screen {
	private final Screen parent;
	private final IConfigContainer config;

	@NotNull private ConfigListWidget configListWidget;
	@NotNull private ClickableWidget cancelButton;
	@NotNull private ClickableWidget applyButton;

	public ConfigScreen(Screen parent, IConfigContainer config) {
		super(Text.translatable("umu_config." + config.getName() + "title"));
		this.parent = parent;
		this.config = config;

		this.configListWidget = new ConfigListWidget();
		this.cancelButton = new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, ScreenTexts.CANCEL, button
				-> MinecraftClient.getInstance().setScreen(parent));
		this.applyButton = new ButtonWidget(this.width / 2 + 5, this.height - 29, 150, 20, Text.translatable("gui.ok"), button
				-> this.configListWidget.configEntries.forEach(configEntry -> {
			configEntry.applyValue();
			ConfigHandler.saveFrom(ConfigScreen.this.config);
		}));
	}

	@Override
	protected void init() {
		this.configListWidget = new ConfigListWidget();
		this.cancelButton = new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, ScreenTexts.CANCEL, button
				-> MinecraftClient.getInstance().setScreen(this.parent));
		this.applyButton = new ButtonWidget(this.width / 2 + 5, this.height - 29, 150, 20, Text.translatable("gui.ok"), button
				-> this.configListWidget.configEntries.forEach(configEntry -> {
			configEntry.applyValue();
			ConfigHandler.saveFrom(ConfigScreen.this.config);
			MinecraftClient.getInstance().setScreen(this.parent);
		}));

		this.addDrawableChild(this.configListWidget);
		this.addDrawableChild(this.cancelButton);
		this.addDrawableChild(this.applyButton);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);
		this.configListWidget.render(matrices, mouseX, mouseY, delta);
		KeybindsScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);

		super.render(matrices, mouseX, mouseY, delta);
	}

	public class ConfigListWidget extends ElementListWidget<AbstractConfigEntry<?, ?>> {
		private final List<AbstractConfigEntry<?, ?>> configEntries = new ArrayList<>();

		public ConfigListWidget() {
			super(ConfigScreen.this.client, ConfigScreen.this.width, ConfigScreen.this.height, 20, ConfigScreen.this.height - 32, 24);

			for (IConfigValue<?> value : ConfigScreen.this.config.getValues()) {
				AbstractConfigEntry<?, ?> entry = value.createEntry();
				this.addEntry(entry);
				this.configEntries.add(entry);
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
