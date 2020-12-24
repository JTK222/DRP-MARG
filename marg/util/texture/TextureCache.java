package net.dark_roleplay.marg.util.texture;

import net.dark_roleplay.marg.client.textures.TextureHolder;

import java.util.HashMap;
import java.util.Map;

public class TextureCache {

    private TextureCache parentCache = null;

    private Map<String, TextureHolder> imageCache = new HashMap<String, TextureHolder>();

    public TextureCache(){}

    public TextureCache(TextureCache parent){
        this.parentCache = parent;
    }

    public TextureHolder getCachedImage(String key){
        TextureHolder image = imageCache.get(key);
        if(image == null && parentCache != null)
            image = parentCache.getCachedImage(key);
        return image;
    }

    public void addImage(String key, TextureHolder image){
        this.imageCache.put(key, image);
    }

    public void clear(){
        this.imageCache.clear();
    }
}
