package io.github.zemelua.umu_config.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import io.github.zemelua.umu_config.ConfigHandler;
import io.github.zemelua.umu_config.client.gui.ConfigScreen;
import io.github.zemelua.umu_config.client.gui.ConfigsScreen;
import io.github.zemelua.umu_config.config.container.IConfigContainer;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static net.fabricmc.api.EnvType.*;

public final class ConfigManager {
	@NotNull private static ImmutableMap<String, ImmutableList<IConfigContainer>> CONFIGS = ImmutableMap.of("dummy", ImmutableList.of());

	@Internal
	public static void initialize() {
		List<EntrypointContainer<IConfigProvider>> modConfigs = FabricLoader.getInstance().getEntrypointContainers("umu-config", IConfigProvider.class);
		CONFIGS = modConfigs.stream().map(entrypoint -> {
			String modID = entrypoint.getProvider().getMetadata().getId();
			IConfigProvider modConfig = entrypoint.getEntrypoint();

			return Pair.of(modID, ImmutableList.copyOf(modConfig.getConfigs()));
		}).collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));

		stream().forEach(ConfigHandler::loadTo);
		stream().forEach(ConfigHandler::saveFrom);
	}

	@Environment(CLIENT)
	public static Optional<Screen> openConfigScreen(Screen parent, String modID) {
		List<IConfigContainer> config = byModID(modID);
		if (config.isEmpty()) return Optional.empty();

		if (config.size() == 1) {
			return Optional.of(new ConfigScreen(parent, config.get(0)));
		} else {
			return Optional.of(new ConfigsScreen(parent, modID));
		}
	}

	public static List<IConfigContainer> byModID(String modID) {
		return CONFIGS.get(modID);
	}

	public static Optional<IConfigContainer> byName(String name) {
		return stream()
				.filter(config -> config.getName().equals(name))
				.findFirst();
	}

	public static Stream<IConfigContainer> stream() {
		return CONFIGS.values().stream()
				.flatMap(Collection::stream);
	}
}
