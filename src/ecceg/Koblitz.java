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
public class Koblitz {
    
    // untested
    public Point[] encode(String plain, EllipticCurve ec) {
        Point[] pointseq = new Point[plain.length()];
        // pick elliptic curve
        // choose aux base parameter e.g. k = 20
        // foreach number mk, take x = mk + 1, try to solve for y
        //                             mk + 2, etc, mk + k-1
        // take point (x, y)
        
        BigInteger k = new BigInteger("20");
        for (int i=0; i<plain.length(); i++) {
            BigInteger m = BigInteger.valueOf(plain.charAt(i));
            pointseq[i] = encodeInd(m,ec,k);
        }
        
        return pointseq;
    }
    
    // untested
    public Point encodeInd(BigInteger m, EllipticCurve ec, BigInteger kaux) {
        BigInteger x;
        for (BigInteger i = BigInteger.ONE; i.compareTo(kaux) == -1; i = i.add(BigInteger.ONE)) {
            x = m.multiply(kaux).add(i);
            if (EllipticCurve.solveForY(x) != null) {
                return new Point(x, EllipticCurve.solveForY(x));
            }
        }
        
        return new Point(new BigInteger("-99"), new BigInteger("-99"));
    }
    
    // untested
    public String decode(Point[] pointseq, BigInteger kaux) {
        String decoded = null;
        BigInteger[] biseq = new BigInteger[pointseq.length];
        
        for (int i=0; i<pointseq.length; i++) {
            biseq[i] = decodeInd(pointseq[i], kaux);
            decoded += Character.toString((char) biseq[i].intValue());
        }
        
        return decoded;
    }
    
    public BigInteger decodeInd(Point cipherpoint, BigInteger kaux) {
        return (cipherpoint.getX().subtract(BigInteger.ONE)).divide(kaux);
    }
    
    public void printPointArray(Point[] p) {
        for (Point p1 : p) {
            System.out.println(p1.toString());
        }
    }
}
