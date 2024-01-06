package io.github.zemelua.umu_config.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.zemelua.umu_config.UMUConfig;
import io.github.zemelua.umu_config.api.util.UMUConfigClothUtils;

public class ModMenuImpl implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
//			ConfigBuilder builder = ConfigBuilder.create();
//			ConfigEntryBuilder entryBuilder = builder.entryBuilder();
//			GameOptions options = MinecraftClient.getInstance().options;
//
//			SubCategoryBuilder subCategoryBuilder = entryBuilder.startSubCategory(Text.literal("subCategory"));
//			subCategoryBuilder.add(entryBuilder.fillKeybindingField(Text.translatable(options.dropKey.getTranslationKey()), options.dropKey).build());
//			subCategoryBuilder.add(entryBuilder.startSubCategory(Text.literal("sub2"))
//					.setExpanded(true)
//					.build());
//
//			builder.getOrCreateCategory(Text.literal("general"))
//					.addEntry(UMUConfigClothUtils.toggle(builder, ModClientConfigs.TEST_BOOLEAN).build())
//					.addEntry(UMUConfigClothUtils.field(builder, ModClientConfigs.TEST_INTEGER).build());
//			builder.getOrCreateCategory(Text.literal("additional"))
//					.addEntry(UMUConfigClothUtils.selector(builder, ModClientConfigs.TEST_ENUM).build())
//					.addEntry(subCategoryBuilder.build())
//					.addEntry(entryBuilder.startTextDescription(Text.literal("text")).build());

//			UMUConfigClothUtils.getOrCreateCategory(builder, ModClientConfigs.CONFIG);
//
//			builder.setParentScreen(parent)
//					.setTitle(ModClientConfigs.CONFIG.getName())
//					.setSavingRunnable(() -> {
//						ConfigFileManager.saveFrom(ModClientConfigs.CONFIG);
//					});



			return UMUConfigClothUtils.screen(UMUConfig.MOD_ID, parent).build();
		};
	}
}
