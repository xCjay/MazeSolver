import acm.graphics.GCompound;
import acm.util.RandomGenerator;

public class Maze extends GCompound {

    private Tile[][] floor;
    private int rows;
    private int cols;
    private MazeSolver parent;

    /**
     * Construct a rectangular maze with the specified number of rows and columns.
     * The paths through the maze are randomly generated. It is not guaranteed that
     * any two arbitrary tiles will have a valid path connecting them.
     * @param rows
     * @param cols
     * @param parent
     */
    public Maze(int rows, int cols, MazeSolver parent){
        this.rows = rows;
        this.cols = cols;
        this.parent = parent;

        floor = new Tile[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                floor[i][j] = new Tile(this, i, j);
                add(floor[i][j], j * floor[i][j].getWidth(),
                        i * floor[i][j].getHeight());
            }
        }

        this.makeHorizontalWall(0,0,cols-1, Tile.NORTH);
        this.makeHorizontalWall(rows -1, 0, cols -1, Tile.SOUTH);
        this.makeVerticalWall(0, 0, rows -1, Tile.WEST);
        this.makeVerticalWall(cols - 1, 0, rows -1, Tile.EAST);

        divideMaze(0, 0, rows -1, cols -1);
    }



    public Tile getTile(int row, int col) {
        if(row < 0 || row >= rows || col < 0 || col >= cols){
            return null;
        }
        return floor[row][col];
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    private void makeHorizontalWall(int row, int startCol, int endCol, int direction){
        for(int i = startCol; i <= endCol; i++){
            getTile(row, i).setWall(direction, true);
        }
    }

    private void makeVerticalWall(int col, int startRow, int endRow, int direction){
        for(int i = startRow; i <= endRow; i++){
            getTile(i, col).setWall(direction, true);
        }
    }

    private void makeRandomHOpening(int row, int startCol, int endCol, int direction){
        if(endCol >= startCol) {
            getTile(row, RandomGenerator.getInstance().nextInt(startCol, endCol -1)).setWall(direction, false);
        }
    }

    private void makeRandomVOpening(int col, int startRow, int endRow, int direction){
        if(endRow >= startRow) {
            getTile(RandomGenerator.getInstance().nextInt(startRow, endRow -1), col).setWall(direction, false);
        }
    }

    /**
     * Constructs the maze by recursively dividing an area into four quadrants.
     *
     * On each division step, the vertical and horizontal wall that defines the quadrants
     * will be drawn, and a random opening is made in each of the four quadrants.
     *
     * Next, the method will call itself (recur) on each quadrant.
     *
     * The method will terminate when the width and
     * height of the area to divide is equal to 0.
     *
     * The parameters describe a starting tile coordinate and an ending tile coordinate.
     * @param r0 starting row
     * @param c0 starting column
     * @param r1 ending row
     * @param c1 ending column
     */
    private void divideMaze(int r0, int c0, int r1, int c1){
        //base case, if we have divided the maze to the point where it cannot
        //be subdivided any more, stop.
        if(r0 == r1 && c0 == c1){
            return;
        }

        ///region Subdivision

        int randomRow = Math.max(r0, RandomGenerator.getInstance().nextInt(r0, r1));
        int randomCol = Math.max(c0, RandomGenerator.getInstance().nextInt(c0,c1));


        if(c1 - c0 >= 0){
            makeHorizontalWall(randomRow, c0, c1, Tile.SOUTH);

            if(randomRow < rows -1){
                makeRandomHOpening(randomRow, c0, c1, Tile.SOUTH);
                makeRandomHOpening(randomRow, randomCol, c1, Tile.SOUTH);
            }
        }

        if(r1 - r0 >= 0){
            makeVerticalWall(randomCol, r0, r1, Tile.EAST);

            //make sure you don't poke holes in the border
            if(randomCol < cols -1){
                makeRandomVOpening(randomCol, r0, randomRow, Tile.EAST);
                makeRandomVOpening(randomCol, randomRow, r1, Tile.EAST);
            }
        }

        ///endregion

        //general cases
        if (randomRow - r0 > 1) {
            if(randomCol - c0 > 1){
                divideMaze(r0, c0, randomRow, randomCol);
            }
            if(c1 - randomCol > 1){
                divideMaze(r0, randomCol, randomRow, c1);
            }
        }

        if(r1 - randomRow > 1){
            if(c1 - randomCol > 1){
                divideMaze(randomRow, randomCol, r1, c1);
            }
            if(randomCol - c0 > 1){
                divideMaze(randomRow, c0, r1, randomCol);
            }
        }
    }
}
