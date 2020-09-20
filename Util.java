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

    public static void reset(Terrain land){
        for(int i = 0; i < land.dimx; i++){
            for (int j = 0; j < land.dimy; j++){
                if (land.surface[i][j].hasWater()){
                    land.surface[i][j].setNoWater();
                }
            }
        }
    }
}
