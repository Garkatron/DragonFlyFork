package org.useless.dragonfly.model.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.stitcher.IconCoordinate;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.DragonFly;
import org.useless.dragonfly.helper.ModelHelper;
import org.useless.dragonfly.mixins.mixin.accessor.RenderBlocksAccessor;
import org.useless.dragonfly.model.block.data.DisplayData;
import org.useless.dragonfly.model.block.data.PositionData;
import org.useless.dragonfly.model.block.processed.BlockCube;
import org.useless.dragonfly.model.block.processed.BlockFace;
import org.useless.dragonfly.model.block.processed.ModernBlockModel;
import org.useless.dragonfly.model.blockstates.data.BlockstateData;
import org.useless.dragonfly.model.blockstates.data.ModelPart;
import org.useless.dragonfly.model.blockstates.data.VariantData;
import org.useless.dragonfly.model.blockstates.processed.MetaStateInterpreter;
import org.useless.dragonfly.utilities.NamespaceId;
import org.useless.dragonfly.utilities.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockModelDragonFly extends BlockModelStandard<Block> {
	public ModernBlockModel baseModel;
	public boolean render3d;
	public float renderScale;
	public BlockstateData blockstateData;
	public MetaStateInterpreter metaStateInterpreter;

	public BlockModelDragonFly(Block block, ModernBlockModel model, BlockstateData blockstateData, MetaStateInterpreter metaStateInterpreter, boolean render3d, float renderScale) {
		super(block);
		this.baseModel = model;
		this.render3d = render3d;
		this.renderScale = renderScale;
		this.blockstateData  = blockstateData;
		this.metaStateInterpreter = metaStateInterpreter;
	}

	@Override
	public boolean render(Tessellator tessellator, int x, int y, int z) {

		InternalModel[] models = getModelsFromState(block, x, y, z, false);
		boolean didRender = false;
        for (InternalModel model : models) {
            didRender |= BlockModelRenderer.renderModelNormal(tessellator, model.model, block, x, y, z, model.rotationX, -model.rotationY);
        }
		return didRender;
	}

	@Override
	public void renderBlockOnInventory(Tessellator tessellator, int metadata, float brightness, float alpha, @Nullable Integer lightmapCoordinate) {
        float xOffset;
		float yOffset;
		float zOffset;
		float xScale;
		float yScale;
		float zScale;
		float xRot;
		float yRot;
		float zRot;

		PositionData displayData = baseModel.getDisplayPosition(DragonFly.renderState);
		DisplayData customDisplayData = baseModel.displayData;

		switch (DragonFly.renderState) {
			case "ground":
				PositionData positionData = customDisplayData.getGround();

				// Comprobar si los valores son nulos, en cuyo caso usar los valores de displayData
				xScale = (positionData != null && positionData.scale != null) ? (float) positionData.scale[2] * 4 : (float) displayData.scale[2] * 4;
				yScale = (positionData != null && positionData.scale != null) ? (float) positionData.scale[1] * 4 : (float) displayData.scale[1] * 4;
				zScale = (positionData != null && positionData.scale != null) ? (float) positionData.scale[0] * 4 : (float) displayData.scale[0] * 4;

				xOffset = 0.5f * xScale;
				yOffset = 0.5f * yScale;
				zOffset = 0.5f * zScale;

				xOffset -= (positionData != null && positionData.translation != null) ? (float) positionData.translation[2] / 16f : (float) displayData.translation[2] / 16f;
				yOffset -= (positionData != null && positionData.translation != null) ? (float) positionData.translation[1] / 16f : (float) displayData.translation[1] / 16f;
				zOffset -= (positionData != null && positionData.translation != null) ? (float) positionData.translation[0] / 16f : (float) displayData.translation[0] / 16f;

				xRot = (positionData != null && positionData.rotation != null) ? (float) positionData.rotation[0] : (float) displayData.rotation[0];
				yRot = (positionData != null && positionData.rotation != null) ? (float) positionData.rotation[1] : (float) displayData.rotation[1];
				zRot = (positionData != null && positionData.rotation != null) ? (float) positionData.rotation[2] : (float) displayData.rotation[2];
				break;

			case "head":
				PositionData headPositionData = customDisplayData.getHead();

				xScale = (headPositionData != null && headPositionData.scale != null) ? (float) headPositionData.scale[0] : (float) displayData.scale[0];
				yScale = (headPositionData != null && headPositionData.scale != null) ? (float) headPositionData.scale[1] : (float) displayData.scale[1];
				zScale = (headPositionData != null && headPositionData.scale != null) ? (float) headPositionData.scale[2] : (float) displayData.scale[2];

				xOffset = 0.5f * xScale;
				yOffset = 0.5f * yScale;
				zOffset = 0.5f * zScale;

				xOffset -= (headPositionData != null && headPositionData.translation != null) ? (float) headPositionData.translation[0] / 16f : (float) displayData.translation[0] / 16f;
				yOffset -= (headPositionData != null && headPositionData.translation != null) ? (float) headPositionData.translation[1] / 16f : (float) displayData.translation[1] / 16f;
				zOffset -= (headPositionData != null && headPositionData.translation != null) ? (float) headPositionData.translation[2] / 16f : (float) displayData.translation[2] / 16f;

				xRot = (headPositionData != null && headPositionData.rotation != null) ? (float) headPositionData.rotation[0] : (float) displayData.rotation[0];
				yRot = (headPositionData != null && headPositionData.rotation != null) ? (float) headPositionData.rotation[1] : (float) displayData.rotation[1];
				zRot = (headPositionData != null && headPositionData.rotation != null) ? (float) headPositionData.rotation[2] : (float) displayData.rotation[2];
				break;

			// Los demás casos siguen el mismo patrón:
			case "firstperson_righthand":
				PositionData firstPersonRightHandPositionData = customDisplayData.getFirstPersonRightHand();

				xScale = (firstPersonRightHandPositionData != null && firstPersonRightHandPositionData.scale != null) ? (float) firstPersonRightHandPositionData.scale[2] * 2.5f : (float) displayData.scale[2] * 2.5f;
				yScale = (firstPersonRightHandPositionData != null && firstPersonRightHandPositionData.scale != null) ? (float) firstPersonRightHandPositionData.scale[1] * 2.5f : (float) displayData.scale[1] * 2.5f;
				zScale = (firstPersonRightHandPositionData != null && firstPersonRightHandPositionData.scale != null) ? (float) firstPersonRightHandPositionData.scale[0] * 2.5f : (float) displayData.scale[0] * 2.5f;

				xOffset = 0.5f * xScale;
				yOffset = 0.5f * yScale;
				zOffset = 0.5f * zScale;

				xOffset -= (firstPersonRightHandPositionData != null && firstPersonRightHandPositionData.translation != null) ? (float) firstPersonRightHandPositionData.translation[2] / 8f : (float) displayData.translation[2] / 8f;
				yOffset -= (firstPersonRightHandPositionData != null && firstPersonRightHandPositionData.translation != null) ? (float) firstPersonRightHandPositionData.translation[1] / 8f : (float) displayData.translation[1] / 8f;
				zOffset -= (firstPersonRightHandPositionData != null && firstPersonRightHandPositionData.translation != null) ? (float) firstPersonRightHandPositionData.translation[0] / 8f : (float) displayData.translation[0] / 8f;

				xRot = (firstPersonRightHandPositionData != null && firstPersonRightHandPositionData.rotation != null) ? (float) firstPersonRightHandPositionData.rotation[0] : (float) displayData.rotation[0];
				yRot = (firstPersonRightHandPositionData != null && firstPersonRightHandPositionData.rotation != null) ? (float) firstPersonRightHandPositionData.rotation[1] + 45 : (float) displayData.rotation[1] + 45;
				zRot = (firstPersonRightHandPositionData != null && firstPersonRightHandPositionData.rotation != null) ? (float) firstPersonRightHandPositionData.rotation[2] : (float) displayData.rotation[2];
				break;

			case "thirdperson_righthand":
				PositionData thirdPersonRightHandPositionData = customDisplayData.getThirdPersonRightHand();

				float scale = 8f / 3;
				xScale = (thirdPersonRightHandPositionData != null && thirdPersonRightHandPositionData.scale != null) ? (float) thirdPersonRightHandPositionData.scale[2] * scale : (float) displayData.scale[2] * scale;
				yScale = (thirdPersonRightHandPositionData != null && thirdPersonRightHandPositionData.scale != null) ? (float) thirdPersonRightHandPositionData.scale[1] * scale : (float) displayData.scale[1] * scale;
				zScale = (thirdPersonRightHandPositionData != null && thirdPersonRightHandPositionData.scale != null) ? (float) thirdPersonRightHandPositionData.scale[0] * scale : (float) displayData.scale[0] * scale;

				xOffset = 0.5f * xScale;
				yOffset = 0.5f * yScale;
				zOffset = 0.5f * zScale;

				xOffset -= (thirdPersonRightHandPositionData != null && thirdPersonRightHandPositionData.translation != null) ? (float) thirdPersonRightHandPositionData.translation[2] / 16f : (float) displayData.translation[2] / 16f;
				yOffset -= (thirdPersonRightHandPositionData != null && thirdPersonRightHandPositionData.translation != null) ? (float) thirdPersonRightHandPositionData.translation[1] / 16f : (float) displayData.translation[1] / 16f;
				zOffset -= (thirdPersonRightHandPositionData != null && thirdPersonRightHandPositionData.translation != null) ? (float) thirdPersonRightHandPositionData.translation[0] / 16f : (float) displayData.translation[0] / 16f;

				xRot = (thirdPersonRightHandPositionData != null && thirdPersonRightHandPositionData.rotation != null) ? (float) (-thirdPersonRightHandPositionData.rotation[2] + 180) : (float) (-displayData.rotation[2] + 180);
				yRot = (thirdPersonRightHandPositionData != null && thirdPersonRightHandPositionData.rotation != null) ? (float) thirdPersonRightHandPositionData.rotation[1] + 45 : (float) displayData.rotation[1] + 45;
				zRot = (thirdPersonRightHandPositionData != null && thirdPersonRightHandPositionData.rotation != null) ? (float) (-thirdPersonRightHandPositionData.rotation[0] - 100) : (float) (-displayData.rotation[0] - 100);
				break;

			case "gui":
				PositionData guiPositionData = customDisplayData.getGui();

				xScale = (guiPositionData != null && guiPositionData.scale != null) ? (float) guiPositionData.scale[2] * 1.6f : (float) displayData.scale[2] * 1.6f;
				yScale = (guiPositionData != null && guiPositionData.scale != null) ? (float) guiPositionData.scale[1] * 1.6f : (float) displayData.scale[1] * 1.6f;
				zScale = (guiPositionData != null && guiPositionData.scale != null) ? (float) guiPositionData.scale[0] * 1.6f : (float) displayData.scale[0] * 1.6f;

				xOffset = 0.5f * xScale;
				yOffset = 0.5f * yScale;
				zOffset = 0.5f * zScale;

				xOffset -= (guiPositionData != null && guiPositionData.translation != null) ? (float) guiPositionData.translation[2] / 16f : (float) displayData.translation[2] / 16f;
				yOffset -= (guiPositionData != null && guiPositionData.translation != null) ? (float) guiPositionData.translation[1] / 16f : (float) displayData.translation[1] / 16f;
				zOffset -= (guiPositionData != null && guiPositionData.translation != null) ? (float) guiPositionData.translation[0] / 16f : (float) displayData.translation[0] / 16f;

				xRot = (guiPositionData != null && guiPositionData.rotation != null) ? (float) guiPositionData.rotation[0] - 30 : (float) displayData.rotation[0] - 30;
				yRot = (guiPositionData != null && guiPositionData.rotation != null) ? (float) guiPositionData.rotation[1] + 45 : (float) displayData.rotation[1] + 45;
				zRot = (guiPositionData != null && guiPositionData.rotation != null) ? (float) guiPositionData.rotation[2] : (float) displayData.rotation[2];
				break;
			default:


		}




		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		GL11.glRotatef(yRot, 0, 1, 0);
		GL11.glRotatef(xRot, 1, 0, 0);
		GL11.glRotatef(zRot, 0, 0, 1);
		GL11.glTranslatef(-xOffset, -yOffset, -zOffset);
		GL11.glScalef(xScale, yScale, zScale);
		if (baseModel.blockCubes != null){
			tessellator.startDrawingQuads();
			GL11.glColor4f(brightness, brightness, brightness, 1);
			for (BlockCube cube: baseModel.blockCubes) {
				for (BlockFace face: cube.getFaces().values()) {
					tessellator.setNormal(face.getSide().getOffsetX(), face.getSide().getOffsetY(), face.getSide().getOffsetZ());
					if (LightmapHelper.isLightmapEnabled() && lightmapCoordinate != null){
						tessellator.setLightmapCoord(lightmapCoordinate);
					}
					float r = 1;
					float g = 1;
					float b = 1;
					if (face.useTint()){
						int color = BlockColorDispatcher.getInstance().getDispatch(block).getFallbackColor(metadata);
						r = (float)(color >> 16 & 0xFF) / 255.0f;
						g = (float)(color >> 8 & 0xFF) / 255.0f;
						b = (float)(color & 0xFF) / 255.0f;
					}
					BlockModelRenderer.renderModelFaceWithColor(tessellator, face, 0, 0, 0, r * brightness, g * brightness, b * brightness);
				}
			}
			tessellator.draw();
		}
		GL11.glDisable(GL11.GL_CULL_FACE); // Deleting this causes render issues on vanilla transparent blocks
		GL11.glTranslatef(xOffset, yOffset, zOffset);
	}

	@Override
	public boolean shouldItemRender3d() {
		return render3d;
	}

	@Override
	public float getItemRenderScale() {
		return renderScale;
	}
	@Override
	public IconCoordinate getParticleTexture(Side side, int meta) {
		if (baseModel.textureMap.containsKey("particle")){
			return TextureRegistry.getTexture(baseModel.getTexture("particle").toString());
		}
		return super.getParticleTexture(side, meta);
	}
	public InternalModel[] getModelsFromState(Block block, int x, int y, int z, boolean sourceFromWorld){
		if (blockstateData == null || metaStateInterpreter == null){
			return new InternalModel[]{new InternalModel(baseModel, 0, 0)};
		}
		RenderBlocksAccessor blocksAccessor = (RenderBlocksAccessor) BlockModelRenderer.getRenderBlocks();
		WorldSource blockSource;
		if (sourceFromWorld){
			blockSource = Minecraft.getMinecraft(Minecraft.class).theWorld; //world
		} else {
			blockSource = blocksAccessor.getBlockAccess(); // chunk cache
		}
		int meta = blockSource.getBlockMetadata(x,y,z);
		Random random = Utilities.getRandomFromPos(x, y, z);
		HashMap<String, String> blockStateList = metaStateInterpreter.getStateMap(blockSource, x, y, z, block, meta);
		if (blockstateData.variants != null){ // If model uses variant system
			return getModelVariant(blockStateList, random);
		}
		if (blockstateData.multipart != null){
			return getMultipartModel(blockStateList, random);
		}
		return new InternalModel[]{new InternalModel(baseModel, 0, 0)};
	}
	public InternalModel[] getModelVariant(HashMap<String, String> blockState, Random random){
		VariantData variantData = null;
		for (String stateString: blockstateData.variants.keySet()) {
			String[] conditions = stateString.split(",");
			HashMap<String, String> conditionMap = new HashMap<>();
			for (String condition : conditions){
				conditionMap.put(condition.split("=")[0], condition.split("=")[1]);
			}
			if (matchConditionsAND(blockState, conditionMap)){
				variantData = blockstateData.variants.get(stateString).getRandomModel(random);
				break;
			}
		}
		if (variantData == null) return new InternalModel[]{new InternalModel(baseModel, 0, 0)};


		return new InternalModel[]{new InternalModel(getModelFromKey(variantData.model), variantData.x, variantData.y)};
	}
	public InternalModel[] getMultipartModel(HashMap<String, String> blockState, Random random){
		List<InternalModel> modelsToRender = new ArrayList<>();
		for (ModelPart modelPart : blockstateData.multipart){
			if (modelPart.when == null || modelPart.when.match(blockState)){
				VariantData data = modelPart.getRandomModel(random);
				modelsToRender.add(new InternalModel(getModelFromKey(data.model), data.x, data.y));
			}
		}
		return modelsToRender.toArray(new InternalModel[0]);
	}
	public boolean matchConditionsAND(HashMap<String, String> blockState, HashMap<String, String> conditions){
		if (conditions == null){
			DragonFly.LOGGER.warn("conditions for model '" + baseModel.namespaceId + "' have returned null!");
			return false;
		}
		boolean stateMet = true;
		for (Map.Entry<String, String > entry: conditions.entrySet()) {
			String stateValue = blockState.get(entry.getKey());
			if (stateValue == null){
				DragonFly.LOGGER.warn("Could not find corresponding value for '" + entry.getKey() + "' in model '" + baseModel.namespaceId + "'!");
				stateMet = false;
				continue;
			}
			stateMet &= stateValue.equals(entry.getValue());
		}
		return stateMet;
	}
	public ModernBlockModel getModelFromKey(String key){
		String[] modelID = key.split(":");
		String namespace;
		String model;
		if (modelID.length < 2){
			namespace = NamespaceId.coreNamespaceId;
			model = modelID[0];
		} else {
			namespace = modelID[0];
			model = modelID[1];
		}
		return ModelHelper.getOrCreateBlockModel(namespace, model);
	}
}
