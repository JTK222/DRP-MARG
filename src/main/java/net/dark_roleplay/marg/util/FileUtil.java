package net.dark_roleplay.marg.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {

    public static File RESOURCE_PACK_FOLDER;
    public static File DATA_PACK_FOLDER;

    public static void setupFolders(){
        File modData = new File("./mod_data/marg/");
        modData.mkdirs();

        if(FMLEnvironment.dist == Dist.CLIENT){
            RESOURCE_PACK_FOLDER = new File(modData, "resource_pack");
            
            new File(RESOURCE_PACK_FOLDER, "assets").mkdirs();

            try {
                if (!new File(RESOURCE_PACK_FOLDER, "pack.mcmeta").exists())
                    Files.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("/assets/mcmeta_template"), new File(RESOURCE_PACK_FOLDER, "pack.mcmeta").toPath());
                if (!new File(RESOURCE_PACK_FOLDER, "pack.png").exists())
                    Files.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("/assets/pack.png"), new File(RESOURCE_PACK_FOLDER, "pack.png").toPath());
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        DATA_PACK_FOLDER = new File(modData, "data_pack");
        new File(DATA_PACK_FOLDER, "data").mkdirs();

        try {
            if( !new File(DATA_PACK_FOLDER, "pack.mcmeta").exists()) Files.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("/data/mcmeta_template"), new File(DATA_PACK_FOLDER, "pack.mcmeta").toPath());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
