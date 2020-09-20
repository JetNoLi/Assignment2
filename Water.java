public class Water{
    private float terrainHeight;
    private float waterDepth;
    
    public Water(float height){
        waterDepth = 0;
        terrainHeight = height;
    }

    public void addWaterClick(){
        waterDepth += 0.03;
    }

    public void removeWater(){
        waterDepth -= 0.01;
    }

    public float getSurfaceHeight(){
        return waterDepth + terrainHeight;
    }

    public float getTerrainHeight(){
        return terrainHeight;
    }
    
    public boolean hasWater(){
        if (waterDepth > 0){
            return true;
        }
        return false;
    }

    public void setNoWater(){
        this.waterDepth = 0;
    }

    synchronized public void takeWater(Water w){
        w.removeWater();
        waterDepth += 0.01;
    }
}
