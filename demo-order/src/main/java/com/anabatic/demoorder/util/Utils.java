package com.anabatic.demoorder.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {

	public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static Object convertFromByteJsonToObject(byte[] object) {

		StringBuilder sb = new StringBuilder("");
		for (byte b : object) {
			sb.append(Character.toString((char) b));
		}
		System.out.println(sb.toString());

		Object result = gson.fromJson(sb.toString(), Object.class);
		return result;
	}
}
