package io.github.zemelua.umu_config.api.config;

import com.google.gson.*;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.lang3.SerializationException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigFileManager {
	public static final Gson GSON = new GsonBuilder()
			.setPrettyPrinting()
			.create();

	public static void saveFrom(IConfigContainer config) throws SerializationException {
		Path configPath = config.getPath();

		try {
			JsonObject fileJson = getOrCreateFileJson(configPath);
			config.saveTo(fileJson);
			BufferedWriter writer = Files.newBufferedWriter(configPath);
			GSON.toJson(fileJson, writer);
			writer.close();
		} catch (IOException exception) {
			throw new SerializationException(exception);
		}
	}

	public static void loadTo(IConfigContainer config) throws SerializationException {
		Path configPath = config.getPath();

		try {
			config.loadFrom(getOrCreateFileJson(configPath));
		} catch (IOException exception) {
			throw new SerializationException(exception);
		}
	}

	private static JsonObject getOrCreateFileJson(Path configPath) throws IOException {
		JsonObject fileJson;

		try {
			BufferedReader reader = Files.newBufferedReader(configPath);
			fileJson = JsonParser.parseReader(reader).getAsJsonObject();
			reader.close();
		} catch (IOException | JsonSyntaxException exception) {
			fileJson = createFileJson(configPath);
			UMUConfig.LOGGER.info(
					"The file did not exist or was not in the correct format, so a new config file {} was generated.",
					configPath.getFileName().toString()
			);
		}

		return fileJson;
	}

	private static JsonObject createFileJson(Path configPath) throws IOException {
		if (!Files.exists(configPath)) {
			Files.createDirectories(configPath.getParent());
			Files.createFile(configPath);
		}

		BufferedWriter writer = Files.newBufferedWriter(configPath);
		JsonObject createdJson = new JsonObject();
		GSON.toJson(createdJson, writer);
		writer.close();

		return createdJson;
	}


	public static Path getConfigPath() {
		return FabricLoader.getInstance().getConfigDir();
	}
}
