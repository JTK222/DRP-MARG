package net.dark_roleplay.marg.impl.generators.textures;

public class TextureGenerator{ // implements IGenerator<TextureGenerator> {
//
//    public static final TextureCache globalCache = new TextureCache();
//
//    private int version = 0;
//    private IMaterialCondition materialRequirements;
//
//    private  TextureCache localCache;
//
//    private ResourceLocation[] requiredTextureLocs;
//    private TextureHolder[] requiredTextures;
//    private TextureTask[]  tasks;
//
//    private boolean wasSuccessfull = true;
//
//    public TextureGenerator(TextureGeneratorData data){
//        this.materialRequirements = new MargMaterialCondition(data.getMaterial());
//        this.version = data.getGeneratorVersion();
//        this.requiredTextureLocs = new ResourceLocation[data.getRequiredTextures().length];
//        for(int i = 0; i < this.requiredTextureLocs.length; i++){
//            this.requiredTextureLocs[i] = new ResourceLocation(data.getRequiredTextures()[i]);
//        }
//        this.tasks = new TextureTask[data.getTasks().length];
//        for(int i = 0; i < this.tasks.length; i++){
//            this.tasks[i] = new TextureTask(data.getTasks()[i]);
//        }
//    }
//
//    @Override
//    public int getVersion() { return this.version; }
//
//    @Override
//    public boolean needsToGenerate(IMaterial material) { return true; }
//
//    @Override
//    public TextureGenerator prepareGenerator() {
//        if(!this.wasSuccessfull) return this;
//        this.requiredTextures = Arrays.stream(requiredTextureLocs).parallel().map(loc -> {
//            try {
//                return Minecraft.getInstance().getResourceManager().getResource(loc);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }).map(resource -> {
//            if(resource == null) return null; //TODO Replace null with error texture
//            TextureData data = TextureDataIO.loadDataFromResources(resource);
//            if(data == null) return null;
//            else return new TextureHolder(data);
//        }).toArray(size -> new TextureHolder[size]);
//
//        this.localCache = new TextureCache(globalCache);
//        return this;
//    }
//
//    @Override
//    public void generate() {
//        if(!this.wasSuccessfull) return;
//        Set<IMaterial> materials = materialRequirements.getMaterials();
//        for(TextureTask task : this.tasks)task.generate(this.requiredTextures, this.localCache, globalCache, materials);
//        this.localCache.clear();
//    }
}
