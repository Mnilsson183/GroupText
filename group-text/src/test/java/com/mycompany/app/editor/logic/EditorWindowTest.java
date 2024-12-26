package com.mycompany.app.editor.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mycompany.app.server.EditorAction;

/**
 * EditorWindowTest
 */
public class EditorWindowTest {

	@Test
	public void moveCursorUpTest() {
		EditorWindow eWindow = new EditorWindow();

		eWindow.moveCursorUp();
		assertEquals(0, eWindow.getCursorY());		// move cursor up when already at the top

		eWindow.insertNewline();
		eWindow.setCursorY(1);
		assertEquals(1, eWindow.getCursorY());
		eWindow.moveCursorUp();
		assertEquals(0, eWindow.getCursorY());		// you can move the cursor directly up

		eWindow.setCursorY(1);
		eWindow.insertCharacter('x', 0, 0);
		eWindow.insertCharacter('x', 0, 0);
		eWindow.moveCursorUp();
		assertEquals(0, eWindow.getCursorX());		// you can move the cursor straight up onto a line larger than the current line and 
								// and keep the cursor x value

		eWindow.insertNewline(0);
		eWindow.setCursorY(1);
		eWindow.setCursorX(2);
		eWindow.moveCursorUp();
		assertEquals(0, eWindow.getCursorX());		// you can move from a larger line to a smaller and the cursor will go to the bottom
	}

	@Test 
	public void moveCursorDownTest() {
		EditorWindow eWindow = new EditorWindow();

		eWindow.moveCursorDown();
		assertEquals(0, eWindow.getCursorY());		// move cursor up when already at the top
		
		eWindow.insertNewline(0);
		eWindow.moveCursorDown();
		assertEquals(1, eWindow.getCursorY());		// you can move the cursor straight downwards

		eWindow.insertCharacter('x', 0, 1);
		eWindow.insertCharacter('x', 0, 1);
		eWindow.setCursorY(0);
		eWindow.moveCursorDown();
		assertEquals(0, eWindow.getCursorX());		// moving the cursor straght up

		eWindow.insertNewline(2);
		eWindow.setCursorX(2);
		eWindow.moveCursorDown();
		assertEquals(0, eWindow.getCursorX());		// you can move the cursor down from a larger cursor x val to a smaller
	}

	@Test 
	public void moveCursorRightTest() {
		EditorWindow eWindow = new EditorWindow();
		eWindow.moveCursorRight();
		assertEquals(0, eWindow.getCursorX());		// moving right at the end of a line with no line under it will not do anything
		assertEquals(0, eWindow.getCursorY());


		eWindow.insertNewline(1);
		eWindow.moveCursorRight();
		assertEquals(0, eWindow.getCursorX());
		assertEquals(1, eWindow.getCursorY());		// moving right at end of line with line under moves the cursor to zero spot on the next line
		
		eWindow.insertCharacter('x', 0, 0);
		eWindow.insertCharacter('x', 0, 0);
		eWindow.insertCharacter('x', 0, 0);
		eWindow.setCursorY(0);
		assertEquals(0, eWindow.getCursorX());
		eWindow.moveCursorRight();
		assertEquals(1, eWindow.getCursorX());
		eWindow.moveCursorRight();
		assertEquals(2, eWindow.getCursorX());		// regular right cursor movement
	}

	@Test 
	public void moveCursorLeftTest() {
		EditorWindow eWindow = new EditorWindow();
		eWindow.moveCursorLeft();
		assertEquals(0, eWindow.getCursorX());		// moving left at begaining of line doesnt do anything
		
		eWindow.insertCharacter('x', 0, 0);
		eWindow.insertCharacter('x', 0, 0);
		eWindow.insertCharacter('x', 0, 0);
		eWindow.setCursorX(2);
		assertEquals(2, eWindow.getCursorX());
		eWindow.moveCursorLeft();
		assertEquals(1, eWindow.getCursorX());
		eWindow.moveCursorLeft();
		assertEquals(0, eWindow.getCursorX());
		eWindow.moveCursorLeft();			// regular cursor movement
	}

	@Test
	public void insertCharacterTest() {
		EditorWindow eWindow = new EditorWindow();
		eWindow.insertCharacter('c', 0, 0);
		assertEquals("c", eWindow.getLine(0));
		assertEquals(0, eWindow.getCursorY());
		assertEquals(0, eWindow.getCursorX());
		assertEquals(1, eWindow.getLine(0).length());

		eWindow.insertCharacter('1');
		assertEquals("1c", eWindow.getLine(0));
		assertEquals(0, eWindow.getCursorY());
		assertEquals(1, eWindow.getCursorX());
		assertEquals(2, eWindow.getLine(0).length());

		eWindow.insertCharacter('2');
		assertEquals("12c", eWindow.getLine(0));
		assertEquals(0, eWindow.getCursorY());
		assertEquals(2, eWindow.getCursorX());
		assertEquals(3, eWindow.getLine(0).length());

		eWindow.insertCharacter('3');
		assertEquals("123c", eWindow.getLine(0));
		assertEquals(0, eWindow.getCursorY());
		assertEquals(3, eWindow.getCursorX());
		assertEquals(4, eWindow.getLine(0).length());

		eWindow.insertCharacter('c', 0, 0);
		assertEquals("c123c", eWindow.getLine(0));
		assertEquals(0, eWindow.getCursorY());
		assertEquals(3, eWindow.getCursorX());
		assertEquals(5, eWindow.getLine(0).length());

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
	
	@Test
	public void editorFunctionActionTest() {
		EditorWindow eWindow = new EditorWindow();
		EditorAction action;

		action = eWindow.insertCharacter('c');
		assertEquals(new EditorAction(0, 0, "c"), action);

		action = eWindow.backspace();
		assertEquals(new EditorAction(0, 0), action);

		action = eWindow.insertNewline();
		assertEquals(new EditorAction(-1, 1, "0"), action);
	}
}
