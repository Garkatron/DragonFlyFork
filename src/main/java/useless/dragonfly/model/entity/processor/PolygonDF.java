package useless.dragonfly.model.entity.processor;

import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.util.phys.Vec3d;

public class PolygonDF {
	public VertexDF[] vertexPositions;
	public int nVertices;
	public boolean invertNormal = false;

	public PolygonDF(VertexDF[] vertices) {
		this.vertexPositions = vertices;
		this.nVertices = vertices.length;
	}

	public PolygonDF(VertexDF[] vertices, double minU, double minV, double maxU, double maxV, double texWidth, double texHeight, boolean mirror) {
		this(vertices);
		double offsetU = 0.0015625d;
		double offsetV = 0.003125d;
		if (mirror){
			vertices[0] = vertices[0].setTexturePosition(minU / texWidth + offsetU, minV / texHeight + offsetV);
			vertices[1] = vertices[1].setTexturePosition(maxU / texWidth - offsetU, minV / texHeight + offsetV);
			vertices[2] = vertices[2].setTexturePosition(maxU / texWidth - offsetU, maxV / texHeight - offsetV);
			vertices[3] = vertices[3].setTexturePosition(minU / texWidth + offsetU, maxV / texHeight - offsetV);
		} else {
			vertices[0] = vertices[0].setTexturePosition(maxU / texWidth - offsetU, minV / texHeight + offsetV);
			vertices[1] = vertices[1].setTexturePosition(minU / texWidth + offsetU, minV / texHeight + offsetV);
			vertices[2] = vertices[2].setTexturePosition(minU / texWidth + offsetU, maxV / texHeight - offsetV);
			vertices[3] = vertices[3].setTexturePosition(maxU / texWidth - offsetU, maxV / texHeight - offsetV);
		}
	}

	public void flipFace() {
		VertexDF[] vertices = new VertexDF[this.vertexPositions.length];

		for(int i = 0; i < this.vertexPositions.length; ++i) {
			vertices[i] = this.vertexPositions[this.vertexPositions.length - i - 1];
		}

		this.vertexPositions = vertices;
	}

	public void draw(Tessellator tessellator, double scale) {
		Vec3d vec3d = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[0].vector3D);
		Vec3d vec3d1 = this.vertexPositions[1].vector3D.subtract(this.vertexPositions[2].vector3D);
		Vec3d vec3d2 = vec3d1.crossProduct(vec3d).normalize();
		tessellator.startDrawingQuads();
		if (this.invertNormal) {
			tessellator.setNormal(-((float)vec3d2.xCoord), -((float)vec3d2.yCoord), -((float)vec3d2.zCoord));
		} else {
			tessellator.setNormal((float)vec3d2.xCoord, (float)vec3d2.yCoord, (float)vec3d2.zCoord);
		}

		for(int i = 0; i < 4; ++i) {
			VertexDF positiontexturevertex = this.vertexPositions[i];
			tessellator.addVertexWithUV(
                    positiontexturevertex.vector3D.xCoord * scale,
                    positiontexturevertex.vector3D.yCoord * scale,
                    positiontexturevertex.vector3D.zCoord * scale,
                    positiontexturevertex.texturePositionX,
                    positiontexturevertex.texturePositionY
			);
		}

		tessellator.draw();
	}
}
