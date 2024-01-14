package io.github.zemelua.umu_config.client.screen;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.Optional;

@Environment(value= EnvType.CLIENT)
public class ConfigSelectScreen extends Screen {
	private static final Text CLIENT_CONFIG_LABEL = Text.translatable("screen.config_select.label.client");
	private static final Text COMMON_CONFIG_LABEL = Text.translatable("screen.config_select.label.common");

	private final ConfigBuilder clientScreen;
	private final ConfigBuilder commonScreen;

	public ConfigSelectScreen(ConfigBuilder clientScreen, ConfigBuilder commonScreen) {
		super(Text.translatable("screen.config_select.title"));

		this.clientScreen = clientScreen;
		this.commonScreen = commonScreen;
	}

	@Override
	protected void init() {
		super.init();

		int i = MathHelper.clamp(this.getTitleY() + 40, this.height / 6 + 96, this.height - 24);

		this.clearChildren();
		Optional.ofNullable(this.client).ifPresent(c -> {
			this.addDrawableChild(ButtonWidget.builder(CLIENT_CONFIG_LABEL, button -> c.setScreen(this.clientScreen.build()))
					.dimensions(this.width / 2 - 155, i, 150, 20).build());
			this.addDrawableChild(ButtonWidget.builder(COMMON_CONFIG_LABEL, button -> c.setScreen(this.commonScreen.build()))
					.dimensions(this.width / 2 - 155 + 160, i, 150, 20).build());
		});
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);

		context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.getTitleY(), 0xFFFFFF);
	}

	private int getTitleY() {
		int i = this.height / 2;
		return MathHelper.clamp(i - 20 - this.textRenderer.fontHeight, 10, 80);
	}
}
