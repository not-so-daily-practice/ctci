package chapter_7_Mathematics_Probability;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

//Given two 2D squares, find a line that would cut these squares in half
//Assume the top/bottom run parallel to x-axis
public class Question_7_5 {

    public static void main(String[] args) {
        Square s1 = new Square(50, 50, 50);
        Square s2 = new Square(250, 100, 100);
        Line l = centerSquares(s1, s2);
        draw(s1, s2, l);
    }

    // returns line that intersects the center of both squares
    public static Line centerSquares(Square s1, Square s2) {
        System.out.println(s1.center().toString());
        System.out.println(s2.center().toString());
        return new Line(s1.center(), s2.center());
    }

    @SuppressWarnings("serial")
    public static void draw(Square s1, Square s2, Line l) {
        JFrame frame = new JFrame("Divide Squares");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D ga = (Graphics2D) g;

                // Draw the red square
                ga.setColor(Color.RED);
                ga.draw(new Rectangle2D.Double(s1.p.x, s1.p.y, s1.radius * 2, s1.radius * 2));

                // Draw the blue square
                ga.setColor(Color.BLUE);
                ga.draw(new Rectangle2D.Double(s2.p.x, s2.p.y, s2.radius * 2, s2.radius * 2));

                ga.drawLine(l.p1.x, l.p1.y, l.p2.x, l.p2.y);
            }
        }, BorderLayout.CENTER);

        frame.pack();
        frame.setSize(new Dimension(500, 500));
        frame.setVisible(true);
    }

    public static Point intersect(Point c1, Point c2, int radius) {
        int xOrient = c1.x < c2.x ? -1 : 1;// x-orientation
        int yOrient = c1.y < c2.y ? -1 : 1; // y-orientation

        // check for same x-value, since this is a vertical line
        if (c1.x == c2.x) {
            return new Point(c1.x, c1.y + yOrient * radius);
        }

        // slope
        double slope = (c1.y - c2.y) / (c1.x - c2.x);
        // intersection point
        double x = 0;// intersection x
        double y = 0;// intersection y

        if (Math.abs(slope) == 1) {
            x = c1.x + xOrient * radius;
            y = c1.y + yOrient * radius;
        } else if (Math.abs(slope) < 1) {
            // shallow
            x = c1.x + xOrient * radius;
            y = slope * (x - c1.x) + c1.y;
        } else {
            // steep
            y = c1.y + yOrient * radius;
            x = (y - c1.y) / slope + c1.x;
        }

        return new Point((int) x, (int) y);
    }
}

class Point {
    public int x, y;// coordinates

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isEqual(Point p2) {
        return this.x == p2.x && this.y == p2.y;
    }

    @Override
    public String toString() {
        return x + "\t" + y;
    }
}

class Line {
    public Point p1, p2;// endpoints

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public String toString() {
        return (p1 + "\t" + p2);
    }

}

class Square {
    public Point p;// top left corner
    public int radius;// distance from center to edge

    public Square(int x, int y, int r) {
        p = new Point(x, y);
        this.radius = r;
    }

    public Point center() {
        return new Point(p.x + radius, p.y + radius);
    }
}
