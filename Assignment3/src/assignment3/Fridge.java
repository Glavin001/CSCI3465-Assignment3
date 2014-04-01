package assignment3;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author Glavin Wiechert
 *
 */
public class Fridge
{
    /**
     * Open connections.
     */
    private ArrayList<Connection> connections;
    /**
     * All magnets.
     */
    private ArrayList<MagnetObject> magnets;
    /**
     * Socket Server.
     */
    private ServerSocket serverSocket;
    /**
     * Socket.
     */
    private Socket socket;
    /**
     * Reusable lock.
     */
    private final ReentrantLock LOCK = new ReentrantLock();
    /**
     * Size of Fridge frame.
     */
    private final double FRAMESIZE = 400;
    /**
     * Number of magnets to create.
     */
    private final int MAGNETSCOUNT = 20;

    /**
     * Constructor
     */
    public Fridge()
    {
        this.connections = new ArrayList<Connection>();
        this.magnets = new ArrayList<MagnetObject>();
    }

    
    /**
     * Start socket server listening for clients.
     *
     * @param port  The port to listen to.
     */
    public void listen(final int port)
    {
        try
        {
            // Create FridgeMagnets and place on Fridge
            for (int i = 0; i < MAGNETSCOUNT; i++)
            {
                // Source: http://webcache.googleusercontent.com/a/5202888
                Random r = new Random();
                char c = (char) (r.nextInt(26) + 'a');
                // 
                MagnetObject m = new MagnetObject(
                        i,
                        (int) ((Double) Math.random() * FRAMESIZE),
                        (int) ((Double) Math.random() * FRAMESIZE),
                        c);
                this.magnets.add(m);
            }
            this.serverSocket = new ServerSocket(port);
            System.out.println("Listening on port "+port+".");
            
            // Listen for new connections
            while (true)
            {
                this.socket = this.serverSocket.accept();
                System.out.println("Found new connection.");
                Connection conn = new Connection(this, this.socket);
                this.connections.add(conn);
                conn.start();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Updates the respective Magnet and emits to other connections.
     * @param magnet
     */
    public void sendMagnet(MagnetObject magnet)
    {
        // Lock: Only send one at a time.
        this.LOCK.lock();
        // 
        ((MagnetObject) this.magnets.get(magnet.getId())).updatePosition(magnet.getPosX(), magnet.getPosY());
        try
        {
            // 
            for (Connection conn : this.connections) {
                conn.sendMagnet(magnet);
            }
        }
        finally
        {
            // Unlock: now can send others.
            this.LOCK.unlock();
        }
    }

    /**
     * Remove connection from list of active connections.
     * @param conn The connection to remove.
     */
    public void disconnect(Connection conn)
    {
        this.connections.remove(conn);
    }

    /**
     * Get array of magnets.
     * @return All magnets.
     */
    public ArrayList<MagnetObject> getMagnets()
    {
        return this.magnets;
    }

    /**
     * Start the Application.
     * @param arg   Command-line arguments.
     */
    public static void main(String[] arg)
    {
        Fridge m = new Fridge();
        m.listen(6666);
    }
}