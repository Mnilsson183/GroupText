package com.mycompany.app.editor.logic;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Scanner;

/**
 * Config
 */
public class Config {

	private Color cursorColor;
	private Color textColor;
	private String fontName;
	private int FontStyle;
	private int FontSize;

	private boolean sendsCursor;

	public Config() {
		this.cursorColor = Color.red;
		this.textColor = Color.black;
		this.fontName = "Monospaced";
		this.FontStyle = Font.PLAIN;
		this.FontSize = 12;
	}

	public Color getCursorColor() {
		return cursorColor;
	}

	public void setCursorColor(Color cursorColor) {
		this.cursorColor = cursorColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getFontStyle() {
		return FontStyle;
	}

	public void setFontStyle(int fontStyle) {
		FontStyle = fontStyle;
	}

	public int getFontSize() {
		return FontSize;
	}

	public void setFontSize(int fontSize) {
		FontSize = fontSize;
	}

	public boolean isSendsCursor() {
		return sendsCursor;
	}

	public void setSendsCursor(boolean sendsCursor) {
		this.sendsCursor = sendsCursor;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				sb.append(field.getName())
				.append(":")
				.append(field.get(this))
				.append("\n");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static Config parseStringToObject(String str) throws Exception {
		Config obj = new Config();
		String[] lines = str.split("\n");

		for (String line : lines) {
			if (line.trim().isEmpty()) continue;

			String[] keyValue = line.split(":");
			if (keyValue.length == 2) {
				String fieldName = keyValue[0].trim();
				String fieldValue = keyValue[1].trim();

				setFieldValue(obj, fieldName, fieldValue);
			}
		}
		return obj;
	}

	private static void setFieldValue(Object obj, String fieldName, String fieldValue) throws Exception {
		Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);  // Allow access to private fields
		if (field.getType().equals(String.class)) {
			field.set(obj, fieldValue);  // Set String value
		} else if (field.getType().equals(int.class)) {
			field.set(obj, Integer.parseInt(fieldValue));  // Set int value
		} else if (field.getType().equals(double.class)) {
			field.set(obj, Double.parseDouble(fieldValue));  // Set double value
		}
	}
}
