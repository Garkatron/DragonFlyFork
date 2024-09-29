package org.useless.dragonfly.model.block.data;

import com.google.gson.annotations.SerializedName;
import org.useless.dragonfly.model.block.data.PositionData;

import java.util.Arrays;

public class DisplayData {
	public PositionData thirdPersonRightHand; // Hacer público
	public PositionData firstPersonRightHand; // Hacer público

	// Nuevas variables añadidas
	public PositionData ground; // Hacer público
	public PositionData gui; // Hacer público
	public PositionData head; // Hacer público
	public PositionData fixed; // Hacer público

	// Getters
	public PositionData getThirdPersonRightHand() {
		return thirdPersonRightHand;
	}

	public PositionData getFirstPersonRightHand() {
		return firstPersonRightHand;
	}

	// Nuevos getters
	public PositionData getGround() {
		return ground;
	}

	public PositionData getGui() {
		return gui;
	}

	public PositionData getHead() {
		return head;
	}

	public PositionData getFixed() {
		return fixed;
	}

	@Override
	public String toString() {
		return "thirdPersonRightHand: " +
			"rotation: " + Arrays.toString(thirdPersonRightHand.rotation) + "\n" +
			"translation: " + Arrays.toString(thirdPersonRightHand.translation) + "\n" +
			"scale: " + Arrays.toString(thirdPersonRightHand.scale) + "\n" +
			"firstPersonRightHand: " +
			"translation: " + Arrays.toString(firstPersonRightHand.translation) + "\n" +
			"ground: " +
			"rotation: " + Arrays.toString(ground.rotation) + "\n" +
			"gui: " +
			"rotation: " + Arrays.toString(gui.rotation) + "\n" +
			"head: " +
			"rotation: " + Arrays.toString(head.rotation) + "\n" +
			"fixed: " +
			"rotation: " + Arrays.toString(fixed.rotation) + "\n";
	}
}
