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
    static final int LETTERSIZE = 15;
    private Kid client;
    private boolean ignoreMove = true;
    private boolean selected = false;
    private int offsetY;
    private int offsetX;
    private int y;
    private int x;
    private int id;
    static final long serialVersionUID = 1L;

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

        setBounds(x, y, 15, 15);

        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * 
     */
    public boolean contains(int x, int y)
    {
        if ((x <= 15) && (x >= 0) && (y <= 15) && (y >= 0)) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param newX
     * @param newY
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
     * 
     * @return
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
        if (contains(event.getX(), event.getY()))
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
    public void mouseReleased(MouseEvent arg0)
    {
        if (this.selected)
        {
            this.client.moveMagnet(new MagnetObject(this.id, this.x, this.y));
            this.selected = false;
        }
    }

    /**
     * 
     */
    public void mouseDragged(MouseEvent arg0)
    {
        if (this.selected)
        {
            updatePosition(this.x + arg0.getX() - this.offsetX, this.y + arg0.getY() - this.offsetY);
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
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

}