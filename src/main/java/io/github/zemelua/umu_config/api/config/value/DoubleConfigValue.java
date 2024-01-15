package io.github.zemelua.umu_config.api.config.value;

import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class DoubleConfigValue extends AbstractRangedConfigValue<Double> {
	public DoubleConfigValue(Identifier id, Double defaultValue, Double minValue, Double maxValue) {
		super(id, defaultValue, minValue, maxValue);
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.getKey(), this.getValue());
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.getKey())) {
			this.setValue(fileJson.get(this.getKey()).getAsDouble());
		}
	}

	@Override
	public void saveTo(NbtCompound sendingNBT) {
		sendingNBT.putDouble(this.getKey(), this.getValue());
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.getKey())) {
			this.setValue(receivedNBT.getDouble(this.getKey()));
		}
	}

	public static class Builder {
		private final Identifier id;
		private double defaultValue = 0.0D;
		private double minValue = 0.0D;
		private double maxValue = 1.0D;

		private Builder(Identifier id) {
			this.id = id;
		}

		public Builder defaultValue(double defaultValue) {
			this.defaultValue = defaultValue;

			return this;
		}

		public Builder min(double minValue) {
			this.minValue = minValue;

			return this;
		}

		public Builder max(double maxValue) {
			this.maxValue = maxValue;

			return this;
		}

		public Builder range(double min, double max) {
			return this.min(min).max(max);
		}

		public DoubleConfigValue build() {
			return new DoubleConfigValue(this.id, this.defaultValue, this.minValue, this.maxValue);
		}
	}
}
