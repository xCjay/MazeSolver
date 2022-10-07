import acm.graphics.GCompound;
import acm.graphics.GLine;
import acm.graphics.GRect;

import java.awt.*;

import static java.awt.Color.*;

public class Tile extends GCompound {

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    public static final double TILEW = 3; // Width of Tile
    public static final double TILEH = 3; // Height of Tile

    private boolean[] walls; // keeps track of whether there is a wall at a given index
    private Maze maze; // the Maze object that this tile belongs to
    private int row; // the row in the maze that this Tile is in
    private int col; // the column in the maze that this Tile is in

    private GRect rect;
    private GLine northWall;
    private GLine eastWall;
    private GLine southWall;
    private GLine westWall;


    public Tile(Maze maze, int row, int col){
        this(maze, row, col, false, false, false, false);
    }

    public Tile(Maze maze, int row, int col, boolean nw, boolean ew, boolean sw, boolean ww){



        this.maze = maze;
        this.row = row;
        this.col = col;

        // initalize boolean array
        walls = new boolean[4];
        walls[NORTH] = nw;
        walls[EAST] = ew;
        walls[SOUTH] = sw;
        walls[WEST] = ww;

        // the GRect represents the tile itself
        rect = new GRect(1, 1, TILEW-1, TILEH-1);
        rect.setColor(Color.WHITE);
        add(rect);

        // the GLines that represent the walls
        northWall = new GLine(0,0, TILEW, 0);
        add(northWall);

        eastWall = new GLine(TILEW, 0, TILEW, TILEH);
        add(eastWall);

        southWall = new GLine(TILEW, TILEH, 0, TILEH);
        add(southWall);

        westWall = new GLine(0, TILEH, 0, 0);
        add(westWall);

        northWall.setColor(gray);
        eastWall.setColor(gray);
        southWall.setColor(gray);
        westWall.setColor(gray);

        drawTile();
    }


    /**
     * Makes the walls visible
     */
    public void drawTile(){
        northWall.setVisible(false);
        eastWall.setVisible(false);
        southWall.setVisible(false);
        westWall.setVisible(false);

        northWall.setVisible(hasWall(NORTH));
        eastWall.setVisible(hasWall(EAST));
        southWall.setVisible(hasWall(SOUTH));
        westWall.setVisible(hasWall(WEST));

        this.repaint();
    }


    /**
     * returns the opposite value for a direction integer
     * @param dir non-negative integer, 0-3
     * @return the opposite direction value
     */
    public int getOppDir(int dir){
        return (dir + 2) % 4;
    }


    /**
     * Determine if a tile has a wall in a given direction
     * @param dir non-negative integer
     * @return true if wall exists at direction
     */
    public boolean hasWall(int dir){
        return walls[dir];
    }


    /**
     * Color in the "rect" object
     * @param color The color to use for painting Tiles
     */
    public void fillColor(Color color){
        rect.setColor(color);
        rect.setFillColor(color);
        rect.setFilled(true);
    }

    /**
     * Enables or disables walls in a given direction
     * @param direction Target wall
     * @param status true to enable, false to disable
     */
    public void setWall(int direction, boolean status) {
        if (status) {

            if (!hasWall(direction)) {
                walls[direction] = true;

                if (hasNeighbor(direction)) {
                    getNeighbor(direction).setWall(getOppDir(direction), status);
                }

                drawTile();

            }

        } else {

            if (hasWall(direction)) {
                walls[direction] = false;

                if (hasNeighbor(direction)) {
                    getNeighbor(direction).setWall(getOppDir(direction), status);
                }

                drawTile();
            }

        }
    }

    /**
     * Finds neighboring tile (if it exists) in a given direction
     * @param dir The direction to check in.
     * @return a Tile if not null
     */
    public Tile getNeighbor(int dir){
        switch (dir){
            case NORTH:
                return maze.getTile(row-1, col);

            case EAST:
                return maze.getTile(row, col+1);

            case SOUTH:
                return maze.getTile(row+1, col);

            case WEST:
                return maze.getTile(row, col-1);

            default:
                return null;
        }
    }


    /**
     * Does the wall have a neighbor in a given
     * @param dir where to look.
     * @return true if neighbor exists
     */
    public boolean hasNeighbor( int dir){
        return getNeighbor(dir) != null;
    }


}
