package io.github.zemelua.umu_config.client.gui.entry;

import com.google.common.collect.ImmutableList;
import io.github.zemelua.umu_config.client.gui.AbstractConfigScreen;
import io.github.zemelua.umu_config.config.category.IConfigCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import static net.minecraft.util.Formatting.*;

public class ConfigCategoryEntry extends AbstractConfigEntry {
	private static final OrderedText UNFOLD_SIGN = OrderedText.concat(
			Text.literal("(").asOrderedText(),
			Text.literal("+").formatted(GREEN).asOrderedText(),
			Text.literal(")").asOrderedText());
	private static final OrderedText FOLD_SIGN = OrderedText.concat(
			Text.literal("(").asOrderedText(),
			Text.literal("-").formatted(RED).asOrderedText(),
			Text.literal(")").asOrderedText());

	private final AbstractConfigScreen.ValueListWidget parent;
	private final ImmutableList<AbstractConfigEntry> contents;
	private final IConfigCategory category;

	private boolean isFolded;

	public ConfigCategoryEntry(AbstractConfigScreen.ValueListWidget parent, IConfigCategory category, int indent) {
		super(indent);

		this.category = category;
		this.parent = parent;
		this.contents = this.category.getElements().stream()
				.map(value -> value.createEntry(parent, this.indent + 1))
				.collect(ImmutableList.toImmutableList());
		this.isFolded = true;
	}

	@Override
	public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);

		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		OrderedText displayText = OrderedText.concat((this.isFolded ? UNFOLD_SIGN : FOLD_SIGN), Text.literal(" ").append(this.category.getName()).asOrderedText());
		textRenderer.draw(matrices, displayText, x + 10 + this.indent * 12, (float) (y + entryHeight / 2 - textRenderer.fontHeight / 2), 0xFFFFFF);
	}

	@Override
	public Text getTooltip() {
		return Text.empty();
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		super.mouseClicked(mouseX, mouseY, button);

		if (this.isFolded) {
			int index = this.parent.children().indexOf(this);
			for (AbstractConfigEntry entry : this.contents) {
				index++;
				this.parent.children().add(index, entry);
			}
		} else {
			this.parent.children().removeAll(this.contents);
		}
		this.isFolded = !this.isFolded;

		return true;
	}

	@Override
	public void applyValue() {
		this.contents.forEach(AbstractConfigEntry::applyValue);
	}
}
