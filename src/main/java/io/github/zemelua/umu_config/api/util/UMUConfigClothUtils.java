package io.github.zemelua.umu_config.api.util;

import io.github.zemelua.umu_config.api.config.category.IConfigCategory;
import io.github.zemelua.umu_config.api.config.container.IConfigContainer;
import io.github.zemelua.umu_config.api.config.value.IConfigValue;
import io.github.zemelua.umu_config.api.config.value.IEnumConfigValue;
import io.github.zemelua.umu_config.api.config.value.IRangedConfigValue;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.impl.builders.*;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.util.Optional;

public final class UMUConfigClothUtils {
	public static ConfigCategory getOrCreateCategory(ConfigBuilder builder, IConfigContainer container) {
		ConfigCategory category = builder.getOrCreateCategory(container.getName());

		container.getElements().forEach(e -> {
			if (e instanceof IConfigCategory umuCategory) {
				category.addEntry(subCategory(builder, umuCategory).build());
			} else if (e instanceof IConfigValue<?> v) {
				auto(builder, v).ifPresent(category::addEntry);
			}
		});

		return category;
	}

	public static SubCategoryBuilder subCategory(ConfigBuilder builder, IConfigCategory category) {
		SubCategoryBuilder subCategoryBuilder = builder.entryBuilder().startSubCategory(createCategoryName(category));
		category.getElements().forEach(e -> {
			if (e instanceof IConfigCategory f) {
				subCategoryBuilder.add(subCategory(builder, f).build());
			} else if (e instanceof IConfigValue<?> v) {
				auto(builder, v).ifPresent(subCategoryBuilder::add);
			}
		});

		return subCategoryBuilder;
	}

	@SuppressWarnings("unchecked")
	public static Optional<AbstractConfigListEntry<?>> auto(ConfigBuilder builder, IConfigValue<?> value) {
		if (value.getValue() instanceof Boolean) {
			return Optional.of(toggle(builder, (IConfigValue<Boolean>) value).build());
		} else if (value.getValue() instanceof Integer) {
			return Optional.of(slider(builder, (IRangedConfigValue<Integer>) value).build());
		} else if (value.getValue() instanceof Enum<?>) {
			return Optional.of(selector(builder, (IEnumConfigValue<?>) value).build());
		}

		return Optional.empty();
	}

	public static BooleanToggleBuilder toggle(ConfigBuilder builder, IConfigValue<Boolean> config) {
		return builder.entryBuilder().startBooleanToggle(createName(config), config.getValue())
				.setDefaultValue(config.getDefaultValue())
				.setTooltip(createTooltip(config))
				.setSaveConsumer(config::setValue);
	}

	@Deprecated
	public static IntSliderBuilder slider(ConfigBuilder builder, IRangedConfigValue<Integer> config) {
		return builder.entryBuilder().startIntSlider(createName(config), config.getValue(), config.getMinValue(), config.getMaxValue())
				.setDefaultValue(config.getDefaultValue())
				.setTooltip(createTooltip(config))
				.setSaveConsumer(config::setValue);
	}

	public static IntFieldBuilder field(ConfigBuilder builder, IRangedConfigValue<Integer> config) {
		return builder.entryBuilder().startIntField(createName(config), config.getValue())
				.setMin(config.getMinValue())
				.setMax(config.getMaxValue())
				.setDefaultValue(config.getDefaultValue())
				.setTooltip(createTooltip(config))
				.setSaveConsumer(config::setValue);
	}

	public static <T extends Enum<T>> EnumSelectorBuilder<T> selector(ConfigBuilder builder, IEnumConfigValue<T> config) {
		return builder.entryBuilder().startEnumSelector(createName(config), config.getEnumClass(), config.getValue())
				.setDefaultValue(config.getDefaultValue())
				.setTooltip(createTooltip(config))
				.setSaveConsumer(config::setValue);
	}

	private static Text createName(IConfigValue<?> config) {
		return Text.translatable(Util.createTranslationKey("config.value", config.getID()));
	}

	private static Text createCategoryName(IConfigCategory category) {
		return Text.translatable(Util.createTranslationKey("config.category", category.getID()));
	}

	private static Text createTooltip(IConfigValue<?> config) {
		return Text.translatable(Util.createTranslationKey("config.value.tooltip", config.getID()));
	}
}
