package org.useless.dragonfly.model.block.processed;

import org.useless.dragonfly.helper.ModelHelper;
import org.useless.dragonfly.model.block.data.ModelData;
import org.useless.dragonfly.model.block.data.PositionData;
import org.useless.dragonfly.utilities.NamespaceId;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class ModernBlockModel {
	private static final HashMap<String, PositionData> defaultDisplays = new HashMap<>();
	static {
		PositionData gui = new PositionData(new double[]{30, 225, 0}, new double[] {0, 0, 0}, new double[]{0.625, 0.625, 0.625});
		PositionData ground = new PositionData(new double[]{0, 0, 0}, new double[] {0, 3, 0}, new double[]{0.25, 0.25, 0.25});
		PositionData right_3rd = new PositionData(new double[]{75, 45, 0}, new double[] {0, 2.5, 0}, new double[]{0.375, 0.375, 0.375});
		PositionData right_first = new PositionData(new double[]{0, 45, 0}, new double[] {0, 0, 0}, new double[]{0.4, 0.4, 0.4});

		defaultDisplays.put("gui", gui);
		defaultDisplays.put("ground", ground);
		defaultDisplays.put("thirdperson_righthand", right_3rd);
		defaultDisplays.put("firstperson_righthand", right_first);
	}
	public BlockCube[] blockCubes = new BlockCube[0];
	protected ModelData modelData;
	protected ModernBlockModel parentModel;
	public HashMap<String, String> textureMap;
	public HashMap<String, PositionData> display;
	public final NamespaceId namespaceId;
	public ModernBlockModel(NamespaceId namespaceId){
		this.namespaceId = namespaceId;
		refreshModel();
	}
	public void refreshModel(){
		this.modelData = ModelHelper.loadBlockModel(namespaceId);
		textureMap = new HashMap<>();
		display = new HashMap<>();

		if (modelData.parent != null){ // Has parent Model
			String namespace;
			String modelName;
			if (modelData.parent.contains(":")){
				namespace = modelData.parent.split(":")[0];
				modelName = modelData.parent.split(":")[1];
			} else {
				namespace = NamespaceId.coreNamespaceId;
				modelName = modelData.parent;
			}
			parentModel = ModelHelper.getOrCreateBlockModel(namespace, modelName );

			textureMap.putAll(parentModel.textureMap);
			display.putAll(parentModel.display);
		}
		textureMap.putAll(modelData.textures);
		display.putAll(modelData.display);

		// Use parent elements if model does not specify its own
		if (parentModel != null && modelData.elements == null){
			this.blockCubes = new BlockCube[parentModel.blockCubes.length];
			for (int i = 0; i < blockCubes.length; i++) {
				blockCubes[i] = new BlockCube(this, parentModel.blockCubes[i].cubeData);
			}
		} else if (modelData.elements != null) {
			this.blockCubes = new BlockCube[modelData.elements.length];
			for (int i = 0; i < blockCubes.length; i++) {
				blockCubes[i] = new BlockCube(this, modelData.elements[i]);
			}
		}
	}
	public NamespaceId getTexture(String textureKey){
		String result;
		if (textureKey.contains("#")){
			result = textureMap.get(textureKey.substring(1));
		} else {
			result = textureMap.get(textureKey);
		}

		if (result == null || result.equals(textureKey)) return NamespaceId.idFromString("minecraft:block/texture_missing");
		if (result.contains("#")){
			return getTexture(result);
		} else if (!result.contains(":")) {
			result = NamespaceId.coreNamespaceId + ":" + result;
		}
		return NamespaceId.idFromString(result);
	}
	public boolean getAO(){
		return modelData.ambientocclusion;
	}
	@Nonnull
	public PositionData getDisplayPosition(String key){
		if (display.containsKey(key)){
			return display.get(key);
		}
		display.put(key, defaultDisplays.getOrDefault(key, new PositionData()));
		return display.get(key);
	}
}
