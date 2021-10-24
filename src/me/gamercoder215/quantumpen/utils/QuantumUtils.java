package me.gamercoder215.quantumpen.utils;

public class QuantumUtils {
	
	public static double solveEquation(final String str) {
	    return new Object() {
	        int pos = -1, ch;

	        void nextChar() {
	            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
	        }

	        boolean eat(int charToEat) {
	            while (ch == ' ') nextChar();
	            if (ch == charToEat) {
	                nextChar();
	                return true;
	            }
	            return false;
	        }
	        

	        double parse() {
	            nextChar();
	            double x = parseExpression();
	            if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
	            return x;
	        }

	        double parseExpression() {
	            double x = parseTerm();
	            for (;;) {
	                if      (eat('+')) x += parseTerm(); // addition
	                else if (eat('-')) x -= parseTerm(); // subtraction
	                else return x;
	            }
	        }

	        double parseTerm() {
	            double x = parseFactor();
	            for (;;) {
	                if      (eat('*')) x *= parseFactor(); // multiplication
	                else if (eat('/')) x /= parseFactor(); // division
	                else return x;
	            }
	        }

	        double parseFactor() {
	            if (eat('+')) return parseFactor(); // unary plus
	            if (eat('-')) return -parseFactor(); // unary minus

	            double x;
	            int startPos = this.pos;
	            if (eat('(')) { // parentheses
	                x = parseExpression();
	                eat(')');
	            } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
	                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
	                x = Double.parseDouble(str.substring(startPos, this.pos));
	            } else if (ch >= 'a' && ch <= 'z') { // functions
	                while (ch >= 'a' && ch <= 'z') nextChar();
	                String func = str.substring(startPos, this.pos);
	                x = parseFactor();
	                if (func.equalsIgnoreCase("sqrt")) x = Math.sqrt(x);
	                else if (func.equalsIgnoreCase("sin")) x = Math.sin(Math.toRadians(x));
	                else if (func.equalsIgnoreCase("cos")) x = Math.cos(Math.toRadians(x));
	                else if (func.equalsIgnoreCase("tan")) x = Math.tan(Math.toRadians(x));
	                else if (func.equalsIgnoreCase("abs")) x = Math.abs(x);
	                else if (func.equalsIgnoreCase("cbrt")) x = Math.cbrt(x);
	                else if (func.equalsIgnoreCase("log")) x = Math.log(Math.toRadians(x));
	                else if (func.equalsIgnoreCase("round")) x = Math.round(x);
	                else if (func.equalsIgnoreCase("cosh")) x = Math.cosh(Math.toRadians(x));
	                else if (func.equalsIgnoreCase("acos")) x = Math.acos(Math.toRadians(x));
	                else if (func.equalsIgnoreCase("asin")) x = Math.asin(Math.toRadians(x));
	                else if (func.equalsIgnoreCase("atan")) x = Math.atan(Math.toRadians(x));
	                else if (func.equalsIgnoreCase("random")) x = Math.random() * x;
	                else if (func.equalsIgnoreCase("signum")) x = Math.signum(Math.toRadians(x));
	                
	                else throw new RuntimeException("Unknown function: " + func);
	            } else {
	                throw new RuntimeException("Unexpected: " + (char)ch);
	            }

	            if (eat('^')) x = Math.pow(x, parseFactor()); 

	            return x;
	        }
	    }.parse();
	}
}