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
public class Point {
    private BigInteger x, y;
    private boolean infinite;
    
    public Point() {
        x = new BigInteger("0");
        y = new BigInteger("0");
    }
    
    public Point(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }
    
    public BigInteger getX() {
        return x;
    }
    
    public BigInteger getY() {
        return y;
    }
    
    public void setY(BigInteger y) {
        this.y = y;
    }
    
    public void setX(BigInteger x) {
        this.x = x;
    }
    
    public String toString() {
        String pointstr;
        pointstr = "(" + x + "," + y + ")";
        
        return pointstr;
    }
    
    public boolean isEqual(Point b) {
        return this.x.equals(b.x) && this.y.equals(b.y);
    }
    
    public boolean isInf() {
        return this.x.equals(BigInteger.ZERO) && this.y.equals(BigInteger.ZERO);
    }
}
