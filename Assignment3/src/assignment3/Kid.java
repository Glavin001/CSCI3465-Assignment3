package assignment3;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The Client GUI.
 * @author Glavin Wiechert
 *
 */
public class Kid extends Thread
{
    /**
     * 
     */
    private JFrame frame = null;
    /**
     * 
     */
    private JPanel content = null;
    /**
     * Socket connection.
     */
    Socket socket;
    /**
     * Input stream.
     */
    ObjectInputStream input;
    /**
     * Output stream.
     */
    ObjectOutputStream output;
    /**
     * Array of Magnets on the Fridge door.
     */
    ArrayList<FridgeMagnet> magnets;
    /**
     * Size of the frame.
     */
    private final int FRAMESIZE = 400;

    /**
     * Constructor
     * @param hostname
     * @param port
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
     * Add a new magnet.
     * @param m
     */
    public void addMagnet(FridgeMagnet m)
    {
        this.magnets.add(m);
        this.content.add(m);
        this.frame.repaint();
    }

    /**
     * Get the frame; create if not already initialized.
     * @return  The initialized frame.
     */
    public JFrame getFrame()
    {
        // Check if there already is a frame
        if (this.frame == null)
        {
            // Frame has not been initialized
            this.frame = new JFrame();
            this.frame.setDefaultCloseOperation(3);
            this.frame.setTitle("Fridge Magnet");
            this.frame.setSize(FRAMESIZE, FRAMESIZE);
            
            this.content = new JPanel();
            this.content.setLayout(null);
            this.frame.add(this.content);
            this.content.setSize(FRAMESIZE, FRAMESIZE);

            JLabel nameLabel = new JLabel("Glavin Wiechert");
            nameLabel.setBounds(10,10, 100, 20);
            this.content.add(nameLabel);

            // Emit on close event
            this.frame.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent e)
                {
                    try
                    {	
                        output.writeObject(null);
                    }
                    catch (IOException err)
                    {
                        err.printStackTrace();
                    }
                }
            });
            
        }
        return this.frame;
    }
    
    /**
     * Run loop
     */
    public void run()
    {
        while (true)
        {
            try
            {
                MagnetObject m = (MagnetObject) this.input.readObject();
                // Check if Magnet exists
                if (this.magnets.size() > m.getId())
                {
                    // Already exists
                    FridgeMagnet t = ((FridgeMagnet) this.magnets.get(m.getId()));
                    // Update it
                    t.updatePosition(m.getPosX(), m.getPosY());
                }
                else
                {
                    // Does not exist
                    // Create it
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
     * Emit that a magnet 
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
     * Start the client.
     * @param args
     */
    public static void main(final String[] args)
    {        
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                Kid client;
                if (args.length == 2)
                {
                    String hostname = args[0];
                    int port = Integer.parseInt(args[1]);
                    
                    // Use arguements
                    client = new Kid(hostname, port);
                }
                else
                {
                    // Use Defaults
                    client = new Kid("localhost", 6666);
                }
                client.getFrame().setVisible(true);
                client.start();
            }
        });
    }
}