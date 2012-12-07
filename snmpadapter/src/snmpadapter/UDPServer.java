/**
 * 
 * @author Kai - Ting (Danil) Ko
 *
 * Save view information as UDP packet
 */

package snmpadapter;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer extends Thread
{
    private DatagramSocket mSocket;
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
        }  // try
        catch(Exception pException)
        {
            System.out.println("Error start server");
        }  // catch

    }  // UDPServer
    
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                byte[] lBuffer = new byte[10240];
                DatagramPacket lPacket = new DatagramPacket(lBuffer, lBuffer.length);
                mSocket.receive(lPacket);
                
                ObjectInputStream lInputStream = new ObjectInputStream(new ByteArrayInputStream(lPacket.getData()));
                
                SNMPCommandObject lObject = (SNMPCommandObject)lInputStream.readObject();
                lInputStream.close();
                
                String lReturnMessage = "";
                NetworkStatus lStatus = new NetworkStatus();
                
                if(lObject != null)
                {
                	performSNMPCommand(lObject, lStatus);
                }  // if
                else
                {
                	lStatus.setMessage("ERROR", "UDP Packet Error");
                }  // else
                
                lReturnMessage = lStatus.toString();
                lBuffer = lReturnMessage.getBytes();
                lPacket = new DatagramPacket(lBuffer, lBuffer.length, lPacket.getAddress(), lPacket.getPort());
                mSocket.send(lPacket);
            }  // try
            catch(Exception pException)
            {
            	pException.printStackTrace();
            }  // 
        }  // while
    }  // void run
    
    private void performSNMPCommand(SNMPCommandObject pObject, NetworkStatus pStatus)
    {
    	if(pObject.getCommand().equalsIgnoreCase("createView"))
    	{
    		CLISNMPOperations.createView(pObject.getCommunity(), pObject.getOption(), 
    				pObject.getViewName(), pObject.getOID(), pStatus);
    	}  // if
    	else if(pObject.getCommand().equalsIgnoreCase("stopSNMPD"))
    	{
    		CLISNMPOperations.stopSNMPD(pObject.getCommunity(), pStatus);
    	}  // else if
    	else if(pObject.getCommand().equalsIgnoreCase("startSNMPD"))
    	{
    		CLISNMPOperations.startSNMPD(pObject.getCommunity(), pStatus);
    	}  // else if
    	else
    	{
    		pStatus.setMessage("ERROR", "SNMP Command Error");
    	}  // else
    }  // void performSNMPCommand
}  // class UDPServer
