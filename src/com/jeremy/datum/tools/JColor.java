package com.jeremy.datum.tools;

import java.awt.*;

public class JColor
{
    public static final Color BLACK;
    public static final Color WHITE;
    public static final Color DARK_RED;
    public static final Color DARK_GREEN;
    public static final Color BLACK_50;
    public static final Color NASTY_GRAY;
    public static final Color NASTY_BROWN;
    public static final Color HAPPY_GREEN;
    
    static {
        BLACK = new Color(0, 0, 0);
        WHITE = new Color(255, 255, 255);
        DARK_RED = new Color(100, 0, 0);
        DARK_GREEN = new Color(0x5B6C58);
        BLACK_50 = new Color(0, 0, 0, 127);
        NASTY_GRAY = new Color(68, 71, 65);
        NASTY_BROWN = new Color(55, 51, 44);
        HAPPY_GREEN = new Color(0x61735E);
    }
}
