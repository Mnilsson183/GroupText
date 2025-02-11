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

	// degault to the solarized light theme
    private Color cursorColor;
    private Color textColor;
    private String fontName;
    private int fontStyle;
    private int fontSize;

    private int resolutionWidth;
    private int resolutionHeight;

	private Color themeBackgroundTones1;
	private Color themeBackgroundTones2;

	private Color contentTones1;
	private Color contentTones2;

	private Color accentColor1;
	private Color accentColor2;
	private Color accentColor3;
	private Color accentColor4;

    private boolean sendsCursor;

	public Config() {
		this.cursorColor = Color.ORANGE;
		this.textColor = Color.BLACK;
		this.fontName = "Monospaced";
		this.fontStyle = Font.PLAIN;
		this.fontSize = 12;

		this.resolutionWidth = 800;
		this.resolutionHeight = 600;

		// test colors
		this.themeBackgroundTones1 = new Color(238, 232, 213);
		this.themeBackgroundTones2 = new Color(253, 246, 227);

		this.contentTones1 = new Color(0, 43, 54);
		this.contentTones2 = new Color(7, 54, 66);

		this.accentColor1 = new Color(181, 137, 0);
		this.accentColor2 = new Color(203, 75, 22);
		this.accentColor3 = new Color(108, 113, 196);
		this.accentColor4 = new Color(38, 139, 210);
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

	public Color getThemeBackgroundTones1() {
		return themeBackgroundTones1;
	}

	public void setThemeBackgroundTones1(Color themeBackgroundTones1) {
		this.themeBackgroundTones1 = themeBackgroundTones1;
	}

	public Color getThemeBackgroundTones2() {
		return themeBackgroundTones2;
	}

	public void setThemeBackgroundTones2(Color themeBackgroundTones2) {
		this.themeBackgroundTones2 = themeBackgroundTones2;
	}

	public Color getContentTones1() {
		return contentTones1;
	}

	public void setContentTones1(Color contentTones1) {
		this.contentTones1 = contentTones1;
	}

	public Color getContentTones2() {
		return contentTones2;
	}

	public void setContentTones2(Color contentTones2) {
		this.contentTones2 = contentTones2;
	}

	public Color getAccentColor1() {
		return accentColor1;
	}

	public void setAccentColor1(Color accentColor1) {
		this.accentColor1 = accentColor1;
	}

	public Color getAccentColor2() {
		return accentColor2;
	}

	public void setAccentColor2(Color accentColor2) {
		this.accentColor2 = accentColor2;
	}

	public Color getAccentColor3() {
		return accentColor3;
	}

	public void setAccentColor3(Color accentColor3) {
		this.accentColor3 = accentColor3;
	}

	public Color getAccentColor4() {
		return accentColor4;
	}

	public void setAccentColor4(Color accentColor4) {
		this.accentColor4 = accentColor4;
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
