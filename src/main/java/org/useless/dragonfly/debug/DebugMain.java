package org.useless.dragonfly.debug;

import net.minecraft.client.render.item.model.ItemModelStandard;
import net.minecraft.client.render.stitcher.TextureRegistry;
import turniplabs.halplibe.helper.ItemHelper;
import org.useless.dragonfly.DragonFly;
import org.useless.dragonfly.debug.item.ItemDebugStick;

public class DebugMain {
	public static void init(){
		ItemHelper.createItem(DragonFly.MOD_ID, new ItemDebugStick("debug", 21000), (item) -> {
			ItemModelStandard model = new ItemModelStandard(item, "minecraft");
			model.icon = TextureRegistry.getTexture("minecraft:item/stick");
			return model;
		});
		DebugBlocks.init();
		DebugEntities.init();
//		StringBuilder builder = new StringBuilder();
//		for (String string: ModelHelper.modelDataFiles.keySet()) {
//			builder.append(string);
//			builder.append(Utilities.tabBlock(ModelHelper.modelDataFiles.get(string).toString(), 1));
//		}
//		DragonFly.LOGGER.info(builder.toString());
	}
}
