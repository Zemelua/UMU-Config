package io.github.zemelua.umu_config.config.value.reference;

import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.config.value.IConfigValue;

/**
 * このクラスの子クラスは、コンフィグjson以外の方法で保存/読み込みを行うコンフィグオブジェクトです。
 * 本来、コンフィグjsonを使用する場合と同様に、外部から保存/読み込み先を引き渡すべきですが、ベストプラクティスが思いつかないので、
 * 保存/読み込み先を都度クラス内から取得し使用します。したがって、引き渡されるjsonオブジェクトは使用されません。
 */
public abstract class AbstractReferenceConfigValue<T> implements IConfigValue<T> {
	protected abstract void save();
	protected abstract void load();

	@Override
	public final void saveTo(JsonObject fileJson) {
		this.save();
	}

	@Override
	public final void loadFrom(JsonObject fileJson) {
		this.load();
	}
}
