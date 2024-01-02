package io.github.zemelua.umu_config.api.client.gui.entry;

import io.github.zemelua.umu_config.api.config.value.IConfigValue;
import io.github.zemelua.umu_config.api.config.value.enternal.IKeyBindConfigValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

public class KeyBindConfigEntry<V extends IConfigValue<InputUtil.Key> & IKeyBindConfigValue> extends AbstractConfigValueEntry<InputUtil.Key, V> {
	private final ButtonWidget editor;
	private boolean selected;
	private boolean duplicate;

	public KeyBindConfigEntry(V config, int indent, boolean readOnly) {
		super(config, indent, readOnly);

		this.editor = ButtonWidget.builder(config.getKeyBinding().getBoundKeyLocalizedText(), button -> {
			this.selected = true;
			this.update();
		})
				.dimensions(0, 0, 0, 20)
				.build();
		this.selected = false;
		this.duplicate = false;
		this.children.add(this.editor);
		this.selectableChildren.add(this.editor);
		this.clickableWidgets.add(this.editor);
		this.editor.active = config.isAvailable();
	}

	private void update() {
		KeyBinding.updateKeysByCode();
		this.editor.setMessage(this.config.getKeyBinding().getBoundKeyLocalizedText());
		this.duplicate = false;
		MutableText mutableText = Text.empty();
		if (!this.config.getKeyBinding().isUnbound()) {
			for (KeyBinding keyBinding : MinecraftClient.getInstance().options.allKeys) {
				if (keyBinding == this.config.getKeyBinding() || !this.config.getKeyBinding().equals(keyBinding)) continue;
				if (this.duplicate) {
					mutableText.append(", ");
				}
				this.duplicate = true;
				mutableText.append(Text.translatable(keyBinding.getTranslationKey()));
			}
		}
		if (this.duplicate) {
			this.editor.setMessage(Text.literal("[ ").append(this.editor.getMessage().copy().formatted(Formatting.WHITE)).append(" ]").formatted(Formatting.RED));
			this.editor.setTooltip(Tooltip.of(Text.translatable("controls.keybinds.duplicateKeybinds", mutableText)));
		} else {
			this.editor.setTooltip(null);
		}
		if (this.selected) {
			this.editor.setMessage(Text.literal("> ").append(this.editor.getMessage().copy().formatted(Formatting.WHITE, Formatting.UNDERLINE)).append(" <").formatted(Formatting.YELLOW));
		}
	}

	@Override
	protected void renderEditor(DrawContext context, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		// context.drawText(this.textRenderer, this.bindingName, x + 90 - ControlsListWidget.this.maxKeyNameLength, y + entryHeight / 2 - ((ControlsListWidget)ControlsListWidget.this).client.textRenderer.fontHeight / 2, 0xFFFFFF, false);
		this.editor.setX(x + entryWidth / 2);
		this.editor.setWidth(x + entryWidth - 65 - this.editor.getX());
		this.editor.setY(y + entryHeight / 2 - this.editor.getHeight() / 2);
		if (this.duplicate) {
			int i = 3;
			int j = this.editor.getX() - 6;
			context.fill(j, y + 2, j + 3, y + entryHeight + 2, (int)(Formatting.RED.getColorValue() | 0xFF000000));
		}
		this.editor.render(context, mouseX, mouseY, tickDelta);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		GameOptions options = MinecraftClient.getInstance().options;

		if (this.selected) {
			if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
				options.setKeyCode(this.config.getKeyBinding(), InputUtil.UNKNOWN_KEY);
			} else {
				options.setKeyCode(this.config.getKeyBinding(), InputUtil.fromKeyCode(keyCode, scanCode));
			}
			this.selected = false;
			this.update();
			return true;
		}

		return super.keyPressed(keyCode, scanCode, modifiers);
	}
}
