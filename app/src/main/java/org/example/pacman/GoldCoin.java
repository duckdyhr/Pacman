package org.example.pacman;

/**
 * This class should contain information about a single GoldCoin.
 * such as x and y coordinates (int) and whether or not the goldcoin
 * has been taken (boolean)
 */

public class GoldCoin {
    private int coinx;
    private int coiny;
    private boolean taken;

    public GoldCoin(int coinx, int coiny){
        this.coinx = coinx;
        this.coiny = coiny;
        this.taken = false;
    }

    public int getCoinx() {
        return coinx;
    }

    public void setCoinx(int coinx) {
        this.coinx = coinx;
    }

    public int getCoiny() {
        return coiny;
    }

    public void setCoiny(int coiny) {
        this.coiny = coiny;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public String toString(){
        return new StringBuilder().append("x, y, taken: ").append(coinx).append(", ").append(coiny).append(", ").append(isTaken()).toString();
    }
}