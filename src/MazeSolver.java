import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;
import svu.csc213.Dialog;

import java.awt.*;
import java.util.ArrayList;

public class MazeSolver extends GraphicsProgram {

    private Maze maze;
    private double pause;
    private Tile startTile;
    private Tile goalTile;
    private ArrayList<Tile> visited = new ArrayList<>();



    public void init() {
        setBackground(Color.white);
        pause = Dialog.getDouble(this, "Enter the delay between steps in milliseconds");
        maze = new Maze(600,1200, this);
        add(maze, 10, 10);
    }


    public void run() {
        String rowRange = String.format("%d-%d:", 0, maze.getRows() -1);
        String colRange = String.format("%d-%d:", 0, maze.getCols() -1);

        int r0 = 0;
        int c0 = 0;

        int r1 = maze.getRows()-1;
        int c1 = maze.getCols()-1;

        startTile = maze.getTile(r0, c0);
        goalTile = maze.getTile(r1, c1);

        maze.getTile(r0, c0).fillColor(Color.green);
        maze.getTile(r1, c1).fillColor(Color.red);

        solve(startTile, goalTile, visited);

    }



    private void solve(Tile startTile, Tile goalTile, ArrayList<Tile> visited){
        visited.add(startTile);



    if (startTile == goalTile){
        Dialog.showMessage("Done!");
        return;
    }


    if (!startTile.hasWall(0) && !visited.contains(startTile.getNeighbor(0))){
        startTile.fillColor(Color.blue);
        pause(pause);
        solve(startTile.getNeighbor(0), goalTile, visited);
        startTile.fillColor(Color.lightGray);
        pause(pause);

    }
    if (!startTile.hasWall(1) && !visited.contains(startTile.getNeighbor(1))){
        startTile.fillColor(Color.blue);
        pause(pause);
        solve(startTile.getNeighbor(1), goalTile, visited);
        startTile.fillColor(Color.lightGray);
        pause(pause);

    }
    if (!startTile.hasWall(2) && !visited.contains(startTile.getNeighbor(2))){
        startTile.fillColor(Color.blue);
        pause(pause);
        solve(startTile.getNeighbor(2), goalTile, visited);
        startTile.fillColor(Color.lightGray);
        pause(pause);

    }
    if (!startTile.hasWall(3) && !visited.contains(startTile.getNeighbor(3))){
        startTile.fillColor(Color.blue);
        pause(pause);
        solve(startTile.getNeighbor(3), goalTile, visited);
        startTile.fillColor(Color.lightGray);
        pause(pause);

    }




    }

    public static void main(String[] args) {
        new MazeSolver().start();
    }
}
