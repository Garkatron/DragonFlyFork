package useless.dragonfly.model.block.processed;

import useless.dragonfly.helper.ModelHelper;
import useless.dragonfly.model.block.data.ModelData;
import useless.dragonfly.registries.TextureRegistry;
import useless.dragonfly.utilities.NamespaceId;

import java.util.HashMap;

public class BlockModel {
	public BlockCube[] blockCubes = new BlockCube[0];
	protected ModelData modelData;
	protected BlockModel parentModel;
	protected HashMap<String, String> textureMap = new HashMap<>();
	public BlockModel(ModelData modelData){
		this.modelData = modelData;

		if (modelData.parent != null && !modelData.parent.equals("block/block")){ // Has parent Model
			String namespace;
			String modelName;
			if (modelData.parent.contains(":")){
				namespace = modelData.parent.split(":")[0];
				modelName = modelData.parent.split(":")[1];
			} else {
				namespace = NamespaceId.coreNamepaceId;
				modelName = modelData.parent;
			}
			parentModel = ModelHelper.getOrCreateBlockModel(namespace, modelName );

			textureMap.putAll(parentModel.textureMap);
		}
		textureMap.putAll(modelData.textures);

		// Initialize textures
		for (String texture: textureMap.values()) {
			if (texture == null) continue;
			TextureRegistry.softRegisterTexture(texture);
		}

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
	public String getTexture(String faceTexKey){
		String result = textureMap.get(faceTexKey.substring(1));
		if (result == null || result.equals(faceTexKey)) return TextureRegistry.getKey(0,0);
		if (result.contains("#")){
			return getTexture(result);
		} else if (!result.contains(":")) {
			result = NamespaceId.coreNamepaceId + ":" + result;
		}
		return result;
	}
	public boolean getAO(){
		return modelData.ambientocclusion;
	}
}
