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
public class ECCEG {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("EC operations test");
        
        Point P = new Point(new BigInteger("2"), new BigInteger("4"));
        Point Q = new Point(new BigInteger("5"), new BigInteger("9"));
        EllipticCurve ec = new EllipticCurve(new BigInteger("1"), new BigInteger("6"),
                new BigInteger("11"), new Point(BigInteger.ZERO, BigInteger.ZERO));
        
        System.out.println(ec.add(P, Q).toString());
        System.out.println(ec.doubles(P));
        System.out.println(ec.multiply(P, new BigInteger("3")));
        
        System.out.println();
        
        System.out.println("Koblitz test");
        
        BigInteger a = new BigInteger("1");
        BigInteger b = new BigInteger("0");
        BigInteger p = new BigInteger("23");
        EllipticCurve eckob = new EllipticCurve(a, b, p, new Point(BigInteger.ZERO, BigInteger.ZERO));
        
        System.out.println(eckob.isInGroup(new Point(new BigInteger("5"), new BigInteger("9"))));
        
        a = new BigInteger("-1");
        b = new BigInteger("188");
        p = new BigInteger("751");
        
        EllipticCurve eck = new EllipticCurve(a, b, p, new Point(BigInteger.ZERO, BigInteger.ZERO));
        
//        eckob.solveForY(new BigInteger("224"));
//        KeyGenerator kg = new KeyGenerator();
//        
//        System.out.println(kg.generatePrivateKey());
    }
    
}
