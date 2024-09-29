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

			double[] rotation = new double[0];
			double[] translation = new double[0];
			double[] scale = new double[]{1, 1, 1};

			if (thirdPersonJson.has("rotation")) {
				rotation = Utilities.doubleArrFromJsonArr(thirdPersonJson.getAsJsonArray("rotation"));
			}
			if (thirdPersonJson.has("translation")) {
				translation = Utilities.doubleArrFromJsonArr(thirdPersonJson.getAsJsonArray("translation"));
			}
			if (thirdPersonJson.has("scale")) {
				scale = Utilities.doubleArrFromJsonArr(thirdPersonJson.getAsJsonArray("scale"));
			}

			displayData.thirdPersonRightHand = new PositionData(rotation, translation, scale);
		}

		// Deserialización de firstperson_righthand
		if (jsonObject.has("firstperson_righthand")) {
			JsonObject firstPersonJson = jsonObject.getAsJsonObject("firstperson_righthand");

			double[] rotation = new double[3];
			double[] translation = new double[3];
			double[] scale = new double[]{1, 1, 1};

			if (firstPersonJson.has("rotation")) {
				rotation = Utilities.doubleArrFromJsonArr(firstPersonJson.getAsJsonArray("rotation"));
			}
			if (firstPersonJson.has("translation")) {
				translation = Utilities.doubleArrFromJsonArr(firstPersonJson.getAsJsonArray("translation"));
			}
			if (firstPersonJson.has("scale")) {
				scale = Utilities.doubleArrFromJsonArr(firstPersonJson.getAsJsonArray("scale"));
			}

			displayData.firstPersonRightHand = new PositionData(rotation, translation, scale);
		}

		// Deserialización de ground
		if (jsonObject.has("ground")) {
			JsonObject groundJson = jsonObject.getAsJsonObject("ground");
			double[] groundRotation = new double[3];

			if (groundJson.has("rotation")) {
				groundRotation = Utilities.doubleArrFromJsonArr(groundJson.getAsJsonArray("rotation"));
			}

			displayData.ground = new PositionData(groundRotation, new double[]{0, 0, 0}, new double[]{1, 1, 1}); // Se puede ajustar la traducción y escala según lo que se necesite
		}

		// Deserialización de gui
		if (jsonObject.has("gui")) {
			JsonObject guiJson = jsonObject.getAsJsonObject("gui");
			double[] guiRotation = new double[3];

			if (guiJson.has("rotation")) {
				guiRotation = Utilities.doubleArrFromJsonArr(guiJson.getAsJsonArray("rotation"));
			}

			displayData.gui = new PositionData(guiRotation, new double[]{0, 0, 0}, new double[]{1, 1, 1}); // Se puede ajustar la traducción y escala según lo que se necesite
		}

		// Deserialización de head
		if (jsonObject.has("head")) {
			JsonObject headJson = jsonObject.getAsJsonObject("head");
			double[] headRotation = new double[3];

			if (headJson.has("rotation")) {
				headRotation = Utilities.doubleArrFromJsonArr(headJson.getAsJsonArray("rotation"));
			}

			displayData.head = new PositionData(headRotation, new double[]{0, 0, 0}, new double[]{1, 1, 1}); // Se puede ajustar la traducción y escala según lo que se necesite
		}

		// Deserialización de fixed
		if (jsonObject.has("fixed")) {
			JsonObject fixedJson = jsonObject.getAsJsonObject("fixed");
			double[] fixedRotation = new double[3];

			if (fixedJson.has("rotation")) {
				fixedRotation = Utilities.doubleArrFromJsonArr(fixedJson.getAsJsonArray("rotation"));
			}

			displayData.fixed = new PositionData(fixedRotation, new double[]{0, 0, 0}, new double[]{1, 1, 1}); // Se puede ajustar la traducción y escala según lo que se necesite
		}

		return displayData;
	}
}
