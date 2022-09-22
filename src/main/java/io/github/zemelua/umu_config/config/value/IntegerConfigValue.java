package io.github.zemelua.umu_config.config.value;

import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.function.Function;

public class IntegerConfigValue extends AbstractNumberConfigValue<Integer> {
	public IntegerConfigValue(String name, Integer defaultValue, Integer maxValue, Integer minValue, Function<Integer, Text> textGenerator) {
		super(name, defaultValue, maxValue, minValue, textGenerator);
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.name, this.value);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.name)) {
			this.value = fileJson.get(this.name).getAsInt();
		}
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		sendNBT.putInt(this.name, this.value);
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.name)) {
			this.value = receivedNBT.getInt(this.name);
		}
	}

	@Override
	public Integer convert(double value) {
		return MathHelper.floor(MathHelper.map(value, 0.0D, 1.0D, this.minValue, this.maxValue));
	}

	@Override
	public double convert(Integer value) {
		return MathHelper.map(value, this.minValue, this.maxValue, 0.0D, 1.0D);
	}
}
