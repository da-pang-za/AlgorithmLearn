package com.dpz.template;

import static com.dpz.template.Const.*;
import static com.dpz.template.Geometry.*;

public class Search {
    /**
     * 模拟退火  简称sa
     * 需要保证函数具有连续性
     */
    static class SimulatedAnnealing {
        double ans = Double.MAX_VALUE;
        Pos[] ps;
        int n;

        void go() {
            n = ni();
            ps = new Pos[n];
            for (int i = 0; i < n; i++) ps[i] = new Pos(nd(), nd());

            for (int i = 0; i < 100; i++) sa();
            out.println(Math.round(ans));
        }

        void sa() {
            Pos cur = new Pos(rand(0, 10000), rand(0, 10000));
            for (double t = 1e4; t > 1e-4; t *= 0.9) {
                Pos np = new Pos(rand(cur.x - t, cur.x + t), rand(cur.y - t, cur.y + t));
                double dt = calc(np) - calc(cur);
                if (Math.exp(-dt / t) > rand(0, 1)) cur = np;//dt<0一定跳 dt>0概率跳且dt越大跳的概率越小
            }
        }

        double calc(Pos p) {
            double res = 0;
            for (int i = 0; i < n; i++)
                res += dist(p, ps[i]);
            ans = Math.min(ans, res);
            return res;
        }

        double rand(double l, double r) {
            return Math.random() * (r - l) + l;
        }
    }

    // A* todo
}
