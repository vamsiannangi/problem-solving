import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class Position {
    int row, col;

    Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Position))
            return false;
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}

public class Chessboard {

    private static final int[][] DIRECTIONS = {
            { 0, 1 },
            { 1, 0 },
            { 0, -1 },
            { -1, 0 }
    };

    private static Set<Position> soldiers = new HashSet<>();
    private static Position castleStart;
    private static List<List<Position>> uniquePaths = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of soldiers: ");
        int numSoldiers = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        for (int i = 1; i <= numSoldiers; i++) {
            System.out.print("Enter coordinates for soldier " + i + ": ");
            String[] coordinates = scanner.nextLine().split(",");
            int x = Integer.parseInt(coordinates[0].trim());
            int y = Integer.parseInt(coordinates[1].trim());
            soldiers.add(new Position(x, y));
        }

        System.out.print("Enter the coordinates for your “special” castle: ");
        String[] castleCoordinates = scanner.nextLine().split(",");
        int startX = Integer.parseInt(castleCoordinates[0].trim());
        int startY = Integer.parseInt(castleCoordinates[1].trim());
        castleStart = new Position(startX, startY);

        List<Position> currentPath = new ArrayList<>();
        currentPath.add(castleStart);
        findPaths(castleStart, 0, currentPath);

        System.out.println("Thanks. There are " + uniquePaths.size() + " unique paths for your ‘special_castle’");
        int pathNumber = 1;
        for (List<Position> path : uniquePaths) {
            System.out.println("Path " + pathNumber + ":");
            System.out.println("=======");
            for (Position pos : path) {
                System.out.print("Kill " + pos + ". Turn Left\n");
            }
            System.out.println("Arrive " + castleStart);
            pathNumber++;
        }
    }

    private static void findPaths(Position current, int direction, List<Position> currentPath) {
        if (current.equals(castleStart) && currentPath.size() > 1) {
            uniquePaths.add(new ArrayList<>(currentPath));
            return;
        }

        for (int i = 0; i < 4; i++) {
            int newDirection = (direction + i) % 4;
            Position next = moveInDirection(current, newDirection);

            if (soldiers.contains(next)) {
                currentPath.add(next);
                soldiers.remove(next);
                findPaths(next, (newDirection + 3) % 4, currentPath);
                soldiers.add(next);
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }

    private static Position moveInDirection(Position pos, int direction) {
        int newRow = pos.row + DIRECTIONS[direction][0];
        int newCol = pos.col + DIRECTIONS[direction][1];
        return new Position(newRow, newCol);
    }
}
