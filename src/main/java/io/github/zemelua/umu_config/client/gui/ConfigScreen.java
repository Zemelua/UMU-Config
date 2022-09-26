package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.config.IConfigValue;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static io.github.zemelua.umu_config.network.NetworkHandler.*;

public class ConfigScreen extends Screen {
	private final Screen parent;
	private final IConfigContainer config;

	@NotNull private ConfigScreen.ValueListWidget valueListWidget;
	@NotNull private ClickableWidget cancelButton;
	@NotNull private ClickableWidget applyButton;

	public ConfigScreen(Screen parent, IConfigContainer config) {
		super(Text.translatable("umu_config." + config.getName() + "title"));
		this.parent = parent;
		this.config = config;

		this.valueListWidget = new ValueListWidget();
		this.cancelButton = new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, ScreenTexts.CANCEL, button
				-> MinecraftClient.getInstance().setScreen(parent));
		this.applyButton = new ButtonWidget(this.width / 2 + 5, this.height - 29, 150, 20, Text.translatable("gui.ok"), button -> {
			this.valueListWidget.configEntries.forEach(AbstractConfigEntry::applyValue);
			if (this.client != null) {
				if (this.client.world == null) {
				} else {
					PacketByteBuf packet = PacketByteBufs.create();
					NbtCompound values = new NbtCompound();
					this.config.saveTo(values);
					packet.writeString(this.config.getName());
					packet.writeNbt(values);

					ClientPlayNetworking.send(CHANNEL_SAVE_CONFIG, packet);
				}
			}
			MinecraftClient.getInstance().setScreen(this.parent);
		});
	}

	@Override
	protected void init() {
		this.valueListWidget = new ValueListWidget();
		this.cancelButton = new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, ScreenTexts.CANCEL, button
				-> MinecraftClient.getInstance().setScreen(this.parent));
		this.applyButton = new ButtonWidget(this.width / 2 + 5, this.height - 29, 150, 20, Text.translatable("gui.ok"), button -> {

			this.valueListWidget.configEntries.forEach(AbstractConfigEntry::applyValue);
			UMUConfig.LOGGER.info(this.client == null);
			if (this.client != null) {
				if (this.client.world == null) {
				} else {
					PacketByteBuf packet = PacketByteBufs.create();
					NbtCompound values = new NbtCompound();
					this.config.saveTo(values);
					packet.writeString(this.config.getName());
					packet.writeNbt(values);

					ClientPlayNetworking.send(CHANNEL_SAVE_CONFIG, packet);
				}
//				if (this.client.world == null) {
//					UMUConfig.LOGGER.info("ser");
//				} else if (this.client.isIntegratedServerRunning() && !Objects.requireNonNull(this.client.getServer()).isRemote()) {
//					ClientPlayNetworking.send(CHANNEL_SAVE_CONFIG, PacketByteBufs.create());
//				} else {
//					ClientPlayNetworking.send(CHANNEL_SAVE_CONFIG, PacketByteBufs.create());
//				}
			}
			MinecraftClient.getInstance().setScreen(this.parent);
		});

		this.addDrawableChild(this.valueListWidget);
		this.addDrawableChild(this.cancelButton);
		this.addDrawableChild(this.applyButton);
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		this.renderBackground(matrices);

		super.render(matrices, mouseX, mouseY, delta);

		drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
	}

	public class ValueListWidget extends ElementListWidget<AbstractConfigEntry<?, ?>> {
		private final List<AbstractConfigEntry<?, ?>> configEntries = new ArrayList<>();

		public ValueListWidget() {
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
	}
}
