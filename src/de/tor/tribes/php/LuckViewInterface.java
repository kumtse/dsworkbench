/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tor.tribes.php;

/**
 *
 * @author Torridity
 */
public class LuckViewInterface {

    public static String createLuckIndicator(double pValue) {
        StringBuilder b = new StringBuilder();
        b.append("http://torridity.de/dsworkbench/luckView.php?luck=").append(Double.toString(pValue));
        return b.toString();
    }

    public static void main(String[] args) {
        System.out.println(LuckViewInterface.createLuckIndicator(20.0));
    }
}
