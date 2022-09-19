package io.github.zemelua.umu_config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import io.github.zemelua.umu_config.serializer.ConfigSerializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.SerializationException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigHandler {
	public static final Gson GSON = new GsonBuilder()
			.registerTypeHierarchyAdapter(IConfigContainer.class, new ConfigSerializer())
			.setPrettyPrinting()
			.create();

	public static void saveFrom(IConfigContainer config) throws SerializationException {
		Path configPath = config.getPath();

		try {
			Files.createDirectories(configPath.getParent());
			BufferedWriter writer = Files.newBufferedWriter(configPath);
			GSON.toJson(config, writer);
			writer.close();
		} catch (IOException exception) {
			throw new SerializationException(exception);
		}
	}

	public static void loadTo(IConfigContainer config) throws SerializationException {
		Path configPath = config.getPath();

		try {
			BufferedReader reader = Files.newBufferedReader(configPath);
			JsonObject jsonObject = GSON.fromJson(reader, JsonObject.class);
			config.loadFrom(jsonObject);
		} catch (IOException exception) {
			throw new SerializationException(exception);
		}
	}


	public static Path getConfigPath() {
		return FabricLoader.getInstance().getConfigDir();
	}
}
