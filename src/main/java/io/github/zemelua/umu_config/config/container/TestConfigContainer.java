package io.github.zemelua.umu_config.config.container;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.config.IConfigValue;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.List;

public class TestConfigContainer implements IConfigContainer {
	public boolean testBool;

	@Override
	public String getName() {
		return null;
	}

	@Override public List<IConfigValue<?>> getValues() {
		return null;
	}

	@Override
	public Path getPath() {
		return FabricLoader.getInstance().getConfigDir().resolve("test_config" + ".json");
	}

	@Override
	public void insertIfAbsent(JsonObject fileJson) {
		fileJson.addProperty("testBool", this.testBool);
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty("testBool", this.testBool);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		if (fileJson.has("testBool")) {
			this.testBool = fileJson.get("testBool").getAsBoolean();
		}
	}
}