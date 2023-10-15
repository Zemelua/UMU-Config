package io.github.zemelua.umu_config.client.gui;

import io.github.zemelua.umu_config.client.ModClientConfigs;
import io.github.zemelua.umu_config.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import io.github.zemelua.umu_config.util.ModUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConfigScreen extends Screen {
	protected final Screen parent;
	protected final IConfigContainer config;
	protected final boolean readOnly;

	@NotNull protected AbstractConfigScreen.ValueListWidget valueListWidget;
	@NotNull protected ClickableWidget cancelButton;
	@NotNull protected ClickableWidget applyButton;

	public AbstractConfigScreen(Screen parent, IConfigContainer config, Text title) {
		super(title);

		this.parent = parent;
		this.config = config;

		this.readOnly = ModUtils.isInMultiplayServer() && !this.config.canEdit(MinecraftClient.getInstance().player);
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
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		this.renderBackground(context, mouseX, mouseY, delta);

		super.render(context, mouseX, mouseY, delta);

		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
	}

	protected ValueListWidget createValueListWidget() {
		return this.new ValueListWidget();
	}

	protected ClickableWidget createCancelButton() {
		int x = ModClientConfigs.reverseApplyButtons() ? this.width / 2 + 5 : this.width / 2 - 155;

		return new ButtonWidget.Builder(ScreenTexts.CANCEL, button -> MinecraftClient.getInstance().setScreen(this.parent))
				.dimensions(x, this.height - 29, 150, 20)
				.build();
	}

	protected ClickableWidget createApplyButton() {
		int x = ModClientConfigs.reverseApplyButtons() ? this.width / 2 - 155 : this.width / 2 + 5;

		return new ButtonWidget.Builder(Text.translatable("gui.ok"), button -> {
			this.applyValues(button);
			MinecraftClient.getInstance().setScreen(this.parent);
		}).dimensions(x, this.height - 29, 150, 20).build();
	}

	protected abstract void applyValues(ClickableWidget button);

	public class ValueListWidget extends ElementListWidget<AbstractConfigEntry> {
		private final List<AbstractConfigEntry> configEntries = new ArrayList<>();

		public ValueListWidget() {
			super(AbstractConfigScreen.this.client, AbstractConfigScreen.this.width, AbstractConfigScreen.this.height, 20, AbstractConfigScreen.this.height - 32, ModClientConfigs.getEntrySpacing());

			AbstractConfigScreen.this.config.getElements().forEach(value -> {
				AbstractConfigEntry entry = value.createEntry(this, 0, AbstractConfigScreen.this.readOnly);
				this.addEntry(entry);
				this.configEntries.add(entry);
			});
		}

		@Override
		public void render(DrawContext context, int mouseX, int mouseY, float delta) {
			super.render(context, mouseX, mouseY, delta);

			TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
			if (this.getHoveredEntry() != null) {
				List<OrderedText> tooltip = textRenderer.wrapLines(this.getHoveredEntry().getTooltip(), 200);
				context.drawOrderedTooltip(textRenderer, tooltip, mouseX, mouseY);
			}
		}

		protected List<AbstractConfigEntry> getConfigEntries() {
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
