/** A Threaded class which will simulate water flowing over the Terrain object
 * @author Jet Hendricks
 */
public class sim implements Runnable{

    int hi;
    int lo;
    Terrain land;

    /**Stores the bounds of the permuted list to simulate for as well as the Terrain object to simulate
     * @param lo    Lower bound
     * @param hi    Upper bound
     * @param land  Terrain object to simulate
    */
    public sim(int lo, int hi, Terrain land){
        this.land = land;
        this.lo = lo;
        this.hi = hi;
        
    }


    /** Returns an array with the row and column index of the lowest neighbour for the given 2D index 
     * @param land  Terrain Object to simulate
     * @param x     2D row index
     * @param y     2D column index
    */
    public  int[] getLowestNeighbour(Terrain land, int x, int y){

        Water current = land.surface[x][y]; //chosen water pixel

        float minHeight = current.getSurfaceHeight(); 
        int[] lowestNeighbour = {x,y};

        for (int i = -1; i < 2; i++){
            for (int j = -1; j<2;j++){
    
                if (i == 0 && j == 0){
                    continue;

                } 

                if (Util.isOut(x+i,y+j, land.getDimX(), land.getDimY())){
                    continue;
                }

                int[] ind = {x+i, y+j};

                float compHeight = land.surface[ind[0]][ind[1]].getSurfaceHeight();
                
                if (compHeight < minHeight){
                    minHeight = compHeight;
                    lowestNeighbour = ind;
                }
            }    
        }
        return lowestNeighbour;  
    }


    /**Method which is executed on sim.start() does the actual simulation of moving the water*/
    @Override
    public void run() {
    
        for (int i  = lo; i < hi; i++){
            int[] ind = new int[2]; 
            land.locate(land.permute.get(i),ind);
        
            Water current = land.surface[ind[0]][ind[1]];
        
            if (current.hasWater()){
                int[] lowestNeighbourIndices = this.getLowestNeighbour(land, ind[0], ind[1]);
                                        
                if (lowestNeighbourIndices == ind){ //in a basin currently
                    continue;
                }
                            
                land.moveWater(ind, lowestNeighbourIndices);
            }
        }
    }
}
