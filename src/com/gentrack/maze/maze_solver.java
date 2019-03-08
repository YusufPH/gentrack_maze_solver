package com.gentrack.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class maze_solver {

    private static final int[][] movement = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } }; // N, E, S, W

    public List<coordinate> solve(maze m) {
        List<coordinate> route = new ArrayList<>();
        if (explore(m, m.getStart().getX(), m.getStart().getY(), route))
        {
            return route;
        }
        return Collections.emptyList();
    }

    private boolean explore(maze m, int row, int col, List<coordinate> route) {

        if (m.isWall(row, col) || m.isExplored(row, col)) {
            return false;
        }
        else if (m.isEnd(row, col)) {
            return true;
        }

        route.add(new coordinate(row, col));
        m.setExplored(row, col, true);

        for (int[] direction : movement) {
            coordinate c = getNextCoordinate(m, row, col, direction[0], direction[1]);
            if (explore(m, c.getX(), c.getY(), route)) {
                return true;
            }
        }

        route.remove(route.size() - 1);
        return false;
    }

    private coordinate getNextCoordinate(maze m, int row, int col, int i, int j) {
        int wrapperCheck = m.isWrappedMovement(row + i, col +j);
        switch (wrapperCheck){
            case 0: return new coordinate((m.getHeight()-1), col + j);
            case 1: return new coordinate(0, col + j);
            case 2: return new coordinate(row + i, m.getWidth()-1);
            case 3: return new coordinate(row + i, 0);
        }
        return new coordinate(row + i, col + j);
    }
}
