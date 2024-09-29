package org.useless.dragonfly.model.block.adapters;

import com.google.gson.*;
import org.useless.dragonfly.model.block.data.DisplayData;
import org.useless.dragonfly.model.block.data.PositionData;
import org.useless.dragonfly.utilities.Utilities;

import java.lang.reflect.Type;

public class DisplayDataJsonAdapter implements JsonDeserializer<DisplayData> {
	@Override
	public DisplayData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		DisplayData displayData = new DisplayData();

		// Deserialización de thirdperson_righthand
		if (jsonObject.has("thirdperson_righthand")) {
			JsonObject thirdPersonJson = jsonObject.getAsJsonObject("thirdperson_righthand");

			double[] rotation = thirdPersonJson.has("rotation")
				? Utilities.doubleArrFromJsonArr(thirdPersonJson.getAsJsonArray("rotation"))
				: null;

			double[] translation = thirdPersonJson.has("translation")
				? Utilities.doubleArrFromJsonArr(thirdPersonJson.getAsJsonArray("translation"))
				: null;

			double[] scale = thirdPersonJson.has("scale")
				? Utilities.doubleArrFromJsonArr(thirdPersonJson.getAsJsonArray("scale"))
				: null;

			displayData.thirdPersonRightHand = new PositionData(rotation, translation, scale);
		} else {
			// Valor por defecto si no existe thirdperson_righthand
			displayData.thirdPersonRightHand = new PositionData(null, null, null);
		}

		// Deserialización de firstperson_righthand
		if (jsonObject.has("firstperson_righthand")) {
			JsonObject firstPersonJson = jsonObject.getAsJsonObject("firstperson_righthand");

			double[] rotation = firstPersonJson.has("rotation")
				? Utilities.doubleArrFromJsonArr(firstPersonJson.getAsJsonArray("rotation"))
				: null;

			double[] translation = firstPersonJson.has("translation")
				? Utilities.doubleArrFromJsonArr(firstPersonJson.getAsJsonArray("translation"))
				: null;

			double[] scale = firstPersonJson.has("scale")
				? Utilities.doubleArrFromJsonArr(firstPersonJson.getAsJsonArray("scale"))
				: null;

			displayData.firstPersonRightHand = new PositionData(rotation, translation, scale);
		} else {
			// Valor por defecto si no existe firstperson_righthand
			displayData.firstPersonRightHand = new PositionData(null, null, null);
		}

		// Deserialización de ground
		if (jsonObject.has("ground")) {
			JsonObject groundJson = jsonObject.getAsJsonObject("ground");

			double[] rotation = groundJson.has("rotation")
				? Utilities.doubleArrFromJsonArr(groundJson.getAsJsonArray("rotation"))
				: null;

			displayData.ground = new PositionData(rotation, null, null);
		} else {
			// Valor por defecto si no existe ground
			displayData.ground = new PositionData(null, null, null);
		}

		// Deserialización de gui
		if (jsonObject.has("gui")) {
			JsonObject guiJson = jsonObject.getAsJsonObject("gui");

			double[] rotation = guiJson.has("rotation")
				? Utilities.doubleArrFromJsonArr(guiJson.getAsJsonArray("rotation"))
				: null;

			displayData.gui = new PositionData(rotation, null, null);
		} else {
			// Valor por defecto si no existe gui
			displayData.gui = new PositionData(null, null, null);
		}

		// Deserialización de head
		if (jsonObject.has("head")) {
			JsonObject headJson = jsonObject.getAsJsonObject("head");

			double[] rotation = headJson.has("rotation")
				? Utilities.doubleArrFromJsonArr(headJson.getAsJsonArray("rotation"))
				: null;

			displayData.head = new PositionData(rotation, null, null);
		} else {
			// Valor por defecto si no existe head
			displayData.head = new PositionData(null, null, null);
		}

		// Deserialización de fixed
		if (jsonObject.has("fixed")) {
			JsonObject fixedJson = jsonObject.getAsJsonObject("fixed");

			double[] rotation = fixedJson.has("rotation")
				? Utilities.doubleArrFromJsonArr(fixedJson.getAsJsonArray("rotation"))
				: null;

			displayData.fixed = new PositionData(rotation, null, null);
		} else {
			// Valor por defecto si no existe fixed
			displayData.fixed = new PositionData(null, null, null);
		}

		return displayData;
	}
}
