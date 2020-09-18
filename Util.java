public class Util{
    
    public static int[] getLowestNeighbour(Terrain land, int x, int y){
        
        int[] neighbours = getNeighbours(x,y, land.dimy); //Linearized list of neighbours including self

        Water current = land.surface[x][y]; //chosen water pixel

        float minHeight = current.getSurfaceHeight(); //set very high, so as to immediately be replaced by first neighbour
        int[] lowestNeighbour = {x,y};
        
        for (int i = 0; i < land.dim(); i++){
            int index = land.permute.get(i);

            if (getNeighbourIndex(neighbours, index) != 10){
                int[] ind = new int[2];
                land.locate(index,ind);

                if (atBorder(ind[0], ind[1], land.dimx, land.dimy)){
                    continue;
                }

                float compHeight = land.surface[ind[0]][ind[1]].getSurfaceHeight();
                
                if (compHeight < minHeight){
                    minHeight = compHeight;
                    lowestNeighbour = ind;
                }
            }    
        }
        return lowestNeighbour;
        
    }

    // returns Linearized Indexes of all neighbours
    public static int[] getNeighbours(int x, int y, int dimY){
        int[] neighbours = new int [8];
        int count = 0;

        for (int i = -1; i < 2; i++){
            for (int j = -1; j<2;j++){

                if (i == 0 && j == 0){
                    continue;
                }

                neighbours[count] = getLinearIndex(x+i,y+j,dimY);
                count++;
            } 
        }

        return neighbours;

    }

    // returns index in the range 0 - 7 if neighbour, else returns 10
    public static int getNeighbourIndex(int[] neighbours, int i){
        for (int j = 0; j < 8; j++){

            if (i == neighbours[j]){
                return j;
                
            }
        }
        return 10;

    }

    public static int getLinearIndex(int x, int y, int dimY){
        return y + x*(dimY);
    }

    public  static boolean atBorder(int x, int y, int dimX, int dimY){

        if (x == 0 || x == dimX-1 || y == dimY -1 || y == 0){
            return true;
        }
    
        return false;
    }
    
}
