package org.useless.dragonfly.model.block;

import net.minecraft.core.block.Block;
import org.lwjgl.Sys;
import org.useless.dragonfly.helper.ModelHelper;
import org.useless.dragonfly.model.block.processed.ModernBlockModel;
import org.useless.dragonfly.model.blockstates.data.BlockstateData;
import org.useless.dragonfly.model.blockstates.processed.MetaStateInterpreter;

public final class DFBlockModelBuilder {
	private final String modid;
	private boolean render3d = true;
	private float renderScale = 0.25f;
	private ModernBlockModel modernBlockModel;
	private BlockstateData blockstateData;
	private MetaStateInterpreter metaStateInterpreter;
	public DFBlockModelBuilder(String modId){
		this.modid = modId;
	}
	public DFBlockModelBuilder setRender3D(boolean render3d){
		this.render3d = render3d;
		return this;
	}
	public DFBlockModelBuilder setRenderScale(float renderScale){
		this.renderScale = renderScale;
		return this;
	}
	public DFBlockModelBuilder setBlockModel(String blockModelPath){
		this.modernBlockModel = ModelHelper.getOrCreateBlockModel(modid, blockModelPath);
		return this;
	}
	public DFBlockModelBuilder setBlockModel(String modid, String blockModelPath){
		this.modernBlockModel = ModelHelper.getOrCreateBlockModel(modid, blockModelPath);
		return this;
	}
	public DFBlockModelBuilder setBlockState(String blockStatePath){
		this.blockstateData = ModelHelper.getOrCreateBlockState(modid, blockStatePath);
		return this;
	}
	public DFBlockModelBuilder setBlockState(String modid, String blockStatePath){
		this.blockstateData = ModelHelper.getOrCreateBlockState(modid, blockStatePath);
		return this;
	}
	public DFBlockModelBuilder setMetaStateInterpreter(MetaStateInterpreter interpreter){
		this.metaStateInterpreter = interpreter;
		return this;
	}
	public BlockModelDragonFly build(Block block){
		if (modernBlockModel == null) throw new RuntimeException("Assign a block model before building! use .setBlockModel");
		if (blockstateData == null && metaStateInterpreter != null) throw new RuntimeException("MetaStateInterpreter require a BlockState! use .setBlockState");
		if (blockstateData != null && metaStateInterpreter == null) throw new RuntimeException("BlockState requires a MetaStateInterpreter! use .setMetaStateInterpreter");
		return new BlockModelDragonFly(block, modernBlockModel, blockstateData, metaStateInterpreter, render3d, renderScale);
	}
}
