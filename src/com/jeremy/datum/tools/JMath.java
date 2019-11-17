package com.jeremy.datum.tools;

public class JMath
{
    public static float distance(final float x1, final float y1, final float x2, final float y2) {
        return (float)Math.sqrt(Math.pow(x2 - x1, 2.0) + Math.pow(y2 - y1, 2.0));
    }
}
