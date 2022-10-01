package io.github.zemelua.umu_config.client.gui.entry;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.zemelua.umu_config.config.value.IConfigValue;
import io.github.zemelua.umu_config.config.value.IEnumConfigValue;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;

public class EnumConfigEntry<T extends Enum<T>, V extends IConfigValue<T> & IEnumConfigValue<T>> extends AbstractConfigValueEntry<T, V> {
	private final ButtonWidget editor;

	public EnumConfigEntry(V config, int indent, boolean readOnly) {
		super(config, indent, readOnly);

		this.editor = new ButtonWidget(0, 0, 0, 20, Text.empty(), (button) -> this.modifyingValue = getNext(this.config.getEnumClass(), this.modifyingValue));
		this.children.add(this.editor);
		this.selectableChildren.add(this.editor);
		this.clickableWidgets.add(this.editor);
	}

	@Override
	protected void renderEditor(MatrixStack matrices, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		this.editor.x = x + entryWidth / 2;
		this.editor.setWidth(x + entryWidth - 65 - this.editor.x);
		this.editor.y = y;
		this.editor.setMessage(this.config.getValueText(this.modifyingValue));
		this.editor.render(matrices, mouseX, mouseY, tickDelta);

		if (this.editor.isHovered()) {
			int barWidth = this.editor.getWidth() - 16;
			int size = this.config.getEnumClass().getEnumConstants().length;
			float oneBarSize = (barWidth - (size - 1) * 2) / (float) size;
			for (int i = 0; i < size; i++) {
				Matrix4f matrix = matrices.peek().getPositionMatrix();
				int color = i == this.modifyingValue.ordinal() ? 0xFFFFFFFF : 0xFF202020;
				int shadowColor = 0xFF404040;
				float barX = this.editor.x + 8 + (oneBarSize + 2) * i;
				BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
				RenderSystem.enableBlend();
				RenderSystem.disableTexture();
				RenderSystem.defaultBlendFunc();
				RenderSystem.setShader(GameRenderer::getPositionColorShader);
				bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
				bufferBuilder.vertex(matrix, barX + 1, y + 18, 1.0f).color(shadowColor).next();
				bufferBuilder.vertex(matrix, barX + oneBarSize + 1, y + 18, 1.0f).color(shadowColor).next();
				bufferBuilder.vertex(matrix, barX + oneBarSize + 1, y + 17F, 1.0f).color(shadowColor).next();
				bufferBuilder.vertex(matrix, barX + 1, y + 17F, 1.0f).color(shadowColor).next();
				bufferBuilder.vertex(matrix, barX, y + 17, 1.03f).color(color).next();
				bufferBuilder.vertex(matrix, barX + oneBarSize, y + 17, 1.03f).color(color).next();
				bufferBuilder.vertex(matrix, barX + oneBarSize, y + 16F, 1.03f).color(color).next();
				bufferBuilder.vertex(matrix, barX, y + 16F, 1.03f).color(color).next();
				BufferRenderer.drawWithShader(bufferBuilder.end());
				RenderSystem.enableTexture();
				RenderSystem.disableBlend();
			}
		}
	}

	private static <T extends Enum<T>> T getNext(Class<T> clazz, T current) {
		T[] values = clazz.getEnumConstants();
		int index = current.ordinal() + 1;

		if (index >= values.length) {
			return values[0];
		}

		return values[index];
	}
}
