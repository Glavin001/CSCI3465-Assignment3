package assignment3;

import java.io.Serializable;

/**
 * 
 * @author Glavin Wiechert
 *
 */
public class MagnetObject implements Serializable
{
    static final long serialVersionUID = 1L;
    //private MagnetDataType t;
    private int id;
    private char letter;
    private int x;
    private int y;

    /**
     * 
     * @param id
     * @param x
     * @param y
     */
    public MagnetObject(int id, int x, int y)
    {
        //this.t = MagnetDataType.MOVEMENT;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * @param id
     * @param x
     * @param y
     * @param letter
     */
    public MagnetObject(int id, int x, int y, char letter)
    {
        //this.t = MagnetDataType.SETUP;
        this.id = id;
        this.x = x;
        this.y = y;
        this.letter = letter;
    }

    /**
     * 
     */
    public MagnetObject()
    {
        //this.t = MagnetDataType.SHUTDOWN;
    }

    /**
     * 
     * @return
     */
    /*
    public MagnetDataType getType()
    {
        return this.t;
    }
    */
    
    /**
     * 
     * @return
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * 
     * @return
     */
    public int getPosX()
    {
        return this.x;
    }

    /**
     * 
     * @return
     */
    public int getPosY()
    {
        return this.y;
    }

    /**
     * 
     * @return
     */
    public char getLetter()
    {
        return this.letter;
    }

    /**
     * 
     * @param x
     * @param y
     */
    public void updatePosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}