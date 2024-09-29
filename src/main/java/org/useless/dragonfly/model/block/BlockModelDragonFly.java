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
				// Inicializar escala, offset y rotación con valores de displayData
				xScale = (float) displayData.scale[2] * 4;
				yScale = (float) displayData.scale[1] * 4;
				zScale = (float) displayData.scale[0] * 4;

				xOffset = 0.5f * xScale;
				yOffset = 0.5f * yScale;
				zOffset = 0.5f * zScale;

				xOffset -= (float) displayData.translation[2] / 16f;
				yOffset -= (float) displayData.translation[1] / 16f;
				zOffset -= (float) displayData.translation[0] / 16f;

				xRot = (float) displayData.rotation[0];
				yRot = (float) displayData.rotation[1];
				zRot = (float) displayData.rotation[2];

				// Sumar valores de customDisplayData si están presentes
				if (customDisplayData!=null && customDisplayData.getGround() != null) {
					PositionData groundData = customDisplayData.getGround();
					xScale += (float) groundData.scale[2] * 4;
					yScale += (float) groundData.scale[1] * 4;
					zScale += (float) groundData.scale[0] * 4;

					xOffset += (float) groundData.translation[2] / 16f;
					yOffset += (float) groundData.translation[1] / 16f;
					zOffset += (float) groundData.translation[0] / 16f;

					xRot += (float) groundData.rotation[0];
					yRot += (float) groundData.rotation[1];
					zRot += (float) groundData.rotation[2];
				}
				break;

			case "head":
				GL11.glFrontFace(GL11.GL_CW);
				// Inicializar escala, offset y rotación con valores de displayData
				xScale = (float) displayData.scale[0];
				yScale = (float) displayData.scale[1];
				zScale = (float) displayData.scale[2];

				xOffset = 0.5f * xScale;
				yOffset = 0.5f * yScale;
				zOffset = 0.5f * zScale;

				xOffset -= (float) displayData.translation[0] / 16f;
				yOffset -= (float) displayData.translation[1] / 16f;
				zOffset -= (float) displayData.translation[2] / 16f;

				xRot = (float) displayData.rotation[0];
				yRot = (float) displayData.rotation[1] + 180;
				zRot = (float) displayData.rotation[2];

				// Sumar valores de customDisplayData si están presentes
				if (customDisplayData!=null && customDisplayData.getHead() != null) {
					PositionData headData = customDisplayData.getHead();
					xScale += (float) headData.scale[0];
					yScale += (float) headData.scale[1];
					zScale += (float) headData.scale[2];

					xOffset += (float) headData.translation[0] / 16f;
					yOffset += (float) headData.translation[1] / 16f;
					zOffset += (float) headData.translation[2] / 16f;

					xRot += (float) headData.rotation[0];
					yRot += (float) headData.rotation[1] + 180;
					zRot += (float) headData.rotation[2];
				}
				break;

			case "firstperson_righthand":
				// Inicializar escala, offset y rotación con valores de displayData
				xScale = (float) displayData.scale[2] * 2.5f;
				yScale = (float) displayData.scale[1] * 2.5f;
				zScale = (float) displayData.scale[0] * 2.5f;

				xOffset = 0.5f * xScale;
				yOffset = 0.5f * yScale;
				zOffset = 0.5f * zScale;

				xOffset -= (float) displayData.translation[2] / 8f;
				yOffset -= (float) displayData.translation[1] / 8f;
				zOffset -= (float) displayData.translation[0] / 8f;

				xRot = (float) displayData.rotation[0];
				yRot = (float) displayData.rotation[1] + 45;
				zRot = (float) displayData.rotation[2];

				// Sumar valores de customDisplayData si están presentes
				if (customDisplayData!=null && customDisplayData.getFirstPersonRightHand() != null) {
					PositionData firstPersonData = customDisplayData.getFirstPersonRightHand();
					xScale += (float) firstPersonData.scale[2] * 2.5f;
					yScale += (float) firstPersonData.scale[1] * 2.5f;
					zScale += (float) firstPersonData.scale[0] * 2.5f;

					xOffset += (float) firstPersonData.translation[2] / 8f;
					yOffset += (float) firstPersonData.translation[1] / 8f;
					zOffset += (float) firstPersonData.translation[0] / 8f;

					xRot += (float) firstPersonData.rotation[0];
					yRot += (float) firstPersonData.rotation[1] + 45;
					zRot += (float) firstPersonData.rotation[2];
				}
				break;

			case "thirdperson_righthand":
				GL11.glFrontFace(GL11.GL_CW);
				// Inicializar rotación, escala y traslación con valores de displayData
				double[] rotation = displayData.rotation;;
				double[] scale = displayData.scale;
				double[] translation = displayData.translation;

				// Sumar valores de customDisplayData si están presentes
				if (customDisplayData!=null && customDisplayData.getThirdPersonRightHand() != null) {
					PositionData thirdPersonData = customDisplayData.getThirdPersonRightHand();
					rotation[0] += thirdPersonData.rotation[0];
					rotation[1] += thirdPersonData.rotation[1];
					rotation[2] += thirdPersonData.rotation[2];

					scale[0] += thirdPersonData.scale[0];
					scale[1] += thirdPersonData.scale[1];
					scale[2] += thirdPersonData.scale[2];

					translation[0] += thirdPersonData.translation[0];
					translation[1] += thirdPersonData.translation[1];
					translation[2] += thirdPersonData.translation[2];
				}

				float scaleFactor = 8f / 3;

				// Usar escala
				xScale = (float) scale[2] * scaleFactor; // Escala en el eje X
				yScale = (float) scale[1] * scaleFactor; // Escala en el eje Y
				zScale = (float) scale[0] * scaleFactor; // Escala en el eje Z

				// Ajustes de Offset
				xOffset = 0.5f * xScale; // Offset inicial en X
				yOffset = 0.5f * yScale; // Offset inicial en Y
				zOffset = 0.5f * zScale; // Offset inicial en Z

				// Sumar traducción
				xOffset += (float) translation[2] / 16f; // Sumar traducción en X
				yOffset += (float) translation[1] / 16f; // Sumar traducción en Y
				zOffset += (float) translation[0] / 16f; // Sumar traducción en Z

				// Rotaciones
				xRot = (float) -rotation[2] + 180; // Mantener esto igual si necesitas invertir
				yRot = (float) rotation[1] + 45; // Sumar 45 grados
				zRot = (float) -rotation[0] - 100; // Mantener esto igual si necesitas invertir

				break;

			case "gui":
			default:
				// Inicializar escala, offset y rotación con valores de displayData
				xScale = (float) displayData.scale[2] * 1.6f;
				yScale = (float) displayData.scale[1] * 1.6f;
				zScale = (float) displayData.scale[0] * 1.6f;

				xOffset = 0.5f * xScale;
				yOffset = 0.5f * yScale;
				zOffset = 0.5f * zScale;

				xOffset -= (float) displayData.translation[2] / 16f;
				yOffset -= (float) displayData.translation[1] / 16f;
				zOffset -= (float) displayData.translation[0] / 16f;

				xRot = (float) displayData.rotation[0];
				yRot = (float) displayData.rotation[1];
				zRot = (float) displayData.rotation[2];

				// Sumar valores de customDisplayData si están presentes
				if (customDisplayData!=null && customDisplayData.getGui() != null) {
					PositionData guiData = customDisplayData.getGui();
					xScale += (float) guiData.scale[2] * 1.6f;
					yScale += (float) guiData.scale[1] * 1.6f;
					zScale += (float) guiData.scale[0] * 1.6f;

					xOffset += (float) guiData.translation[2] / 16f;
					yOffset += (float) guiData.translation[1] / 16f;
					zOffset += (float) guiData.translation[0] / 16f;

					xRot += (float) guiData.rotation[0];
					yRot += (float) guiData.rotation[1];
					zRot += (float) guiData.rotation[2];
				}
				break;
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
