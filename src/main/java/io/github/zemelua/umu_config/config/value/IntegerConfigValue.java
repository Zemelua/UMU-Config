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
		return MathHelper.floor(MathHelper.map(value, 0.0D, 1.0D, this.minValue, this.maxValue));
	}

	@Override
	public double convert(Integer value) {
		return MathHelper.map(value, this.minValue, this.maxValue, 0.0D, 1.0D);
	}
}
