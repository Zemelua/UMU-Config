package io.github.zemelua.umu_config.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.zemelua.umu_config.api.util.UMUConfigClothUtils;
import io.github.zemelua.umu_config.config.ConfigFileManager;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.text.Text;

public class ModMenuImpl implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create();
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();

			SubCategoryBuilder subCategoryBuilder = entryBuilder.startSubCategory(Text.literal("Additional"));
			subCategoryBuilder.add(UMUConfigClothUtils.selector(builder, ModClientConfigs.TEST_ENUM).build());

			builder.getOrCreateCategory(Text.literal("UMU Config Client"))
					.addEntry(UMUConfigClothUtils.toggle(builder, ModClientConfigs.TEST_BOOLEAN).build())
					.addEntry(UMUConfigClothUtils.field(builder, ModClientConfigs.TEST_INTEGER).build())
					.addEntry(subCategoryBuilder.build());


			builder.setParentScreen(parent)
					.setTitle(ModClientConfigs.CONFIG.getName())
					.setSavingRunnable(() -> ConfigFileManager.saveFrom(ModClientConfigs.CONFIG));

			return builder.build();
		};
	}
}
