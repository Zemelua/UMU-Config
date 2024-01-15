package io.github.zemelua.umu_config.api.config.value;

import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class IntegerConfigValue extends AbstractRangedConfigValue<Integer> {
	public IntegerConfigValue(Identifier id, Integer defaultValue, Integer minValue, Integer maxValue) {
		super(id, defaultValue, minValue, maxValue);
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.getKey(), this.getValue());
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.getKey())) {
			this.setValue(fileJson.get(this.getKey()).getAsInt());
		}
	}

	@Override
	public void saveTo(NbtCompound sendingNBT) {
		sendingNBT.putInt(this.getKey(), this.getValue());
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.getKey())) {
			this.setValue(receivedNBT.getInt(this.getKey()));
		}
	}

	public static Builder builder(Identifier id) {
		return new Builder(id);
	}

	public static class Builder {
		private final Identifier id;
		private int defaultValue = 0;
		private int minValue = 0;
		private int maxValue = 1;

		private Builder(Identifier id) {
			this.id = id;
		}

		public Builder defaultValue(int defaultValue) {
			this.defaultValue = defaultValue;

			return this;
		}

		public Builder min(int minValue) {
			this.minValue = minValue;

			return this;
		}

		public Builder max(int maxValue) {
			this.maxValue = maxValue;

			return this;
		}

		public Builder range(int min, int max) {
			return this.min(min).max(max);
		}

		public IntegerConfigValue build() {
			return new IntegerConfigValue(this.id, this.defaultValue, this.minValue, this.maxValue);
		}
	}
}
