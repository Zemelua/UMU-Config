package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.config.IConfigValue;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class AbstractConfigScreen extends Screen permits CommonConfigScreen, ClientConfigScreen {
	protected final Screen parent;
	protected final IConfigContainer config;

	@NotNull protected AbstractConfigScreen.ValueListWidget valueListWidget;
	@NotNull protected ClickableWidget cancelButton;
	@NotNull protected ClickableWidget applyButton;

	public AbstractConfigScreen(Screen parent, IConfigContainer config) {
		super(Text.translatable("umu_config." + config.getName() + ".title").append(Text.literal(" (Read Only)")));
		this.parent = parent;
		this.config = config;

		this.valueListWidget = this.createValueListWidget();
		this.cancelButton = this.createCancelButton();
		this.applyButton = this.createApplyButton();
	}

	@Override
	protected void init() {
		this.valueListWidget = this.createValueListWidget();
		this.cancelButton = this.createCancelButton();
		this.applyButton = this.createApplyButton();

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

	private ValueListWidget createValueListWidget() {
		return this.new ValueListWidget();
	}

	private ClickableWidget createCancelButton() {
		return new ButtonWidget(this.width / 2 - 155, this.height - 29, 150, 20, ScreenTexts.CANCEL, button
				-> MinecraftClient.getInstance().setScreen(parent)
		);
	}

	private ClickableWidget createApplyButton() {
		return new ButtonWidget(this.width / 2 + 5, this.height - 29, 150, 20, Text.translatable("gui.ok"), button -> {
			this.applyValues(button);
			MinecraftClient.getInstance().setScreen(this.parent);;
		});
	}

	protected abstract void applyValues(ClickableWidget button);

	public class ValueListWidget extends ElementListWidget<AbstractConfigEntry<?, ?>> {
		private final List<AbstractConfigEntry<?, ?>> configEntries = new ArrayList<>();

		public ValueListWidget() {
			super(AbstractConfigScreen.this.client, AbstractConfigScreen.this.width, AbstractConfigScreen.this.height, 20, AbstractConfigScreen.this.height - 32, 24);

			for (IConfigValue<?> value : AbstractConfigScreen.this.config.getValues()) {
				AbstractConfigEntry<?, ?> entry = value.createEntry();
				this.addEntry(entry);
				this.configEntries.add(entry);
			}
		}

		@Override
		public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			super.render(matrices, mouseX, mouseY, delta);

			AbstractConfigScreen.this.renderTooltip(matrices, Text.literal("tooltip test"), mouseX, mouseY);
		}

		protected List<AbstractConfigEntry<?, ?>> getConfigEntries() {
			return this.configEntries;
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
