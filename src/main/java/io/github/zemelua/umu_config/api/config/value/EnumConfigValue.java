package io.github.zemelua.umu_config.api.config.value;

import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class EnumConfigValue<E extends Enum<E>> extends AbstractConfigValue<E> implements IEnumConfigValue<E> {
	private final Class<E> clazz;

	public EnumConfigValue(Identifier id, Class<E> clazz, E defaultValue) {
		super(id, defaultValue);

		this.clazz = clazz;
	}

	@Override
	public Class<E> getEnumClass() {
		return this.clazz;
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty(this.getKey(), this.value.name());
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.getKey())) {
			this.setValue(Arrays.stream(this.clazz.getEnumConstants())
					.filter(e -> e.name().equals(fileJson.get(this.getKey()).getAsString()))
					.findFirst()
					.orElseThrow());
		}
	}

	@Override
	public void saveTo(NbtCompound sendingNBT) {
		sendingNBT.putInt(this.getKey(), this.getValue().ordinal());
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.getKey())) {
			int ordinal = receivedNBT.getInt(this.getKey());

			this.setValue(this.getEnumClass().getEnumConstants()[ordinal]);
		}
	}

	public static <E extends Enum<E>> Builder<E> builder(Identifier id, Class<E> clazz) {
		return new Builder<>(id, clazz);
	}

	public static class Builder<E extends Enum<E>> {
		private final Identifier id;
		private final Class<E> clazz;
		private E defaultValue;

		private Builder(Identifier id, Class<E> clazz) {
			this.id = id;
			this.clazz = clazz;

			this.defaultValue = clazz.getEnumConstants()[0];
		}

		public Builder<E> defaultValue(E defaultValue) {
			this.defaultValue = defaultValue;

			return this;
		}

		public EnumConfigValue<E> build() {
			return new EnumConfigValue<>(this.id, this.clazz, this.defaultValue);
		}
	}
}
