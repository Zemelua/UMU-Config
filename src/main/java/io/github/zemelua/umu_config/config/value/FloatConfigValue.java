package io.github.zemelua.umu_config.config.value;

import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

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
}
