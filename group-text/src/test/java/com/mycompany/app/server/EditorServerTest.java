package com.mycompany.app.server;

import com.mycompany.app.server.EditorServer;
import com.mycompany.app.server.EditorAction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;;

/**
 * EditorServerTest
 */
public class EditorServerTest {

    @Test
    public void applyFunctionTest() {
        EditorServer server;
        EditorAction action;

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            EditorServer lamdaServer = new EditorServer("hi\nbye\nhello\nworld");
            EditorAction lamdaAction = new EditorAction(100, 0, '\u0000');
            lamdaServer.applyFunction(lamdaAction);
        });

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            EditorServer lamdaServer = new EditorServer("hi\nbye\nhello\nworld");
            EditorAction lamdaAction = new EditorAction(0, 100, '\u0000');
            lamdaServer.applyFunction(lamdaAction);
        });

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            EditorServer lamdaServer = new EditorServer("hi\nbye\nhello\nworld");
            EditorAction lamdaAction = new EditorAction(-100, 0, '\u0000');
            lamdaServer.applyFunction(lamdaAction);
        });

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            EditorServer lamdaServer = new EditorServer("hi\nbye\nhello\nworld");
            EditorAction lamdaAction = new EditorAction(0, -100, '\u0000');
            lamdaServer.applyFunction(lamdaAction);
        });

        server = new EditorServer("hi\nbye\nhello\nworld");
        action = new EditorAction(0, 0, '\u0000');
        server.applyFunction(action);
        assertEquals("i", server.getLines().get(0));


        server = new EditorServer("hi\nbye\nhello\nworld");
        action = new EditorAction(1, 1, '\u0000');
        server.applyFunction(action);
        assertEquals("be", server.getLines().get(1));

    }

    @Test
    public void applyFunctionAddTest() {
        EditorServer server;
        EditorAction action;


        assertThrows(IndexOutOfBoundsException.class, ()-> {
            EditorServer lamdaServer = new EditorServer("hi\nbye\nhello\nworld");
            EditorAction lamdaAction = new EditorAction(100, 0, 'c');
            lamdaServer.applyFunction(lamdaAction);
        });

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            EditorServer lamdaServer = new EditorServer("hi\nbye\nhello\nworld");
            EditorAction lamdaAction = new EditorAction(0, 100, 'c');
            lamdaServer.applyFunction(lamdaAction);
        });

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            EditorServer lamdaServer = new EditorServer("hi\nbye\nhello\nworld");
            EditorAction lamdaAction = new EditorAction(-100, 0, 'c');
            lamdaServer.applyFunction(lamdaAction);
        });

        assertThrows(IndexOutOfBoundsException.class, ()-> {
            EditorServer lamdaServer = new EditorServer("hi\nbye\nhello\nworld");
            EditorAction lamdaAction = new EditorAction(0, -100, 'c');
            lamdaServer.applyFunction(lamdaAction);
        });

        server = new EditorServer("hi\nbye\nhello\nworld");
        action = new EditorAction(1, 1, 'e');
        server.applyFunction(action);
        assertEquals("beye", server.getLines().get(1));

        server = new EditorServer("hi\nbye\nhello\nworld");
        action = new EditorAction(0, 0, 'e');
        server.applyFunction(action);
        assertEquals("ehi", server.getLines().get(0));

        server = new EditorServer("hi\nbye\nhello\nworld");
        action = new EditorAction(2, 0, 'e');
        server.applyFunction(action);
        assertEquals("hie", server.getLines().get(0));
    }

    @Test
    public void applyFunctionDeleteNewTest() {
        EditorServer server;
        EditorAction action;

        server = new EditorServer("hi\nbye\nhello");
        action = new EditorAction(-1, 0, '\u0000');
        server.applyFunction(action);
        assertEquals("bye\nhello", server.getLinesToString());

        server = new EditorServer("hi\nbye\nhello");
        action = new EditorAction(-1, 1, '\u0000');
        server.applyFunction(action);
        assertEquals("hi\nhello", server.getLinesToString());

        server = new EditorServer("hi\nbye\nhello");
        action = new EditorAction(-1, 2, '\u0000');
        server.applyFunction(action);
        assertEquals("hi\nbye", server.getLinesToString());
    }

    @Test
    public void applyFunctionAddNewTest() {
        EditorServer server;
        EditorAction action;

        server = new EditorServer("hi\nbye\nhello");
        action = new EditorAction(-1, 0, 'a');
        server.applyFunction(action);
        assertEquals("\nhi\nbye\nhello", server.getLinesToString());

        server = new EditorServer("hi\nbye\nhello");
        action = new EditorAction(-1, 1, 'a');
        server.applyFunction(action);
        assertEquals("hi\n\nbye\nhello", server.getLinesToString());
    }

}
