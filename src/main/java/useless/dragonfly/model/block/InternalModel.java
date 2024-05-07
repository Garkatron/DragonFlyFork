package useless.dragonfly.model.block;

import useless.dragonfly.model.block.processed.ModernBlockModel;

public class InternalModel{
	public ModernBlockModel model;
	public int rotationX;
	public int rotationY;
	public InternalModel(ModernBlockModel model, int rotationX, int rotationY){
        this.model = model;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
    }
}
