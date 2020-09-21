/** Represents a Land Element and the Water it contains
 * @author Jet Hendricks
 */

public class Water{
    private float terrainHeight;
    private float waterDepth;
    
    /**Stores terrain height and water depth
     * @param waterDepth    depth of the water on the terrain
     * @param terrainHeight height of the terrain above the land
     */
    public Water(float height){
        waterDepth = 0;
        terrainHeight = height;
    }


    /**Method to add 3 units of water when a user clicks his mouse within this water item radius*/
    public void addWaterClick(){
        waterDepth += 0.03;
    }

    /**Remove a single unit of water */
    public void removeWater(){
        waterDepth -= 0.01;
    }

    /**Returns the total surface height of the land element */
    public float getSurfaceHeight(){
        return waterDepth + terrainHeight;
    }

    /**Returns the height of the terrain of the land element */
    public float getTerrainHeight(){
        return terrainHeight;
    }
    
    /**Returns a boolean true if this land item currently has water on it*/
    public boolean hasWater(){
        if (waterDepth > 0){
            return true;
        }
        return false;
    }


    /**Set this Water item to have no water */
    public void setNoWater(){
        this.waterDepth = 0;
    }

    /**Synchronized as it alters a variable which can cause race conditions. transfers a unit of water from w to this Water item
     * @param w Water item to take unit of water from
    */
    synchronized public void takeWater(Water w){
        w.removeWater();
        waterDepth += 0.01;
    }
}
