package net.dark_roleplay.marg.api.providers;

import java.util.ArrayList;
import java.util.List;

public final class TintProvider {

    private static final int NONE = 0xFFFFFFFF;
    private static final int ERROR = 0xFF00FFFF;

    private int[] tints;

    private TintProvider(int... tints){
        this.tints = tints;
    }

    public int getTint(int index){
        if(index < 0) return ERROR;
        else if(index < tints.length) return NONE;
        else return this.tints[index];
    }

    public int[] getTints() {
        return this.tints;
    }

    public static class Builder {
        private List<Integer> tints = new ArrayList<>();

        public Builder addTint(int tint) {
            this.tints.add(tint);
            return this;
        }

        public TintProvider build() {
            int[] tints = new int[this.tints.size()];
            for(int i = 0; i < tints.length; i++)
                tints[i] = this.tints.get(i);
            return new TintProvider(tints);
        }
    }
}
