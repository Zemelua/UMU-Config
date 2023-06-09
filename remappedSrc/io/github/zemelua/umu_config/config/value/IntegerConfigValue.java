package io.github.zemelua.umu_config.config.value;

import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

public class IntegerConfigValue extends AbstractNumberConfigValue<Integer> {
	public IntegerConfigValue(Identifier ID, Integer defaultValue, Integer maxValue, Integer minValue, Function<Integer, Text> textGenerator) {
		super(ID, defaultValue, maxValue, minValue, textGenerator);
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.ID.getPath(), this.value);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.ID.getPath())) {
			this.value = fileJson.get(this.ID.getPath()).getAsInt();
		}
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		sendNBT.putInt(this.ID.getPath(), this.value);
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.ID.getPath())) {
			this.value = receivedNBT.getInt(this.ID.getPath());
		}
	}

	@Override
	public Integer convert(double value) {
		return MathHelper.floor(MathHelper.map(value, 0.0D, 1.0D, this.minValue.doubleValue(), this.maxValue.doubleValue()));
	}

	@Override
	public double convert(Integer value) {
		return MathHelper.map(value, this.minValue.doubleValue(), this.maxValue.doubleValue(), 0.0D, 1.0D);
	}

	public static class Builder {
		private final Identifier ID;
		private int defaultValue = 0;
		private int maxValue = 0;
		private int minValue = 1;
		private Function<Integer, Text> textGenerator = value -> Text.literal(String.valueOf(value));

		public Builder(Identifier ID) {
			this.ID = ID;
		}

		public Builder defaultValue(int value) {
			this.defaultValue = value;

			return this;
		}

		public Builder maxValue(int value) {
			this.maxValue = value;

			return this;
		}

		public Builder minValue(int value) {
			this.minValue = value;

			return this;
		}

		public Builder textGenerator(Function<Integer, Text> value) {
			this.textGenerator = value;

			return this;
		}

		public IntegerConfigValue build() {
			return new IntegerConfigValue(this.ID, this.defaultValue, this.maxValue, this.minValue, this.textGenerator);
		}
	}
}
