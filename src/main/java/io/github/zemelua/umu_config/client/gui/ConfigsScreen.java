package io.github.zemelua.umu_config.client.gui;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import io.github.zemelua.umu_config.util.ModUtils;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.fabricmc.api.EnvType.*;

public class ConfigsScreen extends Screen {
	private final Screen parent;
	private final ImmutableList<IConfigContainer> configs;
	private final ImmutableList<IConfigContainer> clientConfigs;

	@NotNull private ConfigListWidget configListWidget;
	@NotNull private ClickableWidget backButton;

	public ConfigsScreen(Screen parent, String modID) {
		this(parent, Text.of(ModUtils.getModName(modID)), ConfigManager.byModID(modID), ConfigManager.byModIDClient(modID));
	}

	public ConfigsScreen(Screen parent, Text title, List<IConfigContainer> configs, List<IConfigContainer> clientConfigs) {
		super(title);

		this.parent = parent;
		this.configs = ImmutableList.copyOf(configs);
		this.clientConfigs = ImmutableList.copyOf(clientConfigs);
		this.configListWidget = this.new ConfigListWidget();
		this.backButton = new ButtonWidget(this.width / 2 - 75, this.height - 29, 150, 20, ScreenTexts.BACK, button
				-> MinecraftClient.getInstance().setScreen(this.parent));

		this.addDrawableChild(this.configListWidget);
		this.addDrawableChild(this.backButton);
	}

	@Override
	protected void init() {
		this.configListWidget = this.new ConfigListWidget();
		this.backButton = new ButtonWidget(this.width / 2 - 75, this.height - 29, 150, 20, ScreenTexts.BACK, button
				-> MinecraftClient.getInstance().setScreen(this.parent));

		this.addDrawableChild(this.configListWidget);
		this.addDrawableChild(this.backButton);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);

		super.render(matrices, mouseX, mouseY, delta);

		drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
	}

	public class ConfigListWidget extends ElementListWidget<ConfigListWidget.ConfigEntry> {
		public ConfigListWidget() {
			super(ConfigsScreen.this.client, ConfigsScreen.this.width, ConfigsScreen.this.height, 20, ConfigsScreen.this.height - 32, 24);

			for (IConfigContainer config : ConfigsScreen.this.configs) {
				this.addEntry(new CommonConfigEntry(config));
			}
			for (IConfigContainer config : ConfigsScreen.this.clientConfigs) {
				this.addEntry(new ClientConfigEntry(config));
			}
		}

		@Override
		public int getRowWidth() {
			return this.width - 50;
		}

		@Override
		protected int getScrollbarPositionX() {
			return this.getRowRight() + 6;
		}

		protected sealed class ConfigEntry extends ElementListWidget.Entry<ConfigEntry> permits CommonConfigEntry, ClientConfigEntry {
			private final ClickableWidget button;

			private ConfigEntry(ClickableWidget button) {
				this.button = button;
			}

			@Override
			public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
				this.button.y = y;
				this.button.render(matrices, mouseX, mouseY, tickDelta);
			}

			@Override
			public List<? extends Element> children() {
				return ImmutableList.of(this.button);
			}

			@Override
			public List<? extends Selectable> selectableChildren() {
				return ImmutableList.of(this.button);
			}
		}

		@Environment(CLIENT)
		protected final class CommonConfigEntry extends ConfigEntry {
			private CommonConfigEntry(IConfigContainer config) {
				super(new ButtonWidget(ConfigListWidget.this.width / 2 - 155, 0, 310, 20, config.getName(), button
						-> MinecraftClient.getInstance().setScreen(new CommonConfigScreen(ConfigsScreen.this, config))));
			}
		}

		@Environment(CLIENT)
		protected final class ClientConfigEntry extends ConfigEntry {
			private ClientConfigEntry(IConfigContainer config) {
				super(new ButtonWidget(ConfigListWidget.this.width / 2 - 155, 0, 310, 20, config.getName(), button
						-> MinecraftClient.getInstance().setScreen(new ClientConfigScreen(ConfigsScreen.this, config))));
			}
		}
	}
}
