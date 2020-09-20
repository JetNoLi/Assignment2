public class sim implements Runnable{

    //boolean on;
    int hi;
    int lo;
    Terrain land;

    public sim(int lo, int hi, Terrain land){
        this.land = land;
        this.lo = lo;
        this.hi = hi;
        
    }

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

    @Override
    public void run() {
    
        for (int i  = lo; i < hi; i++){
            int[] ind = new int[2]; 
            land.locate(land.permute.get(i),ind);
        
            Water current = land.surface[ind[0]][ind[1]];
        
            if (current.hasWater()){
                int[] lowestNeighbourIndices = this.getLowestNeighbour(land, ind[0], ind[1]);
                                        
                if (lowestNeighbourIndices == ind){
                    continue;
                }
                            
                land.moveWater(ind, lowestNeighbourIndices);
            }
        }
    }
}
