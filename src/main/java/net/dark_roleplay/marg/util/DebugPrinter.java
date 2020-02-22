package net.dark_roleplay.marg.util;

import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugPrinter {

    public static final Logger LOGGER = LogManager.getLogger("MARG - Debug Printer");

    public static void logMaterial(IMaterial material){
        LOGGER.debug("#================================#");
        LOGGER.debug(String.format("Type: '%s'", material.getType().getTypeName()));
        LOGGER.debug(String.format("Name: '%s'", material.getName()));
        LOGGER.debug("Graphics: {");
        logGraphicProvider(material.getGraphicsProvider());
        LOGGER.debug("}");
    }

    private static void logGraphicProvider(IGraphicsProvider prov){
        LOGGER.debug("| Textures");
        for(String key : prov.getTextures()){
            LOGGER.debug(String.format("| '%s': '%s'", key, prov.getTextureLocation(key)));
        }
        LOGGER.debug("| Tints");
        for(String key : prov.getTints()){
            LOGGER.debug(String.format("| '%s': '%d'", key, prov.getTint(key)));
        }
    }
}
