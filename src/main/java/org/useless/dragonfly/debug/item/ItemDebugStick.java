package org.useless.dragonfly.debug.item;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.useless.dragonfly.DragonFly;
import org.useless.dragonfly.helper.ModelHelper;
import org.useless.dragonfly.model.block.data.ModelData;
import org.useless.dragonfly.model.block.processed.ModernBlockModel;
import org.useless.dragonfly.utilities.NamespaceId;

import static org.useless.dragonfly.helper.ModelHelper.modelDataFiles;

public class ItemDebugStick extends Item {
	public ItemDebugStick(String name, int id) {
		super(name,id);
	}
	@Override
	public boolean onUseItemOnBlock(ItemStack stack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		int meta = world.getBlockMetadata(blockX, blockY, blockZ);
		int id = world.getBlockId(blockX, blockY, blockZ);
		if (player.isSneaking()){
			meta--;
		} else {
			meta++;
		}
		meta &= 0xFF;
		world.setBlockAndMetadataWithNotify(blockX, blockY, blockZ, id, meta);
		return true;
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, EntityPlayer entityplayer) {

		ModernBlockModel model = ModelHelper.getOrCreateBlockModel("dragonfly","block/testblock3");
		//ModelData modelData = ModelHelper.loadBlockModel(NamespaceId.idFromString("dragonfly:block/testblock3"));

		//modelData.displayData.currentDisplayMode="head";
		//model.refreshModel();

		System.out.println(model.currentDisplayMode);
		switch (model.currentDisplayMode) {
			case "":
				model.currentDisplayMode = "ground"; // Cambiar a 'ground' inicialmente
				break;
			case "ground":
				model.currentDisplayMode = "head"; // Cambiar a 'head' en el siguiente uso
				break;
			case "head":
				model.currentDisplayMode = "firstperson_righthand"; // Cambiar a 'firstperson_righthand'
				break;
			case "firstperson_righthand":
				model.currentDisplayMode = "thirdperson_righthand"; // Cambiar a 'thirdperson_righthand'
				break;
			case "thirdperson_righthand":
				model.currentDisplayMode = "gui"; // Cambiar a 'gui'
				break;
			case "gui":
				model.currentDisplayMode = "ground"; // Volver a 'ground'
				break;
			default:
				model.currentDisplayMode = "ground"; // Valor por defecto
				break;
		}
		return super.onUseItem(itemstack, world, entityplayer);
	}
}
