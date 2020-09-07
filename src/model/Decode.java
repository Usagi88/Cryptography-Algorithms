/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author NUser1
 */
public class Decode {

    private String character = "abcdefghijklmnopqrstuvwxyz0123456789";      //characters used for decoding
    private String digits = "0123456789";      //digits used for decoding setB
    private char[] index;
    private String input;
    public boolean flag;            //parameters used in class
    String word, hashWord;
    public long timer = 0;
    public static String decodedWord;

    public Decode() {
    }

    public Decode(String character, char[] index, String input) {       //decode class with getters and setters
        this.character = character;
        this.index = index;
        this.input = input;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public char[] getIndex() {
        return index;
    }

    public void setIndex(char[] index) {
        this.index = index;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void decoding(String text) {     //decodes by using text parameter
        int i = 1;
        int position = 0;
        flag = false;
        while (i < 7) {             //given hashed words are 6 characters max so using loop to go through 6 times
            if (!flag) {
                try {
                    char index[] = new char[i];
                    recursion(index, position, character, text); //using recursion function to make it loop inside to get value
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            }
            i++;    //incrementing
        }

    }

    public void decodingSetB(String text) {     //decodes by using text parameter
        int i = 1;
        int position = 0;
        flag = false;
        while (i < 11) {             //hashed words are 10 characters max so using loop t go 10 times
            if (!flag) {
                try {
                    char index[] = new char[i];
                    recursion(index, position, digits, text); //using recursion function to make it loop inside to get value
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            }
            i++;    //incrementing
        }

    }

    public void recursion(char[] index, int position, String character, String text) throws NoSuchAlgorithmException,
            UnsupportedEncodingException {

        if (position == index.length && !flag) {
            word = new String(index);
            hashWord = SHA1(word);      //using sha1 function to hash

            if (hashWord.equals(text)) {    //if it is equal hashed text
                decodedWord = word;     //initializing to decodedWord
                flag = true;

            }

        } else {

            for (int y = 0; y < character.length(); y++) {
                index[position] = character.charAt(y);  //changing position
                recursion(index, position + 1, character, text);    //using recursion function inside recursion function to make it loop
            }
        }

    }

    private static String convertToHex(byte[] data) { //referenced from week 4 cryptography classic cipher and hashing
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) //referenced from week 4 cryptography classic cipher and hashing
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

}
