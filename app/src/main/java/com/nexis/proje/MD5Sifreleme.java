package com.nexis.proje;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class MD5Sifreleme {

    public static String yeniSifre;
    public static String md5Sifreleme(String gelenMail){
        //burada MD5 SİFRELEME YAPILACAK

     try{

         MessageDigest md= MessageDigest.getInstance("MD5");
         md.update(gelenMail.getBytes(),0,gelenMail.length());
         yeniSifre=new BigInteger(1, md.digest()).toString(16);


     }catch (Exception e){
         e.printStackTrace();
     }
        return yeniSifre;
    }
}
