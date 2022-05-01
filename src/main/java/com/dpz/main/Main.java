package com.dpz.main;

//ACM mode

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T=in.nextInt();
        for (int t = 0; t < T; t++) {
            int A=in.nextInt();
            int B=in.nextInt();
            int C=in.nextInt();
            double delta=Math.pow(A,2)-2*A*B*C;
            if(delta<=0)
                System.out.println(0);
            else{
                delta=Math.sqrt(delta);
                double x1=(A-B*C+delta)/Math.pow(B,2);
//                double x2=(A-B*C-delta)/Math.pow(B,2);
                if(B>0){
                    double e=1e-3;
                    double area=0;
                    for(double x=0;x<x1;x+=e){
                        area+=Math.sqrt(2*A*x)-
                                Math.max(B*x+C,-Math.sqrt(2*A*x));
                    }
                    System.out.println(area/1e3);
                }
                else {
                    double e=1e-5;
                    double area=0;
                    for(double x=0;x<x1;x+=e){
                        area+=Math.min(Math.sqrt(2*A*x),B*x+C) -
                                -Math.sqrt(2*A*x);
                    }
                    System.out.println(area/1e3);
                }

            }
        }

    }

}
