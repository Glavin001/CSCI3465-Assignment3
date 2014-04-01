package assignment3;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;

/**
 * 
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
     * 
     */
    private Kid client;
    /**
     * 
     */
    private boolean ignoreMove = true;
    /**
     * 
     */
    private boolean selected = false;
    /**
     * 
     */
    private int offsetY;
    /**
     * 
     */
    private int offsetX;
    /**
     * 
     */
    private int y;
    /**
     * 
     */
    private int x;
    /**
     * 
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
     * @return
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
            setBounds(newX, newY, 15, 15);
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
     * 
     */
    public void mousePressed(MouseEvent event)
    {
        if (touches(event.getX(), event.getY()))
        {
            this.selected = true;
            this.offsetX = event.getX();
            this.offsetY = event.getY();
        }
        else
        {
            this.selected = false;
        }
    }

    /**
     * 
     */
    public void mouseReleased(MouseEvent event)
    {
        if (this.selected)
        {
            this.client.moveMagnet(this.serialize());
            this.selected = false;
        }
    }

    /**
     * 
     */
    public void mouseDragged(MouseEvent event)
    {
        if (this.selected)
        {
            updatePosition(this.x + event.getX() - this.offsetX, this.y + event.getY() - this.offsetY);
            if (this.ignoreMove)
            {
                this.ignoreMove = false;
            }
            else
            {
                System.out.println("Moving Magnet");
                this.client.moveMagnet(new MagnetObject(this.id, this.x, this.y));
                this.ignoreMove = true;
            }
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