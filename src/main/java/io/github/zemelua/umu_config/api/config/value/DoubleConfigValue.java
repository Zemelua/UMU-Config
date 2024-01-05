package io.github.zemelua.umu_config.api.config.value;

import com.google.gson.JsonObject;
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
}
