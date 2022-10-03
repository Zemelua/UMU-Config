package io.github.zemelua.umu_config.config.value;

import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Locale;
import java.util.function.Function;

public class FloatConfigValue extends AbstractNumberConfigValue<Float> {
	public FloatConfigValue(Identifier ID, Float defaultValue, Float maxValue, Float minValue, Function<Float, Text> textGenerator) {
		super(ID, defaultValue, maxValue, minValue, textGenerator);
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.ID.getPath(), this.value);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.ID.getPath())) {
			this.value = fileJson.get(this.ID.getPath()).getAsFloat();
		}
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		sendNBT.putFloat(this.ID.getPath(), this.value);
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.ID.getPath())) {
			this.value = receivedNBT.getFloat(this.ID.getPath());
		}
	}

	@Override
	public Float convert(double value) {
		return (float) MathHelper.map(value, 0.0D, 1.0D, this.minValue.doubleValue(), this.maxValue.doubleValue());
	}

	@Override
	public double convert(Float value) {
		return MathHelper.map(value, this.minValue.doubleValue(), this.maxValue.doubleValue(), 0.0D, 1.0D);
	}

	public static class Builder {
		private final Identifier ID;
		private float defaultValue = 0.0F;
		private float maxValue = 0.0F;
		private float minValue = 1.0F;
		private Function<Float, Text> textGenerator = value -> Text.literal(String.format(Locale.ROOT, "%.1f", value));

		public Builder(Identifier ID) {
			this.ID = ID;
		}

		public Builder defaultValue(float value) {
			this.defaultValue = value;

			return this;
		}

		public Builder maxValue(float value) {
			this.maxValue = value;

			return this;
		}

		public Builder minValue(float value) {
			this.minValue = value;

			return this;
		}

		public Builder textGenerator(Function<Float, Text> value) {
			this.textGenerator = value;

			return this;
		}

		public FloatConfigValue build() {
			return new FloatConfigValue(this.ID, this.defaultValue, this.maxValue, this.minValue, this.textGenerator);
		}
	}
}
