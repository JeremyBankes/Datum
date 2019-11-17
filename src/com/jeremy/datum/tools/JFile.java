package com.jeremy.datum.tools;

import java.nio.file.*;
import java.io.*;
import java.lang.reflect.*;

public class JFile
{
    private static String home;
    
    static {
        JFile.home = System.getProperty("user.home");
    }
    
    public static File createFile(final String path) {
        try {
            final String folderPath = path.replaceAll("\\\\[^\\\\]+$", "");
            if (!new File(folderPath).exists()) {
                new File(folderPath).mkdirs();
            }
            final File file = new File(path);
            file.createNewFile();
            return file;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static File createFileFromResource(final String path, final String toLocation) {
        try {
            final File output = new File(String.valueOf(JFile.home) + "/" + toLocation + "/" + path);
            final InputStream input = JFile.class.getResourceAsStream("/" + path);
            createFile(output.getPath());
            Files.copy(input, output.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return output;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static File grabResource(final String path) {
        return new File(JFile.class.getResource("/" + path).getFile());
    }
    
    public static File grab(final String path, final String toLocation) {
        final String filePath = String.valueOf(toLocation) + "/" + path;
        if (new File(filePath).exists()) {
            return new File(filePath);
        }
        return createFileFromResource(path, toLocation);
    }
    
    public static String getString(final File file, final String key) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final StringBuilder builder = new StringBuilder();
            String line = reader.readLine();
            boolean read = false;
            while (line != null) {
                if (line.matches(String.valueOf(key) + "\\:.*")) {
                    read = true;
                }
                if (read && !line.startsWith(" - ") && !line.matches(String.valueOf(key) + "\\:.*")) {
                    read = false;
                }
                if (read) {
                    builder.append((String.valueOf(line) + "\n").replaceFirst(" - ", ""));
                }
                line = reader.readLine();
            }
            reader.close();
            return builder.toString().replaceAll(String.valueOf(key) + "\\:|\n$", "").replaceFirst("^ .*?|^\n.*?", "");
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static int getInt(final File file, final String key) {
        try {
            return Integer.parseInt(getString(file, key));
        }
        catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public static void set(final File file, final String key, final Object value) {
        try {
            final File tempFile = new File(file.getPath().replaceAll("([^\\.]+)$", "temp"));
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line = reader.readLine();
            boolean skip = false;
            while (line != null) {
                if (line.matches(String.valueOf(key) + "\\:.*")) {
                    skip = true;
                    if (value.getClass().isArray()) {
                        writer.write(String.valueOf(key) + ":" + "\n");
                        for (int i = 0; i < Array.getLength(value); ++i) {
                            final Object o = Array.get(value, i);
                            writer.write(" - " + o + "\n");
                        }
                    }
                    else {
                        writer.write(String.valueOf(key) + ": " + value + "\n");
                    }
                }
                else if (!line.startsWith(" - ")) {
                    skip = false;
                }
                if (!skip) {
                    writer.write(String.valueOf(line) + "\n");
                }
                line = reader.readLine();
            }
            reader.close();
            writer.close();
            file.delete();
            tempFile.renameTo(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
