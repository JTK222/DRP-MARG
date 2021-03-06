package net.dark_roleplay.marg.util.texture;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TextureCache {

    private TextureCache parentCache = null;

    private Map<String, BufferedImage> imageCache = new HashMap<String, BufferedImage>();

    public TextureCache(){}

    public TextureCache(TextureCache parent){
        this.parentCache = parent;
    }

    public BufferedImage getCachedImage(String key){
        BufferedImage image = imageCache.get(key);
        if(image == null && parentCache != null)
            image = parentCache.getCachedImage(key);
        return image;
    }

    public void addImage(String key, BufferedImage image){
        this.imageCache.put(key, image);
    }

    public void clear(){
        this.imageCache.clear();
    }
}
