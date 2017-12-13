package com.example.mcken.mastermind;

/**
 * Created by mcken on 4/11/2017.
 */
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Set;

import java.lang.Math;
import java.lang.StringBuilder;

public class Code {

        protected static Map PEGS;
        protected String secretCodeString;


        public Code(String input){
            this.secretCodeString = input;
        }

        public Code(){
            randomize();
        }



        private static final void setPegs(){
            PEGS = new HashMap<R.drawable,String>();

            PEGS.put(R.drawable.bluepegfiller,"b");
            PEGS.put(R.drawable.yellowpegfiller,"y");
            PEGS.put(R.drawable.orangepegfiller,"o");
            PEGS.put(R.drawable.greenpegfiller,"g");
            PEGS.put(R.drawable.redpegfiller,"r");
            PEGS.put(R.drawable.purplepegfiller,"p");
        }

        // random Code generation
        // must set to a collection of the keys, not a string from an index
        public Code randomize(){
            setPegs();


            Collection pegSet = PEGS.values();
            Object[] pegArr = pegSet.toArray(new String[pegSet.size()]);
            StringBuilder sb = new StringBuilder();
            Random rand = new Random();



            for (int i = 0; i < 4; i++){
                int randIndex = rand.nextInt(5);
                sb.append(String.valueOf(pegArr[randIndex]));
            }

            secretCodeString = sb.toString();
            Code secretCode = parse(secretCodeString);
            return secretCode;
        }

        private static Code parse(String input) {

            String[] letters = input.toLowerCase().split("");
            StringBuilder sb = new StringBuilder();

            for (String letter : letters) {
                if (Arrays.asList(PEGS).contains(letter)) {
                    sb.append(letter);
                }
            }

            String stringToParse = sb.toString();
            Code parsedCode = new Code(stringToParse);
            return parsedCode;

        }

        protected int countExactMatches(Code guess){
            String guessString = guess.secretCodeString;

            int exactMatches = 0;

            String[] guessArray = guessString.split("");

            String[] winningCodeArray = (this.secretCodeString).split("");

            for(int i = 0; i < 4; i++){

                if(guessArray[i].equals(winningCodeArray[i])){
                    exactMatches++;
                }
            }
            return exactMatches;
        }

        protected int countNearMatches(Code guess) {

            String guessString = guess.secretCodeString;

            HashMap<String,Integer> guessCount = new HashMap<String,Integer>();
            HashMap<String,Integer> secretCodeCount = new HashMap<String,Integer>();

            int matches = 0;

            for(int i = 0; i < guessString.length(); i++) {
                //removes character from string
                String codeCharacter = String.valueOf(guessString.charAt(i));
                String guessShort = guessString.replace(codeCharacter,"");

                //counts instances of said character
                int count = guessString.length() - guessShort.length();

                guessCount.put(codeCharacter, count);

            }

            for(int i = 0; i < secretCodeString.length(); i++) {
                //removes character from string
                String winningString = this.secretCodeString;

                String winningCodeCharacter = String.valueOf(winningString.charAt(i));
                String winningCodeShort = winningString.replace(winningCodeCharacter,"");

                //counts instances of said character
                int count = winningString.length() - winningCodeShort.length();

                secretCodeCount.put(winningCodeCharacter, count);
            }

            Set<String> codeKeys = guessCount.keySet();
            int keys = guessCount.keySet().size();
            String[] keyArray = new String[keys];

            //gets count
            for (int i = 0; i < keys; i++) {
                codeKeys.toArray(keyArray);
                String keyString = keyArray[i];

                if (secretCodeCount.containsKey(keyString)) {
                    matches += Math.min(secretCodeCount.get(keyString), guessCount.get(keyString));
                }
            }

            int nearMatches = matches - countExactMatches(guess);

            return nearMatches;
        }
    }


