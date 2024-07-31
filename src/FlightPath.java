import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class FlightPath extends JPanel {

    private final List<Point[]> flightPaths;

    public FlightPath(List<Point[]> flightPaths) {
        this.flightPaths = flightPaths;
        adjustPathsToAvoidIntersections();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        for (Point[] path : flightPaths) {
            for (int i = 0; i < path.length - 1; i++) {
                Point start = path[i];
                Point end = path[i + 1];
                g2d.setColor(Color.BLACK);
                g2d.draw(new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY()));
            }
        }
    }

    private void adjustPathsToAvoidIntersections() {
        for (int i = 0; i < flightPaths.size(); i++) {
            Point[] path1 = flightPaths.get(i);
            for (int j = i + 1; j < flightPaths.size(); j++) {
                Point[] path2 = flightPaths.get(j);
                for (int k = 0; k < path1.length - 1; k++) {
                    for (int l = 0; l < path2.length - 1; l++) {
                        if (linesIntersect(path1[k], path1[k + 1], path2[l], path2[l + 1])) {
                            adjustPath(path2, l);
                        }
                    }
                }
            }
        }
    }

    private boolean linesIntersect(Point p1, Point p2, Point p3, Point p4) {
        return Line2D.linesIntersect(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY(), p4.getX(),
                p4.getY());
    }

    private void adjustPath(Point[] path, int index) {
        path[index].translate(10, 10);
    }

    public static void main(String[] args) {
        List<Point[]> flightPaths = new ArrayList<>();
        flightPaths.add(new Point[] { new Point(100, 100), new Point(200, 200), new Point(300, 300) });
        flightPaths.add(new Point[] { new Point(100, 100), new Point(200, 400), new Point(300, 200) });
        flightPaths.add(new Point[] { new Point(100, 100), new Point(400, 200), new Point(300, 400) });
        JFrame frame = new JFrame("Flight Path Visualizer");
        FlightPath panel = new FlightPath(flightPaths);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
