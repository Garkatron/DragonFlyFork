package org.useless.dragonfly.debug;

import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelPlayer;
import turniplabs.halplibe.helper.EntityHelper;
import org.useless.dragonfly.debug.testentity.Dragon.DragonModel;
import org.useless.dragonfly.debug.testentity.Dragon.DragonRenderer;
import org.useless.dragonfly.debug.testentity.Dragon.EntityDragon;
import org.useless.dragonfly.debug.testentity.HTest.EntityHTest;
import org.useless.dragonfly.debug.testentity.Warden.EntityWarden;
import org.useless.dragonfly.debug.testentity.Warden.WardenModel;
import org.useless.dragonfly.debug.testentity.Warden.WardenRenderer;
import org.useless.dragonfly.debug.testentity.Zombie.EntityZombieTest;
import org.useless.dragonfly.debug.testentity.Zombie.RenderZombieTest;
import org.useless.dragonfly.debug.testentity.Zombie.ZombieModelTest;
import org.useless.dragonfly.helper.AnimationHelper;
import org.useless.dragonfly.helper.ModelHelper;

import static org.useless.dragonfly.DragonFly.MOD_ID;

public class DebugEntities {
	public static void init(){
		EntityHelper.createEntity(EntityHTest.class, 1000, "ht", () -> new LivingRenderer<>(new ModelPlayer(1), 1));
		EntityHelper.createEntity(EntityZombieTest.class, 1000, "zt", () -> new RenderZombieTest(ModelHelper.getOrCreateEntityModel(MOD_ID, "zombie_test.json", ZombieModelTest.class), 0.5f));
		AnimationHelper.getOrCreateEntityAnimation(MOD_ID, "zombie_test.animation");
		EntityHelper.createEntity(EntityDragon.class, 1001, "dragon", () -> new DragonRenderer(ModelHelper.getOrCreateEntityModel(MOD_ID, "mod_dragon.json", DragonModel.class), 0.5f));
		EntityHelper.createEntity(EntityWarden.class, 1002, "warden", () -> new WardenRenderer(ModelHelper.getOrCreateEntityModel(MOD_ID, "warden.json", WardenModel.class), 0.5f));
		MobInfoRegistry.register(EntityWarden.class, "df.warden.name", "df.warden.desc", 20, 0, null);
	}
}
