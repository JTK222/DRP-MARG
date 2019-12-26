package net.dark_roleplay.marg.util.gson;

import com.google.gson.Gson;

public class GsonWrapper {

    private Gson gson;

    public void setGson(Gson gson){
        this.gson = gson;
    }

    public Gson getGson(){
        return this.gson;
    }
}
