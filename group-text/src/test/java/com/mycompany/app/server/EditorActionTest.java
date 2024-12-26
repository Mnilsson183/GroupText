package com.mycompany.app.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mycompany.app.server.EditorAction;
/**
 * EditorActionTest
 */
public class EditorActionTest {

    @Test
    public void parseEditorActionTest() {
        EditorAction action;

        String s = "2,3,c";
        action = EditorAction.parseEditorAction(s);
        assertEquals(2, action.getX());
        assertEquals(3, action.getY());
        assertEquals("c", action.getValue());

        s = "2,3";
        action = EditorAction.parseEditorAction(s);
        assertEquals(2, action.getX());
        assertEquals(3, action.getY());
        assertFalse(action.hasChar());

        s = "2,3,";
        action = EditorAction.parseEditorAction(s);
        assertEquals(2, action.getX());
        assertEquals(3, action.getY());
        assertFalse(action.hasChar());

        assertThrows(IllegalArgumentException.class, () -> {
            EditorAction.parseEditorAction("2, ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EditorAction.parseEditorAction(" , ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EditorAction.parseEditorAction(" ,2");
        });

        s = "2,3,//";
        action = EditorAction.parseEditorAction(s);
        assertEquals(2, action.getX());
        assertEquals(3, action.getY());
        assertEquals(",", action.getValue());
    }

    @Test
    public void getEditorActionStringTest() {
        EditorAction action;

        action = new EditorAction(2, 3, "c");
        assertEquals("2,3,c", EditorAction.getEditorActionString(action));

        action = new EditorAction(2, 3, "");
        assertEquals("2,3", EditorAction.getEditorActionString(action));

        action = new EditorAction(2, 3, ",");
        assertEquals("2,3,//", EditorAction.getEditorActionString(action));
    }

}
