import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 
 */

/**
 * @author DANILKO
 *
 */
public class UDPServer extends Thread
{
    private DatagramSocket mSocket;
    private SNMPElement mElement;
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new UDPServer().start();
    }

    public UDPServer()
    {
        try
        {
        mSocket = new DatagramSocket(8889);
        System.out.println("Start success");
        }
        catch(Exception pException)
        {
            
            System.out.println("Error start server");
        }  // catch
        mElement = new SNMPElement();

    }  // UDPServer
    
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                byte[] lBuffer = new byte[256];
                DatagramPacket lPacket = new DatagramPacket(lBuffer, lBuffer.length);
                mSocket.receive(lPacket);
                
                String lReceived = new String(lPacket.getData(), 0, lPacket.getLength());
                
                if(lReceived != null && lReceived != "")
                {
                    mElement.setStatus(lReceived);
                }  // if
                
                lBuffer = (mElement.getName() + "Status: " + mElement.getStatus()).getBytes();
                lPacket = new DatagramPacket(lBuffer, lBuffer.length, lPacket.getAddress(), lPacket.getPort());
                mSocket.send(lPacket);
                
                System.out.println("Element Status Reset " + System.currentTimeMillis());
                System.out.println(mElement.getName() + "Status: " + mElement.getStatus());
            }  // try
            catch(Exception pException)
            {
                System.out.println("Error in server");
            }  // 
        }  // while
        
    }
}
