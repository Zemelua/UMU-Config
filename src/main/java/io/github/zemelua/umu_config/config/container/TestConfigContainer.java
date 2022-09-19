package io.github.zemelua.umu_config.config.container;

import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class TestConfigContainer implements IConfigContainer {
	public boolean testBool;

	@Override
	public Path getPath() {
		return FabricLoader.getInstance().getConfigDir().resolve("test_config" + ".json");
	}

	@Override
	public void saveTo(JsonObject fileJson) {
		fileJson.addProperty("testBool", this.testBool);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		this.testBool = fileJson.get("testBool").getAsBoolean();
	}
}