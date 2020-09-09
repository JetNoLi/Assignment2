public class Water{
    private int x, y; //2D position
    private float terrainHeight;
    private float waterDepth;
    
    public Water(int x, int y, float height){
        this.x = x;
        this.y = y;
        waterDepth = 0;
        terrainHeight = height;
    }

    public void addWater(){
        waterDepth += 0.3;
    }

    public void removeWater(){
        waterDepth -= 0.1;
    }

    public float getSurfaceHeight(){
        return waterDepth + terrainHeight;
    }

    public float getTerrainHeight(){
        return terrainHeight;
    }
    
}
