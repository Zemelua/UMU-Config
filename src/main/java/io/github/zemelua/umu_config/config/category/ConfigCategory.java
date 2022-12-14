package io.github.zemelua.umu_config.config.category;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.config.IConfigElement;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;

import java.util.List;

public class ConfigCategory implements IConfigCategory {
	private final Identifier ID;
	private final List<IConfigElement> elements;

	public ConfigCategory(Identifier ID, IConfigElement... elements) {
		this.ID = ID;
		this.elements = ImmutableList.copyOf(elements);
	}

	@Override
	public List<IConfigElement> getElements() {
		return this.elements;
	};

	@Override
	public void saveTo(JsonObject fileJson) {
		JsonObject compositeJson = new JsonObject();
		this.elements.forEach(element -> element.saveTo(compositeJson));

		fileJson.add(this.ID.getPath(), compositeJson);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.ID.getPath())) {
			JsonObject compositeJson = fileJson.get(this.ID.getPath()).getAsJsonObject();
			this.elements.forEach(element -> element.loadFrom(compositeJson));
		}
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		NbtCompound compositeNBT = new NbtCompound();
		this.elements.forEach(element -> element.saveTo(compositeNBT));

		sendNBT.put(this.ID.getPath(), compositeNBT);
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.ID.getPath(), NbtElement.COMPOUND_TYPE)) {
			NbtCompound compositeNBT = receivedNBT.getCompound(this.ID.getPath());
			this.elements.forEach(element -> element.loadFrom(compositeNBT));
		}
	}

	@Override
	public Identifier getID() {
		return this.ID;
	}
}
