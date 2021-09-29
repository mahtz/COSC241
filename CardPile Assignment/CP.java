import java.util.Arrays;
import java.util.Scanner;

/**
 * A class CP which implements CardPile, that is able to load in arrays of int and perform transformations 
 * and analysis on the array.
 *
 * @author Mathew Shields
 */
public class CP implements CardPile{

    /** Private array of int data field which stores a copy of a loaded in array. */
    private int[] nCards;
    /** Private array of int data field that stores an array which has undergone a transformation. */
    private int[] transformed;
    /** Private array of String containing the 8 transformation specifications. */
    private String[] specs = {"TL", "BL", "TR", "BR", "LT", "LB", "RT", "RB"};

    /**
     * Loads in a given array of int and makes a copy of it.
     *
     * @param cards array of int.
     */
    public void load(int[] cards){
        int length = cards.length;
        nCards = new int[length];
        for(int x = 0; x < length; x++){
            nCards[x] = cards[x];
        }
    }

    /**
     * Loads an array of int with numbers from 1 to n.
     *
     * A CardPileException will be thrown if n is not a positive integer.
     *
     * @param n int.
     */
    public void load(int n){
        if(n < 1){
            throw new CardPileException("n is not a positive integer.");
        }
        nCards = new int[n];
        for(int x = 0; x < n; x++){
            nCards[x] = x+1;
        }
    }

    /**
     * Returns either the loaded in array or an array that has been transformed.
     *
     * @return nCards or transformed, depending on if a transformation has occurred.
     */
    public int[] getPile(){
        if(nCards != null && transformed == null){
            return nCards;
        } else{
            return transformed;
        }
    }

    /**
     * Performs a transformation on an array by first turning it into a 2-Dimensional array with the number
     * of columns equal to rowLength. A transformation is then performed according to the spec given.
     *
     * A CardPileException will be thrown if the size of the pile is not a multiple of rowLength or if the
     * spec given is not one of the eight specifications.
     *
     * @param rowLength int the length of the rows in the card pile.
     * @param spec String the transform specification.
     */
    public void transform(int rowLength, String spec){
        int[] cardPile = getPile();
        int numCards = cardPile.length;
        int numRows = numCards/rowLength;

        if(numCards % rowLength != 0){
            throw new CardPileException("The size of the pile is not a multiple of rowLength.");
        }
       
        // Stores card pile in 2-Dimensional array cardArrays.
        int[][] cardArrays = new int[numRows][rowLength];
        int y = 0;        
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < rowLength; j++){
                cardArrays[i][j] = cardPile[y];
                y++;
            }
        }
        
        // Transforms the 2-Dimensional array depending on the specification given and stores values in
        // transformed.
        transformed = new int[numCards];
        switch(spec){
            case "TL":
                int x = 0;
                for(int i = 0; i < rowLength; i++){
                    for(int j = 0; j < numRows; j++){
                        transformed[x] = cardArrays[j][i];
                        x++;
                    }
                }
                break;
            case "TR":
                x = 0;
                for(int i = rowLength-1; i >= 0; i--){
                    for(int j = 0; j < numRows; j++){
                        transformed[x] = cardArrays[j][i];
                        x++;
                    }
                }
                break;
            case "BL":
                x = 0;
                for(int i = 0; i < rowLength; i++){
                    for(int j = numRows-1; j >= 0; j--){
                        transformed[x] = cardArrays[j][i];
                        x++;
                    }
                }
                break;
            case "BR":
                x = 0;
                for(int i = rowLength-1; i >= 0; i--){
                    for(int j = numRows-1; j >= 0; j--){
                        transformed[x] = cardArrays[j][i];
                        x++;
                    }
                }
                break;
            case "LT":
                x = 0;
                for(int i = 0; i < numRows; i++){
                    for(int j = 0; j < rowLength; j++){
                        transformed[x] = cardArrays[i][j];
                        x++;
                    }
                }
                break;
            case "RT":
                x = 0;
                for(int i = 0; i < numRows; i++){
                    for(int j = rowLength-1; j >= 0; j--){
                        transformed[x] = cardArrays[i][j];
                        x++;
                    }
                }
                break;
            case "LB":
                x = 0;
                for(int i = numRows-1; i >= 0; i--){
                    for(int j = 0; j < rowLength; j++){
                        transformed[x] = cardArrays[i][j];
                        x++;
                    }
                }
                break;
            case "RB":
                x = 0;
                for(int i = numRows-1; i >= 0; i--){
                    for(int j = rowLength-1; j >= 0; j--){
                        transformed[x] = cardArrays[i][j];
                        x++;
                    }
                }
                break;
            default:
                throw new CardPileException("String spec is not one of the eight specified.");
        }
    }

    /**
     * Calculates and returns the minimum number of transformations with a certain rowLength and spec that
     * will be needed for the pile to return to its original state.
     *
     * A CardPileError will be thrown if the size of the pile is not a multiple of rowLength.
     *
     * @param rowLength int the lengths of the rows in the card pile.
     * @param String the transform specification.
     */
    public int count(int rowLength, String spec){

        int[] cp = getPile();
        if(cp.length % rowLength != 0){
            throw new CardPileException("The size of the pile is not a multiple of rowLength.");
        }
        int count = 1;
        transform(rowLength, spec);
        while(!Arrays.equals(cp, transformed)){
            transform(rowLength, spec);
            count++;
        }
        return count;
    }

    /**
     * Main method.
     * Calls certain methods depending on the number of command-line inputs or input from stdin.
     * If there is no command-line inputs input comes from stdin. Several different operations can be
     * called depending on the input.
     * If there is two command-line inputs the first item will be the size of the pile to be loaded and the
     * second will be the row length. The program will output the number of transformations needed for the
     * pile to return to its original state for each of the eight specifications for the given row length and
     * pile.
     * If there is three or more command-line inputs the first item will be the size of the pile to be loaded
     * and the second will be the row length. Any items after that will be transform specifications that will
     * be applied to a pile with the size and row length given.
     *
     * A CardPile Error will be thrown if an incorrect number of command-line inputs is given.
     */
    public static void main(String[] args){

        CP cp = new CP();
        
        if(args.length == 0){
            Scanner scan = new Scanner(System.in);
            while(scan.hasNext()){
                switch(scan.next()){
                    case "l":     // Calls load(int).
                        cp.load(scan.nextInt());
                        cp.transformed = cp.nCards;
                        break;
                    case "L":     // Calls load(int[]).
                        String temp = scan.nextLine().substring(1);
                        String[] split = temp.split(" ");
                        int[] arr = new int[split.length];
                        for(int i = 0; i < split.length; i++){
                            arr[i] = Integer.parseInt(split[i]);
                        }
                        cp.load(arr);
                        cp.transformed = cp.nCards;
                        break;
                    case "t":     // Calls transform(int, String).
                        int rowL = scan.nextInt();
                        String sp = scan.next();
                        cp.transform(rowL, sp);
                        break;
                    case "c":     // Calls count(int, String) and prints out result.
                        rowL = scan.nextInt();
                        sp = scan.next();
                        System.out.println(cp.count(rowL, sp));
                        break;
                    case "p":     // Prints out the current pile.
                        for(int card : cp.getPile()){
                            System.out.print(card + " ");
                        }
                        System.out.println();
                        break;
                    case "P":     // Prints the cards in rows of length n.
                        int[] pile = cp.getPile();
                        rowL = scan.nextInt();
                        int numR = pile.length/rowL;
                        int[][] pile2D = new int[numR][rowL];
                        int x = 0;
                        for(int i = 0; i < numR; i++){
                            for(int j = 0; j < rowL; j++){
                                pile2D[i][j] = pile[x];
                                x++;
                            }
                        }
                        for(int[] a : pile2D){
                            for(int i : a){
                                System.out.print(i + " ");
                            }
                            System.out.println();
                        }
                }
            }
        } else if(args.length == 2){
            cp.load(Integer.parseInt(args[0]));
            for(String sp : cp.specs){
                System.out.println(sp + " " + cp.count(Integer.parseInt(args[1]), sp));
            }
        } else if(args.length >= 3){   
            cp.load(Integer.parseInt(args[0]));
            for(int card : cp.getPile()){
                System.out.print(card + " ");
            }
            System.out.println();
            for(int x = 2; x < args.length; x++){
                cp.transform(Integer.parseInt(args[1]), args[x]);
                for(int card : cp.transformed){
                    System.out.print(card + " ");
                }
                System.out.println();
            }
        } else{
            throw new CardPileException("Incorrect number of command-line arguments.");
        }
    }
}
