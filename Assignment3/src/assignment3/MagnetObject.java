package assignment3;

import java.io.Serializable;

/**
 * Fridge Magnet in data object form for serializing and send across socket connections.
 * @author Glavin Wiechert
 *
 */
public class MagnetObject implements Serializable
{
    static final long serialVersionUID = 1L;
    private int id;
    private char letter;
    private int x;
    private int y;

    /**
     * Constructor
     * @param id
     * @param x
     * @param y
     */
    public MagnetObject(int id, int x, int y)
    {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor
     * @param id
     * @param x
     * @param y
     * @param letter
     */
    public MagnetObject(int id, int x, int y, char letter)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.letter = letter;
    }

    /**
     * Constructor
     */
    public MagnetObject()
    {

    }
    
    /**
     * Get Identifier / index in array of Fridge magnets.
     * @return  The identifier.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Get X position.
     * @return X position.
     */
    public int getPosX()
    {
        return this.x;
    }

    /**
     * Get Y position.
     * @return Y position.
     */
    public int getPosY()
    {
        return this.y;
    }

    /**
     * Get the letter / chararacter.
     * @return  The letter.
     */
    public char getLetter()
    {
        return this.letter;
    }

    /**
     * Move to a new position.
     * @param x
     * @param y
     */
    public void updatePosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}