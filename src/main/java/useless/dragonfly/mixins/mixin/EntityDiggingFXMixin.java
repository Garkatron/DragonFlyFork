package useless.dragonfly.mixins.mixin;

import net.minecraft.client.entity.fx.EntityDiggingFX;
import net.minecraft.client.entity.fx.EntityFX;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import useless.dragonfly.model.block.BlockModelDragonFly;
import useless.dragonfly.registries.TextureRegistry;

@Mixin(value = EntityDiggingFX.class, remap = false)
public class EntityDiggingFXMixin extends EntityFX {
	@Shadow
	private Block block;

	public EntityDiggingFXMixin(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}
	@Inject(method = "func_4041_a(III)Lnet/minecraft/client/entity/fx/EntityDiggingFX;", at = @At(value = "HEAD"))
	private void particleFromModel(int i, int j, int k, CallbackInfoReturnable<EntityDiggingFX> cir){
		if (world.getBlock(i, j, k) == block){
			BlockModel model = BlockModelDispatcher.getInstance().getDispatch(block);
			int meta = world.getBlockMetadata(i, j, k);
			int tex;
			if (model instanceof BlockModelDragonFly){
				tex = TextureRegistry.getIndexOrDefault(((BlockModelDragonFly) model).getModelsFromState(block, i, j, k, true)[0].model.getTexture("particle"), block.getBlockTextureFromSideAndMetadata(Side.BOTTOM, meta));
			} else {
				tex = block.getBlockTextureFromSideAndMetadata(Side.BOTTOM, meta);
			}
			particleTextureIndex = tex;
		}
	}
}
