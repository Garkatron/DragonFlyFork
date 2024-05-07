package useless.dragonfly.debug.testentity.Zombie;

import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.TessellatorBase;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import useless.dragonfly.model.entity.BenchEntityModel;

public class RenderZombieTest extends LivingRenderer<EntityZombieTest> {
	protected BenchEntityModel modelBench;

	public RenderZombieTest(BenchEntityModel modelbase, float shadowSize) {
		super(modelbase, shadowSize);
		this.modelBench = modelbase;
	}

	@Override
	protected void renderEquippedItems(EntityZombieTest entity, float f) {
		ItemStack itemstack = entity.getHeldItem();
		if (itemstack != null) {
			if (!this.modelBench.getIndexBones().isEmpty() && this.modelBench.getIndexBones().containsKey("RightArm")) {
				GL11.glPushMatrix();
				this.modelBench.postRender(this.modelBench.getIndexBones().get("RightArm"), 0.0625f);
				ItemModelDispatcher.getInstance().getDispatch(itemstack).renderItemThirdPerson(TessellatorBase.instance, renderDispatcher.itemRenderer, entity, itemstack, true);
				GL11.glPopMatrix();
			}
		}
	}
}
