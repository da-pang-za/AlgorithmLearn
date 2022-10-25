package com.dpz.template;

public class Geometry {
    static class Pos {
        public double x;
        public double y;

        Pos(double a, double b) {
            x = a;
            y = b;
        }
    }

    static double dist(Pos a, Pos b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
