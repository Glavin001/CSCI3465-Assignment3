package assignment3;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;

/**
 * The FridgeMagnet for GUI.
 * @author Glavin Wiechert
 *
 */
class FridgeMagnet extends JLabel implements MouseMotionListener, MouseListener
{

    /**
     * For serializing.
     */
    static final long serialVersionUID = 1L;
    /**
     * Size of the label's bounds.
     */
    private int size = 15;
    /**
     * Client Kid.
     */
    private Kid client;
    /**
     * Has the user's interactions selected this magnet to move?
     */
    private boolean selected = false;
    /**
     * Y Position Offset
     */
    private int offsetPosY;
    /**
     * X Position Offset
     */
    private int offsetPosX;
    /**
     * Y Position
     */
    private int y;
    /**
     * X Position
     */
    private int x;
    /**
     * Identifier / Index in array of Magnets
     */
    private int id;

    /**
     * 
     * @param id
     * @param x
     * @param y
     * @param c
     * @param client
     */
    public FridgeMagnet(int id, int x, int y, char c, Kid client)
    {
        setFont(new Font("Courier", 1, 20));
        setText(Character.toString(c));
        this.x = x;
        this.y = y;
        this.client = client;
        this.id = id;

        setBounds(x, y, size, size);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * Tests if position touches the label's bounds.
     * @param x
     * @param y
     * @return Whether or not the position touches the label.
     */
    public boolean touches(int x, int y)
    {
        if (
                (x <= size) 
                && (x >= 0) 
                && (y <= size) 
                && (y >= 0)
                ) {
            return true;
        }
        return false;
    }

    /**
     * Update position of Magnet.
     * @param newX  New X position.
     * @param newY  New Y position.
     */
    public void updatePosition(int newX, int newY)
    {
        if ((this.x != newX) && (this.y != newY))
        {
            setBounds(newX, newY, size, size);
            this.x = newX;
            this.y = newY;
        }
    }

    /**
     * Creates MagnetObject (serializable) of this FridgeMagnet.
     * @return  MagnetObject (serializable) of this FridgeMagnet.
     */
    public MagnetObject serialize()
    {
        return new MagnetObject(this.id, this.x, this.y);
    }

    /**
     * Mouse pressed down event.
     * @param event The mouse event.
     */
    public void mousePressed(MouseEvent event)
    {
        // Check if the mouse event touches the magnet
        if (touches(event.getX(), event.getY()))
        {
            // Select this magnet by user interaction.
            this.selected = true;
            // Translate offset position
            this.offsetPosX = event.getX();
            this.offsetPosY = event.getY();
        }
        else
        {
            // Remove this selection by user interaction.
            this.selected = false;
        }
    }

    /**
     * Mouse release event handler.
     * @param event The mouse event.
     */
    public void mouseReleased(MouseEvent event)
    {
     // Check if this magnet has been selected by user interaction.
        if (this.selected)
        {
            // 
            this.client.moveMagnet(this.serialize());
            // Remove this magnet from selection by user interaction.
            this.selected = false;
        }
    }

    /**
     * Mouse drag event handler. Moves magnet if applicable.
     * @param event The mouse event.
     */
    public void mouseDragged(MouseEvent event)
    {
        // Check if this magnet has been selected by user interaction.
        if (this.selected)
        {
            // Move the magnet
            updatePosition(this.x + event.getX() - this.offsetPosX, this.y + event.getY() - this.offsetPosY);

            // Moving Magnet
            this.client.moveMagnet(new MagnetObject(this.id, this.x, this.y));
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent event) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent event) {
        // TODO Auto-generated method stub

    }

}