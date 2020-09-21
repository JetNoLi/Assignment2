/** A utility class of static functions used by multiple classes
 * @authour Jet Hendricks
 */ 

public class Util{

    /** Returns 1D index which corresponds to a 2D array
     * @param x     2D array row index 
     * @param y     2D array column index 
     * @param dimY  Number of columns i.e. the width necessary for calculation
    */
    public static int getLinearIndex(int x, int y, int dimY){
        return y + x*(dimY);
    }


    /** Returns a boolean which is true if the index positions are at the border of the array
     * @param x     2D array row index 
     * @param y     2D array column index 
     * @param dimX  Number of rows i.e. the height necessary for calculation
     * @param dimY  Number of columns i.e. the width necessary for calculation
    */
    public static boolean atBorder(int x, int y, int dimX, int dimY){

        if (x == 0 || x == dimX-1 || y == dimY -1 || y == 0){
            return true;
        }
    
        return false;
    }

    /** Returns a boolean which is true if the index positions are out of bounds
     * @param x     2D array row index 
     * @param y     2D array column index 
     * @param dimX  Number of rows i.e. the height necessary for calculation
     * @param dimY  Number of columns i.e. the width necessary for calculation
    */
    public static boolean isOut(int x, int y, int dimX, int dimY){

        if (x < 0 || x > dimX-1 || y > dimY -1 || y < 0){
            return true;
        }
    
        return false;
    }
}
