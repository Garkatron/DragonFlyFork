package org.useless.dragonfly.model.entity.processor;

import net.minecraft.core.util.phys.Vec3d;

public class VertexDF {
	public Vec3d vector3D;
	public double texturePositionX;
	public double texturePositionY;

	public VertexDF(double x, double y, double z, double u, double v) {
		this(Vec3d.createVectorHelper(x, y, z), u, v);
	}

	public VertexDF setTexturePosition(double u, double v) {
		return new VertexDF(this, u, v);
	}

	public VertexDF(VertexDF positiontexturevertex, double u, double v) {
		this.vector3D = positiontexturevertex.vector3D;
		this.texturePositionX = u;
		this.texturePositionY = v;
	}

	public VertexDF(Vec3d vec3d, double u, double v) {
		this.vector3D = vec3d;
		this.texturePositionX = u;
		this.texturePositionY = v;
	}
}
