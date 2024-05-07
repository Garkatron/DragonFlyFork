package useless.dragonfly.debug.testentity.Warden;

import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

public class WardenRenderer extends LivingRenderer<EntityWarden> {
	public WardenRenderer(ModelBase modelbase, float shadowSize) {
		super(modelbase, shadowSize);
	}
	public void doRenderPreview(Tessellator tessellator, EntityWarden entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glScalef(0.65f, 0.65f, 0.65f);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.doRender(tessellator, entity, x, y, z, yaw, partialTick);
	}
}
