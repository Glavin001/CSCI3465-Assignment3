package assignment3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Socket connection from Client to Server.
 * @author Glavin Wiechert
 *
 */
class Connection extends Thread
{
    /**
     * Socket connection.
     */
    private Socket socket;
    /**
     * Input stream.
     */
    private ObjectInputStream input;
    /**
     * Output stream.
     */
    private ObjectOutputStream output;
    /**
     * Fridge server.
     */
    private Fridge fridge;

    /**
     * Construct a new Connection.
     * @param fridge    The Fridge (server).
     * @param socket    The socket (client).
     */
    public Connection(Fridge fridge, Socket socket)
    {
        this.fridge = fridge;
        this.socket = socket;
        try
        {
            this.output = new ObjectOutputStream(this.socket.getOutputStream());
            this.input = new ObjectInputStream(this.socket.getInputStream());
            
            // Push all Magnets from fridge to kid's to view.
            for (MagnetObject m : fridge.getMagnets()) {
                this.output.writeObject(m);
            }
            
        }
        catch (Exception localException) {}
    }

    /**
     * Method executed when Connection Thread is started.
     */
    public void run()
    {
        boolean isRunning = true;
        while (isRunning) {
            try
            {
                // Get the message data (MagnetObject)
                MagnetObject m = (MagnetObject) this.input.readObject();
                // Check if client (Kid) has disconnected
                if (m == null)
                {
                    // Has disconnected
                    System.out.println("Disconnected.");
                    this.fridge.disconnect(this);
                    // Stop running this Connection loop.
                    isRunning = false;
                }
                // Emit the magnet data to the Fridge
                this.fridge.sendMagnet(m);
            }
            catch (Exception localException) {
                
            }
        }
    }

    /**
     * Output Magnet to socket output stream.
     * @param magnet
     */
    public void sendMagnet(MagnetObject magnet)
    {
        try
        {
            // Emit the magnet to the output stream.
            this.output.writeObject(magnet);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}