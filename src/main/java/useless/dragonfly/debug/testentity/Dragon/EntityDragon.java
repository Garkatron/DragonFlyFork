package useless.dragonfly.debug.testentity.Dragon;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.world.World;

public class EntityDragon extends EntityLiving {
	public EntityDragon(World world) {
		super(world);
	}
	@Override
	public void onLivingUpdate() {}
	@Override
	public String getEntityTexture() {
		return "/assets/dragonfly/textures/entity/dragontex2variant.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/dragonfly/textures/entity/dragontex2variant.png";
	}
}
