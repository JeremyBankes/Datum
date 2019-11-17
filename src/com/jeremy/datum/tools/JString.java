package com.jeremy.datum.tools;

public class JString
{
    public static String capitalize(final String text) {
        final char[] chars = text.toLowerCase().toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        for (int i = 1; i < text.length(); ++i) {
            if (Character.isWhitespace(chars[i - 1])) {
                chars[i] = Character.toUpperCase(chars[i]);
            }
        }
        return new String(chars);
    }
    
    public static String join(final String[] list, final String seperator) {
        String result = "";
        for (int i = 0; i < list.length; ++i) {
            if (list[i] != null) {
                if (i != 0) {
                    result = String.valueOf(result) + seperator + list[i];
                }
                else {
                    result = String.valueOf(result) + list[i];
                }
            }
        }
        return result;
    }
}
