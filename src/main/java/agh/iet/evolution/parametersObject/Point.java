package agh.iet.evolution.parametersObject;

import java.util.Objects;

public class Point {

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean precedes(Point other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Point other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Point upperRight(Point other){
        return new Point(Math.max(x, other.x), Math.max(y, other.y));
    }

    public Point lowerLeft(Point other){
        return new Point(Math.min(x, other.x), Math.min(y, other.y));
    }

    public Point add(Point other){
        return new Point(this.x + other.x, this.y + other.y);
    }

    public Point subtract(Point other){
        return new Point(this.x - other.x, this.y - other.y);
    }

    public Point opposite(){
        return new Point( - this.x, - this.y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
