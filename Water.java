import java.awt.Graphics;

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

    public void takeWater(Water w){
        w.removeWater();
        waterDepth += 0.01;

        //System.out.println(w.getSurfaceHeight());
        //System.out.println(this.getSurfaceHeight());
    }
}
