package io.github.zemelua.umu_config.api.config.value;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

public class BooleanConfigValue extends AbstractConfigValue<Boolean> {
	public BooleanConfigValue(Identifier id, Boolean defaultValue) {
		super(id, defaultValue);
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.getKey(), this.getValue());
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.getKey())) {
			this.setValue(fileJson.get(this.getKey()).getAsBoolean());
		}
	}

	public static Builder builder(Identifier id) {
		return new Builder(id);
	}

	public static class Builder {
		private final Identifier id;
		private boolean defaultValue = true;

		private Builder(Identifier id) {
			this.id = id;
		}

		public Builder defaultValue(boolean defaultValue) {
			this.defaultValue = defaultValue;

			return this;
		}

		public BooleanConfigValue build() {
			return new BooleanConfigValue(this.id, this.defaultValue);
		}
	}
}
