package io.github.zemelua.umu_config.old.api.client.gui.entry;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.zemelua.umu_config.client.ModClientConfigs;
import io.github.zemelua.umu_config.config.value.IConfigValue;
import io.github.zemelua.umu_config.config.value.IEnumConfigValue;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

public class EnumConfigEntry<T extends Enum<T>, V extends IConfigValue<T> & IEnumConfigValue<T>> extends AbstractConfigValueEntry<T, V> {
	private final ButtonWidget editor;

	public EnumConfigEntry(V config, int indent, boolean readOnly) {
		super(config, indent, readOnly);

		this.editor = new ButtonWidget.Builder(Text.empty(), button -> this.modifyingValue = getNext(this.config.getEnumClass(), this.modifyingValue))
				.dimensions(0, 0, 0, 20)
				.build();
		this.children.add(this.editor);
		this.selectableChildren.add(this.editor);
		this.clickableWidgets.add(this.editor);
	}

	@Override
	protected void renderEditor(DrawContext context, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
		this.editor.method_46421(x + entryWidth / 2);
		this.editor.setWidth(x + entryWidth - 65 - this.editor.getX());
		this.editor.method_46419(y + entryHeight / 2 - this.editor.getHeight() / 2);
		this.editor.setMessage(this.config.getValueText(this.modifyingValue));
		this.editor.render(context, mouseX, mouseY, tickDelta);

		if (ModClientConfigs.drawEnumEntryBar(this.editor)) {
			int barWidth = this.editor.getWidth() - 16;
			int size = this.config.getEnumClass().getEnumConstants().length;
			float oneBarSize = (barWidth - (size - 1) * 2) / (float) size;
			for (int i = 0; i < size; i++) {
				Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
				int color = i == this.modifyingValue.ordinal() ? 0xFFFFFFFF : 0xFF202020;
				int shadowColor = 0xFF404040;
				float barX = this.editor.getX() + 8 + (oneBarSize + 2) * i;
				BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.setShader(GameRenderer::getPositionColorProgram);
				bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
				bufferBuilder.vertex(matrix, barX + 1, y + 18, 1.0f).color(shadowColor).next();
				bufferBuilder.vertex(matrix, barX + oneBarSize + 1, y + 18, 1.0f).color(shadowColor).next();
				bufferBuilder.vertex(matrix, barX + oneBarSize + 1, y + 17F, 1.0f).color(shadowColor).next();
				bufferBuilder.vertex(matrix, barX + 1, y + 17F, 1.0f).color(shadowColor).next();
				bufferBuilder.vertex(matrix, barX, y + 17, 1.03f).color(color).next();
				bufferBuilder.vertex(matrix, barX + oneBarSize, y + 17, 1.03f).color(color).next();
				bufferBuilder.vertex(matrix, barX + oneBarSize, y + 16F, 1.03f).color(color).next();
				bufferBuilder.vertex(matrix, barX, y + 16F, 1.03f).color(color).next();
				BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
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
