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
    public static Point[] encode(byte[] data, EllipticCurve ec) {
        Point[] pointseq = new Point[data.length];
        // pick elliptic curve
        // choose aux base parameter e.g. k = 20
        // foreach number mk, take x = mk + 1, try to solve for y
        //                             mk + 2, etc, mk + k-1
        // take point (x, y)
        
        BigInteger k = new BigInteger("20");
        
        for (int i=0; i<data.length; i++) {
            byte m = data[i];
            pointseq[i] = encodeInd(m,ec,k);
        }
        
        return pointseq;
    }
    
    // untested
    public static Point encodeInd(byte data, EllipticCurve ec, BigInteger kaux) {
        int bdata = data & 0xFF;
        BigInteger x,y;
        BigInteger m = BigInteger.valueOf(bdata);
        Point encoded = ec.solveForY(m,kaux);
        
        return new Point(new BigInteger("-99"), new BigInteger("-99"));
    }
    
    // untested
    public static String decode(Point[] pointseq, BigInteger kaux) {
        String decoded = null;
        BigInteger[] biseq = new BigInteger[pointseq.length];
        
        for (int i=0; i<pointseq.length; i++) {
            biseq[i] = decodeInd(pointseq[i], kaux);
            decoded += Character.toString((char) biseq[i].intValue());
        }
        
        return decoded;
    }
    
    public static BigInteger decodeInd(Point cipherpoint, BigInteger kaux) {
        return (cipherpoint.getX().subtract(BigInteger.ONE)).divide(kaux);
    }
    
    public void printPointArray(Point[] p) {
        for (Point p1 : p) {
            System.out.println(p1.toString());
        }
    }
}
