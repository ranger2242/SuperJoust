package com.superjoust.qxst;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Chris Cavazos on 9/16/2016.
 * EMATH v0.2
 */
public class EMath {

    static Random rn = new Random();

    public static double pathag(Vector2 a, Vector2 b) {
        return  Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public static double pathag(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }



    public static double angle(Vector2 a, Vector2 b) {
        double angx = b.x - a.x;
        double angy = b.y - a.y;
        boolean cox, coy;
        double initdeg = Math.toDegrees(Math.atan(angy / angx));
        if (b.dx(b) >= 0)
            cox = true;
        else
            cox = false;
        if (b.dy(b) >= 0)
            coy = true;
        else
            coy = false;

        if (!cox && coy) {
            initdeg += 180;
        }
        if (!cox && !coy) {
            initdeg += 180;
        }
        if (cox && !coy) {
            initdeg += 360;
        }
        return initdeg;
    }

    public static double dx(double a, double b) {
        return b - a;
    }

    public static double round(double d) {
        double rem = d - Math.floor(d);
        if (rem < .5f) {
            return Math.floor(d);
        } else {
            return Math.ceil(d);
        }
    }

    public static int roundToNearest45(int ang) {
        int[] arr = new int[]{0, 45, 90, 135, 180, 225, 270, 315, 360};
        int index = 0;
        int min = 10000;
        for (int i = 0; i < arr.length; i++) {
            double d = dx(ang, arr[i]);
            if (d <= min) {
                min = (int) d;
                index = i;
            }
        }
        return arr[index];
    }

    public static double arcL(double theta, double r, double dl) {
        double arc =  (((2 * Math.PI * r) / 360) * dl);
        return 0;
    }

    public static double average(double[] f) {
        double sum = 0;
        for (int i = 0; i < f.length; i++) {
            sum += f[i];
        }
        return sum / f.length;
    }

    public static double randomAverage(double a, double b) {
        double dif = a - b;
        if (b > a) dif = b - a;
        double step = dif / 100;
        double fill = step * rn.nextInt(100) + 1;
        if (a > b) return b + fill;
        else return a + fill;
    }//test

    public static double randomGaussianAverage(double a, double b) {
        double mid = (a + b) / 2;
        double r = rn.nextGaussian();
        double max = 1, min = 1;
        if (a > b) {
            min = b / mid;
            max = a / mid;
        }
        if (b > a) {
            min = a / mid;
            max = b / mid;
        }
        while (r < min || r > max)
            r = rn.nextGaussian();

        return (int) mid * r;
    }

    public static Vector2 vectorAverage(ArrayList<Vector2> list) {
        Vector2 sum = new Vector2(0, 0);
        for (int i = 0; i < list.size(); i++) {
            sum.x += list.get(i).x;
            sum.y += list.get(i).y;
        }
        if (!list.isEmpty()) {
            sum.x /= list.size();
            sum.y /= list.size();
        }
        return sum;
    }

    public static boolean isInHalfRange(double test, double start, double end, boolean low) {
        double a = (start + end) / 2;
        if (test >= start && test <= end) {
            boolean q = test <= a;
            boolean w = test >= a;
            return ((low && q) || (!low && w));
        } else
            return false;
    }
    public static double[] quadraticEq(double a, double b, double c){
        double qu=(Math.pow(b,2)-4*a*c);
        if(qu>=0) {
            double posQuad =  ((-b) + (Math.sqrt(qu) / (2 * a)));
            double negQuad = ((-b) - (Math.sqrt(qu) / (2 * a)));
            double[] temp = {posQuad, negQuad};
            System.out.print(posQuad + " " + negQuad);
            return temp;
        }else{
            System.out.println("Complex roots");
            return new double[]{0,0};
        }

    }
}