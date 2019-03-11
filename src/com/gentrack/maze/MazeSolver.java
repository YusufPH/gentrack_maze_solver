package com.gentrack.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeSolver {

    private static final int[][] movement = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } }; // N, E, S, W

    public List<Coordinate> solve(Maze m) {
        List<Coordinate> route = new ArrayList<>();
        if (exploreMaze(m, m.getStart().getX(), m.getStart().getY(), route))
        {
            return route;
        }
        return Collections.emptyList(); //case for unsolvable maze
    }

    private boolean exploreMaze(Maze m, int row, int col, List<Coordinate> route) {

        if (m.isWall(row, col) || m.isExplored(row, col)) {
            return false;
        }
        else if (m.isEnd(row, col)) {
            return true;
        }

        route.add(new Coordinate(row, col));
        m.setExplored(row, col, true);

        for (int[] direction : movement) {
            Coordinate c = getNextCoordinate(m, row, col, direction[0], direction[1]);
            if (exploreMaze(m, c.getX(), c.getY(), route)) {
                return true;
            }
        }
        route.remove(route.size() - 1);
        return false;
    }

    private Coordinate getNextCoordinate(Maze m, int row, int col, int i, int j) {
        int wrapperCheck = m.isWrappedMovement(row + i, col +j);
        switch (wrapperCheck){
            case 0: return new Coordinate((m.getHeight()-1), col + j); //x=0 -> x=max (prevent x<0)
            case 1: return new Coordinate(0, col + j); //x=max -> x=0 (prevent x>max)
            case 2: return new Coordinate(row + i, m.getWidth()-1); //y=0 -> y=max (prevent y<0)
            case 3: return new Coordinate(row + i, 0); //y=max -> y=0 (prevent y>max1)
        }
        return new Coordinate(row + i, col + j);
    }
}
