package com.audaque.lib.core.lexical;

/**
 *
 * @author yd
 */
public class SyntaxError {

    public int line;
    public int column;
    public String message;

    @Override
    public String toString() {
        return "(" + line + ", " + column + ") " + message;
    }
}
