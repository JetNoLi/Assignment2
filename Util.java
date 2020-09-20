public class Util{

    // returns Linearized Indexes of all neighbours
    public static int[] getNeighbours(int x, int y, int dimY){
        int[] neighbours = new int [8];
        int count = 0;

        for (int i = -1; i < 2; i++){
            for (int j = -1; j<2;j++){

                if (i == 0 && j == 0){
                    continue;
                }

                neighbours[count] = 
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

    public static boolean atBorder(int x, int y, int dimX, int dimY){

        if (x == 0 || x == dimX-1 || y == dimY -1 || y == 0){
            return true;
        }
    
        return false;
    }

    public static boolean isOut(int x, int y, int dimX, int dimY){

        if (x < 0 || x > dimX-1 || y > dimY -1 || y < 0){
            return true;
        }
    
        return false;
    }

    static int[] getLowestNeighbour(Terrain land, int x, int y){

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
        System.out.println(lowestNeighbour[0]);
        return lowestNeighbour;
    
    }

    static void simulate(Terrain land){
        for (int i  = 0; i < land.dim(); i++){
            int[] ind = new int[2]; 
			land.locate(land.permute.get(i),ind);

			Water current = land.surface[ind[0]][ind[1]];

			if (current.hasWater()){
				int[] lowestNeighbourIndices = Util.getLowestNeighbour(land, ind[0], ind[1]);
								
				if (lowestNeighbourIndices == ind){
					continue;
				}
					
				land.moveWater(ind, lowestNeighbourIndices);
			}
        }
    }

    static void delay(){
        int i = 0;

        for (int j = 0; j <1000; j++ ){
            i+= j;
        }
    }
    
}
