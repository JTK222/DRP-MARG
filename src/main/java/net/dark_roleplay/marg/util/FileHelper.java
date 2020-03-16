package net.dark_roleplay.marg.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

import net.minecraft.util.ResourceLocation;

public class FileHelper {

    public static boolean doesFileExistClient(String string) {
        ResourceLocation loc = new ResourceLocation(string);
        File file = new File(FileUtil.RESOURCE_PACK_FOLDER, String.format("assets/%s/%s", loc.getNamespace(), loc.getPath()));

        return file.exists();
    }

    public static File getFileClient(String string) {
        ResourceLocation loc = new ResourceLocation(string);
        File file = new File(FileUtil.RESOURCE_PACK_FOLDER, String.format("assets/%s/%s", loc.getNamespace(), loc.getPath()));

        return file;
    }

    public static String quickLoadString(InputStream input) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }

    public static void writeFile(File outputFile, String output) throws IOException {
        if(!outputFile.exists()){
            outputFile.getParentFile().mkdirs();
            outputFile.createNewFile();
        }
        FileOutputStream outStream = new FileOutputStream(outputFile);
        try(Writer writer = new OutputStreamWriter(outStream, StandardCharsets.UTF_8.name())){
            writer.write(output);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
