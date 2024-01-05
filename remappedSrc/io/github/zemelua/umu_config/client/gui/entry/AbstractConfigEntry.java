package io.github.zemelua.umu_config.old.gui.entry;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConfigEntry extends ElementListWidget.Entry<AbstractConfigEntry> {
	protected final List<Element> children = new ArrayList<>();
	protected final List<Selectable> selectableChildren = new ArrayList<>();
	protected final int indent;

	public AbstractConfigEntry(int indent) {
		this.indent = indent;
	}

	@Override
	public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		if (hovered) {
			context.fill(x, y, x + entryWidth, y + entryHeight, 0x24FFFFFF);
		}
	}

	@Override
	public List<? extends Element> children() {
		return this.children;
	}

	@Override
	public List<? extends Selectable> selectableChildren() {
		return this.selectableChildren;
	}

	public abstract Text getTooltip();

	public abstract void applyValue();
}
