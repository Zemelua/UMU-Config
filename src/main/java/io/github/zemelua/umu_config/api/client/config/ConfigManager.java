package io.github.zemelua.umu_config.api.client.config;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import io.github.zemelua.umu_config.api.config.IConfigProvider;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import io.github.zemelua.umu_config.config.ConfigFileManager;
import io.github.zemelua.umu_config.network.NetworkHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.stream.Stream;

public final class ConfigManager {
	public static final ConfigManager INSTANCE = new ConfigManager();

	private final Map<String, ImmutableList<IConfigContainer>> configs = new HashMap<>();

	@Environment(EnvType.CLIENT)
	@ApiStatus.Internal
	public void initialize(String entrypointKey) {
		List<EntrypointContainer<IConfigProvider>> modConfigs = FabricLoader.getInstance().getEntrypointContainers(entrypointKey, IConfigProvider.class);
		this.configs.putAll(modConfigs.stream().map(entrypoint -> {
			String modID = entrypoint.getProvider().getMetadata().getId();
			IConfigProvider modConfig = entrypoint.getEntrypoint();

			return Pair.of(modID, ImmutableList.copyOf(modConfig.getConfigs()));
		}).collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond)));

		this.reloadAll();
	}

	public void loadAll() {
		this.stream().forEach(ConfigFileManager::loadTo);
	}

	public void saveAll() {
		this.stream().forEach(ConfigFileManager::saveFrom);
	}

	public void reloadAll() {
		this.loadAll();
		this.saveAll();
	}

	public Stream<IConfigContainer> stream() {
		return this.configs.values().stream()
				.flatMap(ImmutableCollection::stream);
	}

	public Optional<IConfigContainer> fromID(Identifier id) {
		return this.stream()
				.filter(c -> c.getID().equals(id))
				.findFirst();
	}

	public Stream<IConfigContainer> fromModID(String modID) {
		return Optional.ofNullable(this.configs.get(modID)).stream()
				.flatMap(Collection::stream);
	}

	public static void sendToClient(ServerPlayerEntity player, IConfigContainer config) {
		PacketByteBuf packet = PacketByteBufs.create();
		NbtCompound values = new NbtCompound();
		config.saveTo(values);
		packet.writeIdentifier(config.getID());
		packet.writeNbt(values);

		ServerPlayNetworking.send(player, NetworkHandler.CHANNEL_SYNC_CONFIG_TO_CLIENT, packet);
	}
}
