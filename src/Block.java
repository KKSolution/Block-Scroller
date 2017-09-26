/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author KK
 */
public class Block {
    private int topX;
    private int topY;
    private int botX;
    private int botY;
    public boolean selected;

    public Block(int tx, int ty, int bx, int by){
        topX = tx;
        topY = ty;
        botX = bx;
        botY = by;
        selected = false;
    }
    public void setTopX(int topX) {
        this.topX = topX;
    }

    public void setTopY(int topY) {
        this.topY = topY;
    }

    public void setBotX(int botX) {
        this.botX = botX;
    }

    public void setBotY(int botY) {
        this.botY = botY;
    }

    public int getTopX() {
        return topX;
    }

    public int getTopY() {
        return topY;
    }

    public int getBotX() {
        return botX;
    }

    public int getBotY() {
        return botY;
    }
    
    
}
