package io.github.zemelua.umu_config.api.config.value.reference;

import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.api.client.gui.AbstractConfigScreen;
import io.github.zemelua.umu_config.api.client.gui.entry.AbstractConfigEntry;
import io.github.zemelua.umu_config.api.client.gui.entry.BooleanConfigEntry;
import io.github.zemelua.umu_config.api.config.value.IBooleanConfigValue;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.resource.loader.ModNioResourcePack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.stream.Collectors;

import static net.fabricmc.api.EnvType.*;

public abstract class AbstractResourcePackConfigValue extends AbstractReferenceConfigValue<Boolean> implements IBooleanConfigValue {
	protected final Identifier ID;
	protected final String packName;

	protected Boolean value;

	public AbstractResourcePackConfigValue(Identifier ID, String packName) {
		this.ID = ID;
		this.packName = packName;
		this.value = this.getDefaultValue();
	}

	protected abstract ResourcePackManager getResourcePackManager();

	@Override
	public Boolean getValue() {
		return this.value;
	}

	@Override
	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	@SuppressWarnings({"UnstableApiUsage", "DataFlowIssue"})
	public Boolean getDefaultValue() {
		ResourcePackManager resourcePackManager = this.getResourcePackManager();

		if (resourcePackManager.hasProfile(this.packName)) {
			if (resourcePackManager.getProfile(this.packName).createResourcePack() instanceof ModNioResourcePack modPack) {
				return modPack.getActivationType().isEnabledByDefault();
			}

			return resourcePackManager.getProfile(this.packName).isAlwaysEnabled();
		}

		return false;
	}

	@Override
	public Identifier getID() {
		return this.ID;
	}

	/**
	 * ビルトインリソースはコンフィグファイルに保存せず、{@link net.minecraft.resource.ResourcePackManager} を保存先として
	 * 扱います。したがって、セーブ/ロードのタイミングで、随時クライアントインスタンスから取得し、セーブ/ロードを行います。
	 */
	@Override
	public void save() {
		ResourcePackManager resourcePackManager = this.getResourcePackManager();
		GameOptions gameOptions = MinecraftClient.getInstance().options;

		UMUConfig.LOGGER.info(resourcePackManager.getProfiles().stream().map(ResourcePackProfile::getName).collect(Collectors.toList()));

		if (resourcePackManager.hasProfile(this.packName)) {
			if (this.value && resourcePackManager.enable(this.packName)) {
				gameOptions.refreshResourcePacks(resourcePackManager);

				UMUConfig.LOGGER.info("Enabled resource pack: " + this.packName);
			} else if (resourcePackManager.disable(this.packName)) {
				gameOptions.refreshResourcePacks(resourcePackManager);

				UMUConfig.LOGGER.info("Disabled resource pack: " + this.packName);
			}
		}
	}

	@Override
	public void load() {
		ResourcePackManager resourcePackManager = this.getResourcePackManager();

		if (resourcePackManager.hasProfile(this.packName)) {
			this.value = resourcePackManager.getEnabledNames().contains(this.packName);
		}
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		sendNBT.putBoolean(this.ID.getPath(), this.value);
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		if (receivedNBT.contains(this.ID.getPath())) {
			this.value = receivedNBT.getBoolean(this.ID.getPath());
		}
	}

	@Environment(CLIENT)
	@Override
	public AbstractConfigEntry createEntry(AbstractConfigScreen.ValueListWidget parent, int indent, boolean readOnly) {
		return new BooleanConfigEntry<>(this, indent, readOnly);
	}

	@Environment(CLIENT)
	@Override
	public Text getValueText(Boolean value) {
		return value ? ScreenTexts.ON : ScreenTexts.OFF;
	}
}
