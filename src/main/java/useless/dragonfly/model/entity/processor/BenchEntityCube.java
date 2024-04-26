package useless.dragonfly.model.entity.processor;

import com.google.common.collect.Lists;
import net.minecraft.client.GLAllocation;
import net.minecraft.client.render.Tessellator;
import net.minecraft.core.util.helper.Direction;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import useless.dragonfly.utilities.Utilities;
import useless.dragonfly.utilities.vector.Vector3f;

import java.util.List;

public class BenchEntityCube {

	private static final float COMPARE_CONST = 0.001f;
	private VertexDF[] corners;
	private List<PolygonDF> polygons;
	private Boolean mirror;
	private final float inflate;
	private final Vector3f size;
	private final Vector3f origin;
	private final List<Float> uv;
	@Nullable
	private final BenchFaceUVsItem faceUv;
	private final Vector3f rotation;
	private final Vector3f pivot;
	public BenchEntityCube(Vector3f origin, Vector3f pivot, Vector3f rotation, Vector3f size, float inflate, List<Float> uv, BenchFaceUVsItem faceUv, Boolean mirrored){
        this.origin = origin;
        this.pivot = pivot;
        this.rotation = rotation;
        this.size = size;
        this.inflate = inflate;
        this.uv = uv;
        this.faceUv = faceUv;
		this.mirror = mirrored;
    }

	private boolean compiled = false;
	private int displayList = 0;

	public boolean isMirror() {
		if (mirror == null) return false;
		return mirror;
	}

	public boolean isHasMirror() {
		return mirror != null;
	}

	public void setMirror(boolean mirror) {
		this.mirror = mirror;
	}

	public float getInflate() {
		return inflate;
	}

	public Vector3f getSize() {
		return size;
	}

	public Vector3f getOrigin() {
		return origin;
	}

	@Nullable
	public Vector3f getRotation() {
		return rotation;
	}

	@Nullable
	public Vector3f getPivot() {
		return pivot;
	}

	public List<Float> getUv() {
		return uv;
	}

	@Nullable
	public BenchFaceUVsItem getFaceUv() {
		return faceUv;
	}

	public void addBox(int texWidth, int texHeight, float x, float y, float z, boolean flipBottomUV) {
		if (faceUv != null) {
			this.corners = new VertexDF[8];
			this.polygons = Lists.newArrayList();

			float minX = x;
			float minY = y;
			float minZ = z;
			float maxX = size.x + x;
			float maxY = size.y + y;
			float maxZ = size.z + z;
			minX -= inflate;
			minY -= inflate;
			minZ -= inflate;
			maxX += inflate;
			maxY += inflate;
			maxZ += inflate;

			VertexDF ptvMinXMinYMinZ = new VertexDF(minX, minY, minZ, 0.0f, 0.0f);
			VertexDF ptvMaxXMinYMinZ = new VertexDF(maxX, minY, minZ, 0.0f, 8.0f);
			VertexDF ptvMaxXMaxYMinZ = new VertexDF(maxX, maxY, minZ, 8.0f, 8.0f);
			VertexDF ptvMinXMaxYMinZ = new VertexDF(minX, maxY, minZ, 8.0f, 0.0f);
			VertexDF ptvMinXMinYMaxZ = new VertexDF(minX, minY, maxZ, 0.0f, 0.0f);
			VertexDF ptvMaxXMinYMaxZ = new VertexDF(maxX, minY, maxZ, 0.0f, 8.0f);
			VertexDF ptvMaxXMaxYMaxZ = new VertexDF(maxX, maxY, maxZ, 8.0f, 8.0f);
			VertexDF ptvMinXMaxYMaxZ = new VertexDF(minX, maxY, maxZ, 8.0f, 0.0f);
			this.corners[0] = ptvMinXMinYMinZ;
			this.corners[1] = ptvMaxXMinYMinZ;
			this.corners[2] = ptvMaxXMaxYMinZ;
			this.corners[3] = ptvMinXMaxYMinZ;
			this.corners[4] = ptvMinXMinYMaxZ;
			this.corners[5] = ptvMaxXMinYMaxZ;
			this.corners[6] = ptvMaxXMaxYMaxZ;
			this.corners[7] = ptvMinXMaxYMaxZ;

			BenchEntityFace benchFace = faceUv.getFace(Direction.EAST);
			double texU = benchFace.getUv()[0];
			double texV = benchFace.getUv()[1];
			double uSize = benchFace.getUvSize()[0];
			double vSize = benchFace.getUvSize()[1];
			this.polygons.add(makePolygon(new VertexDF[]{ptvMaxXMinYMaxZ, ptvMaxXMinYMinZ, ptvMaxXMaxYMinZ, ptvMaxXMaxYMaxZ}, texU, texV, (texU + uSize), (texV + vSize), texWidth, texHeight));

			benchFace = faceUv.getFace(Direction.WEST);
			texU = benchFace.getUv()[0];
			texV = benchFace.getUv()[1];
			uSize = benchFace.getUvSize()[0];
			vSize = benchFace.getUvSize()[1];
			this.polygons.add(makePolygon(new VertexDF[]{ptvMinXMinYMinZ, ptvMinXMinYMaxZ, ptvMinXMaxYMaxZ, ptvMinXMaxYMinZ}, texU, texV, (texU + uSize), (texV + vSize), texWidth, texHeight));

			benchFace = faceUv.getFace(Direction.DOWN);
			texU = benchFace.getUv()[0];
			texV = benchFace.getUv()[1];
			uSize = benchFace.getUvSize()[0];
			vSize = benchFace.getUvSize()[1];
			this.polygons.add(makePolygon(new VertexDF[]{ptvMaxXMinYMaxZ, ptvMinXMinYMaxZ, ptvMinXMinYMinZ, ptvMaxXMinYMinZ}, texU, texV, (texU + uSize), (texV + vSize), texWidth, texHeight));

			benchFace = faceUv.getFace(Direction.UP);
			texU = benchFace.getUv()[0];
			texV = benchFace.getUv()[1];
			uSize = benchFace.getUvSize()[0];
			vSize = benchFace.getUvSize()[1];
			if (flipBottomUV) {
				PolygonDF polygon = makePolygon(new VertexDF[]{ptvMaxXMaxYMaxZ, ptvMinXMaxYMaxZ, ptvMinXMaxYMinZ, ptvMaxXMaxYMinZ}, texU, texV, (texU + uSize), (texV + vSize), texWidth, texHeight);
				polygon.invertNormal = true;
				this.polygons.add(polygon);
			} else {
				PolygonDF polygon = makePolygon(new VertexDF[]{ptvMaxXMaxYMinZ, ptvMinXMaxYMinZ, ptvMinXMaxYMaxZ, ptvMaxXMaxYMaxZ}, texU, texV, (texU + uSize), (texV + vSize), texWidth, texHeight);
				this.polygons.add(polygon);
			}

			benchFace = faceUv.getFace(Direction.NORTH);
			texU = benchFace.getUv()[0];
			texV = benchFace.getUv()[1];
			uSize = benchFace.getUvSize()[0];
			vSize = benchFace.getUvSize()[1];
			this.polygons.add(makePolygon(new VertexDF[]{ptvMaxXMinYMinZ, ptvMinXMinYMinZ, ptvMinXMaxYMinZ, ptvMaxXMaxYMinZ}, texU, texV, (texU + uSize), (texV + vSize), texWidth, texHeight));
			benchFace = faceUv.getFace(Direction.SOUTH);
			texU = benchFace.getUv()[0];
			texV = benchFace.getUv()[1];
			uSize = benchFace.getUvSize()[0];
			vSize = benchFace.getUvSize()[1];
			this.polygons.add(makePolygon(new VertexDF[]{ptvMinXMinYMaxZ, ptvMaxXMinYMaxZ, ptvMaxXMaxYMaxZ, ptvMinXMaxYMaxZ}, texU, texV, (texU + uSize), (texV + vSize), texWidth, texHeight));
		} else if (polygons == null) {
			this.corners = new VertexDF[8];
			this.polygons = Lists.newArrayList();

			float texU = uv.get(0);
			float texV = uv.get(1);
			float minX = x;
			float minY = y;
			float minZ = z;
			float maxX = size.x + x;
			float maxY = size.y + y;
			float maxZ = size.z + z;
			float sizeX = size.x;
			float sizeY = size.y;
			float sizeZ = size.z;
			minX -= inflate;
			minY -= inflate;
			minZ -= inflate;
			maxX += inflate;
			maxY += inflate;
			maxZ += inflate;

			VertexDF ptvMinXMinYMinZ = new VertexDF(minX, minY, minZ, 0.0f, 0.0f);
			VertexDF ptvMaxXMinYMinZ = new VertexDF(maxX, minY, minZ, 0.0f, 8.0f);
			VertexDF ptvMaxXMaxYMinZ = new VertexDF(maxX, maxY, minZ, 8.0f, 8.0f);
			VertexDF ptvMinXMaxYMinZ = new VertexDF(minX, maxY, minZ, 8.0f, 0.0f);
			VertexDF ptvMinXMinYMaxZ = new VertexDF(minX, minY, maxZ, 0.0f, 0.0f);
			VertexDF ptvMaxXMinYMaxZ = new VertexDF(maxX, minY, maxZ, 0.0f, 8.0f);
			VertexDF ptvMaxXMaxYMaxZ = new VertexDF(maxX, maxY, maxZ, 8.0f, 8.0f);
			VertexDF ptvMinXMaxYMaxZ = new VertexDF(minX, maxY, maxZ, 8.0f, 0.0f);
			this.corners[0] = ptvMinXMinYMinZ;
			this.corners[1] = ptvMaxXMinYMinZ;
			this.corners[2] = ptvMaxXMaxYMinZ;
			this.corners[3] = ptvMinXMaxYMinZ;
			this.corners[4] = ptvMinXMinYMaxZ;
			this.corners[5] = ptvMaxXMinYMaxZ;
			this.corners[6] = ptvMaxXMaxYMaxZ;
			this.corners[7] = ptvMinXMaxYMaxZ;
			this.polygons.add(makePolygon(new VertexDF[]{ptvMaxXMinYMaxZ, ptvMaxXMinYMinZ, ptvMaxXMaxYMinZ, ptvMaxXMaxYMaxZ}, (texU + sizeZ + sizeX), (texV + sizeZ), (texU + sizeZ + sizeX + sizeZ), (texV + sizeZ + sizeY), texWidth, texHeight));
			this.polygons.add(makePolygon(new VertexDF[]{ptvMinXMinYMinZ, ptvMinXMinYMaxZ, ptvMinXMaxYMaxZ, ptvMinXMaxYMinZ}, texU, (texV + sizeZ), (texU + sizeZ), (texV + sizeZ + sizeY), texWidth, texHeight));
			this.polygons.add(makePolygon(new VertexDF[]{ptvMaxXMinYMaxZ, ptvMinXMinYMaxZ, ptvMinXMinYMinZ, ptvMaxXMinYMinZ}, (texU + sizeZ), texV, (texU + sizeZ + sizeX), (texV + sizeZ), texWidth, texHeight));
			if (flipBottomUV) {
				PolygonDF polygon = makePolygon(new VertexDF[]{ptvMaxXMaxYMaxZ, ptvMinXMaxYMaxZ, ptvMinXMaxYMinZ, ptvMaxXMaxYMinZ}, (texU + sizeZ + sizeX), texV, (texU + sizeZ + sizeX + sizeX), (texV + sizeZ), texWidth, texHeight);
				polygon.invertNormal = true;
				this.polygons.add(polygon);
			} else {
				PolygonDF polygon = makePolygon(new VertexDF[]{ptvMaxXMaxYMinZ, ptvMinXMaxYMinZ, ptvMinXMaxYMaxZ, ptvMaxXMaxYMaxZ}, (texU + sizeZ + sizeX), texV, (texU + sizeZ + sizeX + sizeX), (texV + sizeZ), texWidth, texHeight);
				this.polygons.add(polygon);
			}
			this.polygons.add(makePolygon(new VertexDF[]{ptvMaxXMinYMinZ, ptvMinXMinYMinZ, ptvMinXMaxYMinZ, ptvMaxXMaxYMinZ}, (texU + sizeZ), (texV + sizeZ), (texU + sizeZ + sizeX), (texV + sizeZ + sizeY), texWidth, texHeight));
			this.polygons.add(makePolygon(new VertexDF[]{ptvMinXMinYMaxZ, ptvMaxXMinYMaxZ, ptvMaxXMaxYMaxZ, ptvMinXMaxYMaxZ}, (texU + sizeZ + sizeX + sizeZ), (texV + sizeZ), (texU + sizeZ + sizeX + sizeZ + sizeX), (texV + sizeZ + sizeY), texWidth, texHeight));
		}
	}

	private PolygonDF makePolygon(VertexDF[] vertices, double minU, double minV, double maxU, double maxV, double texWidth, double texHeight){
        return new PolygonDF(vertices, (int) minU, (int) minV, (int) maxU, (int) maxV, (int) texWidth, (int) texHeight, isMirror());
	}

	private PolygonDF getTexturedQuad(VertexDF[] positionsIn, float texWidth, float texHeight, Direction direction, BenchFaceUVsItem faces) {
		BenchEntityFace face = faces.getFace(direction);
		if (Utilities.equalFloat(face.getUvSize()[0], 0.0F) && Utilities.equalFloat(face.getUvSize()[1], 0.0F))
			return null;
		double u1 = face.getUv()[0];
		double v1 = face.getUv()[1];
		double u2 = u1 + face.getUvSize()[0];
		double v2 = v1 + face.getUvSize()[1];
		PolygonDF polygon = makePolygon(positionsIn, u1, v1, u2, v2, texWidth, texHeight);

		for (int i = 0; i < polygon.vertexPositions.length; ++i) {
			polygon.vertexPositions[i].vector3D.xCoord += direction.getOffsetX();
			polygon.vertexPositions[i].vector3D.yCoord += direction.getOffsetY();
			polygon.vertexPositions[i].vector3D.zCoord += direction.getOffsetZ();
		}

		return polygon;
	}

	public void compileDisplayList(double f) {
		if (this.polygons != null) {
			this.displayList = GLAllocation.generateDisplayLists(1);
			GL11.glNewList(this.displayList, 4864);
			Tessellator tessellator = Tessellator.instance;
            for (PolygonDF polygon : this.polygons) {
                polygon.draw(tessellator, f);
            }
			GL11.glEndList();
			this.compiled = true;
		}
	}

	private static boolean equalFloats(float a, float b) {
		return Math.abs(Float.compare(a, b)) < COMPARE_CONST;
	}

	public boolean isCompiled() {
		return compiled;
	}

	public int getDisplayList() {
		return displayList;
	}

}
