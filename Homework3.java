//Colin Lesser
//cl868773@wcupa.edu
//CSC 302-01 
//Homework 3 RSA Encryption
//This is my final version

import java.math.*;
import java.util.*;
import java.io.*;

public class Homework3
{
   private BigInteger p;
   private BigInteger q;
   private BigInteger n;
   private BigInteger e;
   private BigInteger d;
   private BigInteger phi;
   private int bitLength = 1024;
   private Random rand;
   
   public Homework3()
   {
      rand = new Random();
      p  = BigInteger.probablePrime(bitLength, rand);
      q = BigInteger.probablePrime(bitLength, rand);
      n = p.multiply(q);
      phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
      e = BigInteger.probablePrime(bitLength / 2, rand);
      
      while(phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
      {
         e.add(BigInteger.ONE);
      }
      d = e.modInverse(phi);
   }
   public Homework3(BigInteger e, BigInteger d, BigInteger n)
   {
      this.n = n;
      this.e = e;
      this.d = d;
   }
   private static String bytesToString(byte[] encrypted)
   {
      String test = "";
      for(byte b : encrypted)
      {
         test += Byte.toString(b);
      }
      return test;
   }
   public byte[] signature(byte[] plain)
   {
      return (new BigInteger(plain)).modPow(n, d).toByteArray();
   }
   public byte[] encrypt(byte[] plain)
   {
      return (new BigInteger(plain)).modPow(e, n).toByteArray();
   }
   public byte[] decrypt(byte[] plain)
   {
      return (new BigInteger(plain)).modPow(d, n).toByteArray();
   }
   public byte[] verification(byte[] plain)
   {
      return (new BigInteger(signature(plain)).modPow(n, e).toByteArray());
   }
   
   @SuppressWarnings("deprecation")
   public static void main(String[] args)throws IOException
   {
      Homework3 rsa = new Homework3();
      Scanner sc = new Scanner(System.in);     
      
      System.out.println("Please enter a message to be encrypted: ");
      String input = sc.nextLine();
      
      System.out.println("Input: " + input);
      
      byte[] signature = rsa.signature(input.getBytes());
      System.out.println("Signature: " + signature);
      
      System.out.println("Input in bytes: " + bytesToString(input.getBytes()));
      
      byte[] encrypted = rsa.encrypt(input.getBytes());
      System.out.println("\nEncrypted input: " + encrypted);
      
      byte[] decrypted = rsa.decrypt(encrypted);
      
      System.out.println("\nDecrypted input in bytes: " + bytesToString(decrypted));
      System.out.println("Decrypted input: " + new String(decrypted));
      
      byte[] verification = rsa.verification(input.getBytes());
      System.out.println("Verification: " + verification);
      
   }
}