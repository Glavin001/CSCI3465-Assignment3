package assignment3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Connection extends Thread
{
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Fridge fridge;

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

    public void run()
    {
        boolean isRunning = true;
        while (isRunning) {
            try
            {
                MagnetObject m = (MagnetObject) this.input.readObject();
                if (m == null)
                {
                    System.out.println("Disconnected.");
                    this.fridge.disconnect(this);
                    isRunning = false;
                }
                System.out.println(m);
                this.fridge.sendMessages(m);
            }
            catch (Exception localException) {}
        }
    }

    public void sendMessage(MagnetObject message)
    {
        try
        {
            this.output.writeObject(message);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}