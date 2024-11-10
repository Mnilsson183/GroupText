package com.mycompany.app.editor.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * EditorWindowTest
 */
public class EditorWindowTest {

	@Test
	public void insertCharacterTest() {
		EditorWindow eWindow = new EditorWindow();
		eWindow.insertCharacter('c', 0, 0);
		assertEquals("c", eWindow.getLine(0));

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			eWindow.insertCharacter('c', -1, 0);
		});

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			eWindow.insertCharacter('c', 100, 0);
		});

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			eWindow.insertCharacter('c', 0, -1);
		});

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			eWindow.insertCharacter('c', 0, 100);
		});
	}

	@Test
	public void removeCharacter() {
		EditorWindow eWindow = new EditorWindow();
		eWindow.insertCharacter('c', 0, 0);
		eWindow.removeCharacter(0, 0);
		assertEquals("", eWindow.getLine(0));

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			eWindow.removeCharacter(-1, 0);
		});
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			eWindow.removeCharacter(100, 0);
		});
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			eWindow.removeCharacter(0, -1);
		});
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			eWindow.removeCharacter(0, 100);
		});
	}

	@Test
	public void insertNewlineTest() {
		EditorWindow eWindow = new EditorWindow();
		eWindow.insertCharacter('c', 0, 0);
		eWindow.insertNewline(1);
		eWindow.insertCharacter('d', 0, 1);
		assertEquals("c", eWindow.getLine(0));
		assertEquals("d", eWindow.getLine(1));

		eWindow.removeLine(0);
		eWindow.removeLine(0);

		eWindow.insertNewline(0);
		eWindow.insertCharacter('c', 0, 0);
		eWindow.insertNewline(0);

		assertEquals("", eWindow.getLine(0));
		assertEquals("c", eWindow.getLine(1));

		eWindow.insertNewline(100);
		assertEquals("", eWindow.getLine(2));

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			eWindow.insertNewline(-1);
		});
	}

	@Test
	public void removeLineTest() {
		EditorWindow eWindow = new EditorWindow();
		eWindow.removeLine(0);

		eWindow.insertNewline(0);

		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			eWindow.removeLine(1000);
		});
	}
	
}
