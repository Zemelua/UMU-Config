package io.github.zemelua.umu_config.config.value.resourcepack;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.config.value.BooleanConfigValue;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.util.Identifier;

public abstract class AbstractResourcePackConfigValue extends BooleanConfigValue {
	private final String packName;

	public AbstractResourcePackConfigValue(Identifier ID, Boolean defaultValue, String packName) {
		super(ID, defaultValue);
		this.packName = packName;
	}

	protected abstract ResourcePackManager getResourcePackManager();

	/**
	 * ビルトインリソースはコンフィグファイルに保存せず、{@link net.minecraft.resource.ResourcePackManager} を保存先として
	 * 扱います。したがって、セーブ/ロードのタイミングで、随時クライアントインスタンスから取得し、セーブ/ロードを行います。
	 */
	@Override
	public void saveTo(JsonObject fileJson) {
		ResourcePackManager resourcePackManager = this.getResourcePackManager();

		if (resourcePackManager.hasProfile(this.packName)) {
			if (this.value) {
				resourcePackManager.enable(this.packName);
			} else {
				resourcePackManager.disable(this.packName);
			}
		}
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		ResourcePackManager resourcePackManager = this.getResourcePackManager();

		if (resourcePackManager.hasProfile(this.packName)) {
			this.value = resourcePackManager.getEnabledNames().contains(this.packName);
		}
	}
}
