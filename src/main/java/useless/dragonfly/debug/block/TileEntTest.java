package useless.dragonfly.debug.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.PlayerSkinParser;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelPlayerSlim;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.block.entity.TileEntityFlag;
import org.lwjgl.opengl.GL11;

public class TileEntTest extends TileEntityRenderer<TileEntityFlag> {
	ModelBase model = new ModelPlayerSlim(0);
	@Override
	public void doRender(Tessellator tessellator, TileEntityFlag tileEntity, double x, double y, double z, float partialTick) {
		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		try {
			float scale = 0.0625f;
			GL11.glEnable(32826);
			GL11.glScalef(-1, -1, 1);
			GL11.glTranslatef(0.0f, -24.0f * scale - 0.0078125f, 0.0f);
			renderDispatcher.renderEngine.loadDownloadableTexture(Minecraft.getMinecraft(this).thePlayer.skinURL, "/mob/char/0.png", PlayerSkinParser.instance);
			GL11.glEnable(3008);
			model.render(0, 0, 1, 0, 0, scale);
			GL11.glDisable(32826);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}
}
