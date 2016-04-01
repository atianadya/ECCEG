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
    
    public static Point[] encode(String data, EllipticCurve ec) {
        Point[] pointseq = new Point[data.length()];
        // pick elliptic curve
        // choose aux base parameter e.g. k = 20
        // foreach number mk, take x = mk + 1, try to solve for y
        //                             mk + 2, etc, mk + k-1
        // take point (x, y)
        
        BigInteger k = new BigInteger("20");
        
        for (int i=0; i<data.length(); i++) {
            Integer ch = (int)data.charAt(i);
            BigInteger chara = BigInteger.valueOf(ch.intValue());
            pointseq[i] = encodeInd(chara,ec,k);
        }
        
        return pointseq;
    }
     
   // untested
    private static Point encodeInd(BigInteger m, EllipticCurve ec, BigInteger kaux) {
        BigInteger x,y;
        Point encoded = ec.solveForY(m,kaux);
        return encoded;
    }
    
    // untested
    public static String decode(Point[] pointseq, BigInteger kaux) {
        String decoded = "";
        BigInteger[] biseq = new BigInteger[pointseq.length];
        
        for (int i=0; i<pointseq.length; i++) {
            biseq[i] = decodeInd(pointseq[i], kaux);
            decoded += Character.toString((char) biseq[i].intValue());
        }
        
        return decoded;
    }
    
    private static BigInteger decodeInd(Point cipherpoint, BigInteger kaux) {
        return (cipherpoint.getX().subtract(BigInteger.ONE)).divide(kaux);
    }
    
    public void printPointArray(Point[] p) {
        for (Point p1 : p) {
            System.out.println(p1.toString());
        }
    }
}
