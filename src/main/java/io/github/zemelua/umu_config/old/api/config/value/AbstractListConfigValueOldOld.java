package io.github.zemelua.umu_config.old.api.config.value;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.zemelua.umu_config.api.client.gui.AbstractConfigScreen;
import io.github.zemelua.umu_config.old.api.client.gui.entry.AbstractConfigEntry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.StreamSupport;

public abstract class AbstractListConfigValueOldOld<E> extends AbstractConfigValueOldOld<List<E>> {
	private final int nbtType;

	public AbstractListConfigValueOldOld(Identifier ID, int nbtType, E... defaultValues) {
		super(ID, ImmutableList.copyOf(defaultValues));

		this.nbtType = nbtType;
	}

	protected abstract JsonElement convertToJson(E value);
	protected abstract E convertFromJson(JsonElement json);
	protected abstract NbtElement convertToNBT(E value);
	protected abstract E convertFromNBT(NbtElement nbt);

	@Override
	public void saveTo(JsonObject fileJson) {
		JsonArray jsonArray = new JsonArray();
		this.value.forEach(v -> jsonArray.add(this.convertToJson(v)));
		fileJson.add(this.ID.getPath(), jsonArray);
	}

	@Override
	public void loadFrom(JsonObject fileJson) {
		List<E> newList = StreamSupport.stream(fileJson.get(this.ID.getPath()).getAsJsonArray().spliterator(), false)
				.map(this::convertFromJson)
				.toList();

		this.value = ImmutableList.copyOf(newList);
	}

	@Override
	public void saveTo(NbtCompound sendNBT) {
		NbtList nbtList = new NbtList();
		this.value.forEach(v -> nbtList.add(this.convertToNBT(v)));
		sendNBT.put(this.ID.getPath(), nbtList);
	}

	@Override
	public void loadFrom(NbtCompound receivedNBT) {
		List<E> newList = receivedNBT.getList(this.ID.getPath(), this.nbtType).stream()
				.map(this::convertFromNBT)
				.toList();

		this.value = ImmutableList.copyOf(newList);
	}

	@Override
	public AbstractConfigEntry createEntry(AbstractConfigScreen.ValueListWidget parent, int indent, boolean readOnly) {
		return null;
	}

	@Override
	public Text getValueText(List<E> value) {
		return Text.of(this.value.toString());
	}
}
