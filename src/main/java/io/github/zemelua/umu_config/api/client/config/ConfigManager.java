package io.github.zemelua.umu_config.api.client.config;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import io.github.zemelua.umu_config.api.config.IConfigProvider;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import io.github.zemelua.umu_config.config.ConfigFileManager;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static net.fabricmc.api.EnvType.CLIENT;

public final class ConfigManager {
	private final Map<String, ImmutableCollection<IConfigContainer>> configs = new HashMap<>();

	@Environment(CLIENT)
	@ApiStatus.Internal
	public void initialize(String entrypointKey) {
		List<EntrypointContainer<IConfigProvider>> modConfigs = FabricLoader.getInstance().getEntrypointContainers(entrypointKey, IConfigProvider.class);
		this.configs.putAll(modConfigs.stream().map(entrypoint -> {
			String modID = entrypoint.getProvider().getMetadata().getId();
			IConfigProvider modConfig = entrypoint.getEntrypoint();

			return Pair.of(modID, ImmutableList.copyOf(modConfig.getConfigs()));
		}).collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond)));

		this.stream().forEach(ConfigFileManager::loadTo);
		this.stream().forEach(ConfigFileManager::saveFrom);
	}

	public Stream<IConfigContainer> stream() {
		return this.configs.values().stream()
				.flatMap(ImmutableCollection::stream);
	}
}
