package org.useless.dragonfly.model.block.data;

import com.google.gson.annotations.SerializedName;
import org.useless.dragonfly.model.block.data.PositionData;

public class DisplayData {
	private PositionData thirdPersonRightHand;
	private PositionData firstPersonRightHand;

	// Getters y Setters
	public PositionData getThirdPersonRightHand() {
		return thirdPersonRightHand;
	}

	public void setThirdPersonRightHand(PositionData thirdPersonRightHand) {
		this.thirdPersonRightHand = thirdPersonRightHand;
	}

	public PositionData getFirstPersonRightHand() {
		return firstPersonRightHand;
	}

	public void setFirstPersonRightHand(PositionData firstPersonRightHand) {
		this.firstPersonRightHand = firstPersonRightHand;
	}
}
