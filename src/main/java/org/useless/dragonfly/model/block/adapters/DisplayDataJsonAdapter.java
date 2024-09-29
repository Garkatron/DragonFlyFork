package org.useless.dragonfly.model.block.adapters;

import com.google.gson.*;
import org.useless.dragonfly.model.block.data.DisplayData;
import org.useless.dragonfly.model.block.data.PositionData;

import java.lang.reflect.Type;

public class DisplayDataJsonAdapter implements JsonDeserializer<DisplayData> {
	@Override
	public DisplayData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		DisplayData displayData = new DisplayData();

		if (jsonObject.has("thirdperson_righthand")) {
			JsonObject thirdPersonJson = jsonObject.getAsJsonObject("thirdperson_righthand");
			PositionData thirdPersonPosition = context.deserialize(thirdPersonJson, PositionData.class);
			displayData.setThirdPersonRightHand(thirdPersonPosition);
		}

		if (jsonObject.has("firstperson_righthand")) {
			JsonObject firstPersonJson = jsonObject.getAsJsonObject("firstperson_righthand");
			PositionData firstPersonPosition = context.deserialize(firstPersonJson, PositionData.class);
			displayData.setFirstPersonRightHand(firstPersonPosition);
		}

		// I don't need it, I don't want to do it... Sorry

		if (jsonObject.has("ground")) {
			// Need to implement
		}

		if (jsonObject.has("gui")) {
			// Need to implement
		}

		if (jsonObject.has("head")) {
			// Need to implement
		}

		if (jsonObject.has("fixed")) {
			// Need to implement
		}

		return displayData;
	}
}
