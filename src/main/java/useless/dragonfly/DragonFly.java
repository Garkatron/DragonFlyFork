package useless.dragonfly;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.stitcher.TextureRegistry;
import net.minecraft.core.util.helper.Side;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.RecipeEntrypoint;
import useless.dragonfly.model.block.adapters.CubeDataJsonAdapter;
import useless.dragonfly.model.block.adapters.FaceDataJsonAdapter;
import useless.dragonfly.model.block.adapters.ModelDataJsonAdapter;
import useless.dragonfly.model.block.adapters.PositionDataJsonAdapter;
import useless.dragonfly.model.block.adapters.RotationDataJsonAdapter;
import useless.dragonfly.model.block.data.CubeData;
import useless.dragonfly.model.block.data.FaceData;
import useless.dragonfly.model.block.data.ModelData;
import useless.dragonfly.model.block.data.PositionData;
import useless.dragonfly.model.block.data.RotationData;
import useless.dragonfly.model.blockstates.adapters.BlockStateJsonAdapter;
import useless.dragonfly.model.blockstates.adapters.ModelPartJsonAdapter;
import useless.dragonfly.model.blockstates.adapters.VariantDataJsonAdapter;
import useless.dragonfly.model.blockstates.data.BlockstateData;
import useless.dragonfly.model.blockstates.data.ModelPart;
import useless.dragonfly.model.blockstates.data.VariantData;
import useless.dragonfly.model.entity.adapters.AnimationDeserializer;
import useless.dragonfly.model.entity.adapters.BenchEntityBonesJsonAdapter;
import useless.dragonfly.model.entity.adapters.BenchEntityCubeJsonAdapter;
import useless.dragonfly.model.entity.adapters.BenchEntityDataJsonAdapter;
import useless.dragonfly.model.entity.adapters.BenchEntityGeometryJsonAdapter;
import useless.dragonfly.model.entity.animation.Animation;
import useless.dragonfly.model.entity.processor.BenchEntityBones;
import useless.dragonfly.model.entity.processor.BenchEntityCube;
import useless.dragonfly.model.entity.processor.BenchEntityGeometry;
import useless.dragonfly.model.entity.processor.BenchEntityModelData;
import useless.dragonfly.utilities.vector.Vector3f;
import useless.dragonfly.utilities.vector.Vector3fJsonAdapter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class DragonFly implements RecipeEntrypoint {
    public static final String MOD_ID = "dragonfly";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Gson GSON = new GsonBuilder()
		.registerTypeAdapter(Vector3f.class, new Vector3fJsonAdapter())
		.registerTypeAdapter(Animation.class, new AnimationDeserializer())
		.registerTypeAdapter(ModelData.class, new ModelDataJsonAdapter())
		.registerTypeAdapter(PositionData.class, new PositionDataJsonAdapter())
		.registerTypeAdapter(CubeData.class, new CubeDataJsonAdapter())
		.registerTypeAdapter(FaceData.class, new FaceDataJsonAdapter())
		.registerTypeAdapter(RotationData.class, new RotationDataJsonAdapter())
		.registerTypeAdapter(ModelPart.class, new ModelPartJsonAdapter())
		.registerTypeAdapter(VariantData.class, new VariantDataJsonAdapter())
		.registerTypeAdapter(BlockstateData.class, new BlockStateJsonAdapter())
		.registerTypeAdapter(BenchEntityModelData.class, new BenchEntityDataJsonAdapter())
		.registerTypeAdapter(BenchEntityGeometry.class, new BenchEntityGeometryJsonAdapter())
		.registerTypeAdapter(BenchEntityBones.class, new BenchEntityBonesJsonAdapter())
		.registerTypeAdapter(BenchEntityCube.class, new BenchEntityCubeJsonAdapter())
		.create();
	public static final Side[] sides = new Side[]{Side.BOTTOM, Side.TOP, Side.NORTH, Side.SOUTH, Side.WEST, Side.EAST};
	public static String version;
	public static boolean isDev;
	public static String renderState = "gui";
	static {
		version = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();
		isDev = version.equals("${version}") || version.contains("dev");
	}
	@Override
	public void onRecipesReady() {
        try {
            TextureRegistry.initializeAllFiles(MOD_ID, TextureRegistry.blockAtlas);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        if (isDev){
			LOGGER.info("DragonFly " + version + " loading debug assets");
			try {
				Class<?> debug = Class.forName("useless.dragonfly.debug.DebugMain");
				debug.getMethod("init").invoke(null);
			} catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
				LOGGER.warn("DragonFly " + version + " failed to find debug assets!", e);
			}
		}
		LOGGER.info("DragonFly initialized.");
	}

	@Override
	public void initNamespaces() {

	}
}
