package assignment3;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Kid extends Thread
{
    private JFrame jFrame = null;
    private JPanel content = null;
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    ArrayList<FridgeMagnet> magnets;
    static final int WINDOWSIZE = 500;

    /**
     * 
     */
    public Kid(final String hostname, final int port)
    {
        // Init magnets array
        this.magnets = new ArrayList<FridgeMagnet>();
        try
        {
            // Connect to Fridge (server)
            this.socket = new Socket(hostname, port);
            this.output = new ObjectOutputStream(this.socket.getOutputStream());
            this.input = new ObjectInputStream(this.socket.getInputStream());
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param m
     */
    public void addMagnet(FridgeMagnet m)
    {
        this.magnets.add(m);
        this.content.add(m);
        this.jFrame.repaint();
    }

    /**
     * 
     * @return
     */
    public JFrame getJFrame()
    {
        if (this.jFrame == null)
        {
            this.jFrame = new JFrame();
            this.jFrame.setDefaultCloseOperation(3);
            this.jFrame.setSize(500, 500);
            this.jFrame.setLocation(400, 200);
            this.jFrame.setTitle("Fridge Magnet");
            this.jFrame.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    try
                    {	
                        //Kid.this.out.writeObject(new MagnetObject());
                        output.writeObject(null);
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            });
            this.content = new JPanel();
            this.content.setLayout(null);
            this.jFrame.add(this.content);
        }
        return this.jFrame;
    }
    
    /**
     * 
     */
    public void run()
    {
        while (true)
        {
            try
            {
                MagnetObject m = (MagnetObject) this.input.readObject();
                System.out.println(m);
                // Check if Magnet exists
                if (this.magnets.size() > m.getId())
                {
                    System.out.println("Updated");
                    // Already exists
                    FridgeMagnet t = ((FridgeMagnet) this.magnets.get(m.getId()));
                    // Update it
                    t.updatePosition(m.getPosX(), m.getPosY());
                }
                else
                {
                    // Does not exist
                    // Create it
                    System.out.println("Create");
                    addMagnet(new FridgeMagnet(m.getId(), m.getPosX(), m.getPosY(), m.getLetter(), this));
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @param moveData
     */
    public void moveMagnet(MagnetObject moveData)
    {
        try
        {
            this.output.writeObject(moveData);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param arg
     */
    public static void main(String[] arg)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                Kid application = new Kid("localhost", 6666);
                application.getJFrame().setVisible(true);
                application.start();
            }
        });
    }
}