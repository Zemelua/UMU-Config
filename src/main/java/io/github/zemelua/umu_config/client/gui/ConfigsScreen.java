package io.github.zemelua.umu_config.client.gui;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_config.config.ConfigManager;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import io.github.zemelua.umu_config.util.ModUtils;
import net.fabricmc.api.EnvType;
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

public class ConfigsScreen extends Screen {
	private final Screen parent;
	private final ImmutableList<IConfigContainer> configs;

	@NotNull private ConfigListWidget configListWidget;
	@NotNull private ClickableWidget backButton;

	public ConfigsScreen(Screen parent, String modID) {
		this(parent, Text.of(ModUtils.getModName(modID)), ConfigManager.byModID(modID).toArray(new IConfigContainer[0]));
	}

	public ConfigsScreen(Screen parent, Text title, IConfigContainer... configs) {
		super(title);

		this.parent = parent;
		this.configs = ImmutableList.copyOf(configs);
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
				this.addEntry(new ConfigEntry(config));
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

		@Environment(value = EnvType.CLIENT)
		protected class ConfigEntry extends ElementListWidget.Entry<ConfigEntry> {
			private final ClickableWidget button;

			private ConfigEntry(IConfigContainer config) {
				this.button = new ButtonWidget(ConfigListWidget.this.width / 2 - 155, 0, 310, 20, Text.literal(config.getName()), button
						-> MinecraftClient.getInstance().setScreen(new ConfigScreen(ConfigsScreen.this, config)));
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
	}
}