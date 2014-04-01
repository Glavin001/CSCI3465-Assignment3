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
    private ArrayList<Connection> connections;
    private ArrayList<MagnetObject> magnets;
    private ServerSocket serverSocket;
    private Socket socket;
    private final ReentrantLock sendLock = new ReentrantLock();
    private final double FRAMESIZE = 200;
    private final int MAGNETSCOUNT = 20;

    /**
     * 
     */
    public Fridge()
    {
        this.connections = new ArrayList<Connection>();
        this.magnets = new ArrayList<MagnetObject>();
    }

    /**
     * 
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
                MagnetObject m = new MagnetObject(i, new Double(Math.random() * FRAMESIZE).intValue(), 
                        new Double(Math.random() * FRAMESIZE).intValue(), c);
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
            System.exit(-1);
        }
    }

    /**
     * 
     * @param message
     */
    public void sendMessages(MagnetObject message)
    {
        this.sendLock.lock();
        // 
        ((MagnetObject) this.magnets.get(message.getId())).updatePosition(message.getPosX(), message.getPosY());
        try
        {
            // 
            for (Connection conn : this.connections) {
                conn.sendMessage(message);
            }
        }
        catch (Exception localException)
        {

        }
        finally
        {
            this.sendLock.unlock();
        }
    }

    /**
     * 
     * @param c
     */
    public void disconnect(Connection c)
    {
        this.connections.remove(c);
    }

    /**
     * 
     * @return
     */
    public ArrayList<MagnetObject> getMagnets()
    {
        return this.magnets;
    }

    /**
     * 
     * @param arg
     */
    public static void main(String[] arg)
    {
        Fridge m = new Fridge();
        m.listen(6666);
    }
}