package com.mycompany.app.editor.logic;

import java.awt.Font;
import java.lang.reflect.Field;

import com.mycompany.app.editor.render.EditorTheme;
import com.mycompany.app.editor.render.SolarizedThemeLight;

/**
 * Config
 */
public class Config {

	// degault to the solarized light theme
    private String fontName;
    private int fontStyle;
    private int fontSize;

    private int resolutionWidth;
    private int resolutionHeight;

    private boolean sendsCursor;

	private EditorTheme theme;

	public Config() {
		this.fontName = "Monospaced";
		this.fontStyle = Font.PLAIN;
		this.fontSize = 12;

		this.resolutionWidth = 800;
		this.resolutionHeight = 600;

		this.theme = new SolarizedThemeLight();
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public int getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getResolutionWidth() {
		return resolutionWidth;
	}

	public void setResolutionWidth(int resolutionWidth) {
		this.resolutionWidth = resolutionWidth;
	}

	public int getResolutionHeight() {
		return resolutionHeight;
	}

	public void setResolutionHeight(int resolutionHeight) {
		this.resolutionHeight = resolutionHeight;
	}

	public EditorTheme getTheme() {
		return theme;
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
