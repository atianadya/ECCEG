/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecceg;

import java.math.BigInteger;

/**
 *
 * @author atia
 */
public class EllipticCurve {
    
    // eq
    // y^2 = x^3 + ax + b mod p
    
    // base point ∈ elliptic group
    
    public static BigInteger a;
    public static BigInteger b;
    public static BigInteger p;
    public static Point base;
    
    public EllipticCurve(BigInteger a, BigInteger b, BigInteger p, Point base) {
        this.a = a;
        this.b = b;
        this.p = p;
        this.base = base;
    }
    
    // point iteration
    public Point add(Point p1, Point q1) {
        Point r;
        
        // gradient
        BigInteger gradient = (((p1.getY().subtract(q1.getY())).abs())
                .multiply((p1.getX().subtract(q1.getX())).abs().modInverse(p)))
                .mod(p);
        
        // Rx = ( gradient^2 - xp - xq ) mod p
        BigInteger xr = ((gradient.pow(2)).subtract(p1.getX()).subtract(q1.getX()))
                .mod(p);
        
        // Ry = ( gradient(xp - xr) - yp ) mod p
        BigInteger yr = ((gradient.multiply(p1.getX().subtract(xr)))
                .subtract(p1.getY())).mod(p);
        
        r = new Point(xr, yr);
        
        return r;
    }
    
    //point doubling
    public Point doubles(Point q) {
        Point r;
        
        // if yp == 0 2p = O (infinity)
        
        //gradient = ( 3xp + a / 2yp ) mod p
        BigInteger gradient = (((new BigInteger("3").multiply(q.getX().pow(2))).add(a)) 
                .multiply(new BigInteger("2").multiply(q.getY()).modInverse(p)))
                .mod(p);
        
        // Rx = ( gradient^2 - 2xp ) mod p
        BigInteger xr = ((gradient.pow(2)).subtract(new BigInteger("2")
                .multiply(q.getX()))).mod(p);
        
        // Ry = ( gradient(xp - xr) - yp ) mod p
        BigInteger yr = ((gradient.multiply(q.getX().subtract(xr)))
                .subtract(q.getY())).mod(p);
        
        r = new Point(xr, yr);
        
        return r;
    }
    
    public Point multiply (Point point, BigInteger scalar) {
        
        /*
            scalar == 0
                return (0,0)
            scalar == 1
                return point
            scalar even
                return 2 * ((scalar/2) * point)
            scalar odd
                return point + ((scalar-1)*point)
        */
        
        if (scalar.equals(BigInteger.ZERO)) {
            return new Point (new BigInteger("0"), new BigInteger("0"));
        } else if (scalar.equals(BigInteger.ONE)) {
            return point;
        } else if (scalar.mod(new BigInteger("2")).equals(BigInteger.ZERO)) {
//            return multiply(multiply(point, (scalar.divide(new BigInteger("2")))),
//                    new BigInteger("2"));
            
            return doubles(multiply(point, scalar.divide(new BigInteger("2"))));
            
        } else {
            return add(point, multiply(point, scalar.subtract(BigInteger.ONE)));
        }
    }
    
    public Point subtract(Point p1, Point q1) {
        Point minQ = new Point(q1.getX(), q1.getY().multiply(new BigInteger("-1")).mod(p));
        return add(p1, minQ);
    }
    
    public boolean isInGroup(Point point) {
        BigInteger RHS = ((point.getX().pow(3)).add(a.multiply(point.getX())).add(b)).mod(p);
        BigInteger LHS = (point.getY().pow(2)).mod(p);
        
        System.out.println("EQ: " + "y^2 mod " + p + " = x^3 + " + a + "x + " + b + " mod " + p);
        System.out.println("RHS: " + RHS);
        System.out.println("LHS: " + LHS);
        
        return LHS.equals(RHS);
    }
    
    public Point solveForY(BigInteger m, BigInteger kaux) {
        BigInteger x,yy,y;
        
        for (BigInteger i = BigInteger.ONE; i.compareTo(kaux) == -1; i = i.add(BigInteger.ONE)) {
            x = m.multiply(kaux).add(i);
            yy = (x.pow(3).add(a.multiply(x)).add(b)).mod(p);
            if (yy.modPow(p.subtract(BigInteger.ONE).divide(new BigInteger("2")), p).compareTo(BigInteger.ONE) == 0) {
                System.out.println(i);
                System.out.println(yy);
                y = yy.modPow(p.add(BigInteger.ONE).divide(new BigInteger("4")), p);
                return new Point(x,y);
            }
        }
        
        return new Point(new BigInteger("-1"), new BigInteger("-1"));
    }
    
    public Point getBasePoint() {
        return base;
    }
    
}
