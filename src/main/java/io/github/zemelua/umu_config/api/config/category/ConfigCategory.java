package io.github.zemelua.umu_config.api.config.category;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.api.config.IConfigElement;
import io.github.zemelua.umu_config.api.config.value.IConfigValue;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ConfigCategory implements IConfigCategory {
	private final Identifier id;
	private final ImmutableList<IConfigElement> elements;

	public ConfigCategory(Identifier id, IConfigElement... elements) {
		this.id = id;
		this.elements = ImmutableList.copyOf(elements);
	}

	@Override
	public List<IConfigElement> getElements() {
		return this.elements;
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		JsonObject compositeJson = new JsonObject();
		this.elements.forEach(element -> element.saveTo(compositeJson));

		fileJson.add(this.getKey(), compositeJson);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has(this.getKey())) {
			JsonObject compositeJson = fileJson.get(this.getKey()).getAsJsonObject();
			this.elements.forEach(element -> element.loadFrom(compositeJson));
		}
	}

	@Override
	public Identifier getID() {
		return this.id;
	}

	protected String getKey() {
		return this.id.getPath();
	}

	public static Builder builder(Identifier id) {
		return new Builder(id);
	}

	public static class Builder {
		private final Identifier id;
		private final List<IConfigElement> elements = new ArrayList<>();

		private Builder(Identifier id) {
			this.id = id;
		}

		public Builder addValue(IConfigValue<?> value) {
			this.elements.add(value);

			return this;
		}

		public Builder addCategory(IConfigCategory category) {
			this.elements.add(category);

			return this;
		}

		public ConfigCategory build() {
			return new ConfigCategory(this.id, this.elements.toArray(new IConfigElement[0]));
		}
	}
}
