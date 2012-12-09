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
	private boolean mRunning;
	private static RMONAdapter mAdapter;
    private DatagramSocket mSocket;
    
	private boolean lCustomString = false;
	private String lString = "";
	
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        new UDPServer().start();
        mAdapter = new RMONAdapter();
        mAdapter.start();
    }

    public UDPServer()
    {
    	mRunning = true;
    	
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
        while(mRunning)
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
                
                lCustomString = false;
                
                if(lObject != null)
                {
                	performSNMPCommand(lObject, lStatus);
                }  // if
                else
                {
                	lStatus.setMessage("ERROR", "UDP Packet Error");
                }  // else
                
                if(lCustomString)
                {
                	lReturnMessage = lString;
                }  // if
                else
                {
                	lReturnMessage = lStatus.toString();
                }  // else
                lBuffer = lReturnMessage.getBytes();
                lPacket = new DatagramPacket(lBuffer, lBuffer.length, lPacket.getAddress(), lPacket.getPort());
                mSocket.send(lPacket);
            }  // try
            catch(Exception pException)
            {
            	pException.printStackTrace();
            }  // catch 
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
    		if(pObject.getCommunity().equalsIgnoreCase("linux"))
    		{
    			CLISNMPOperations.stopSNMPD(pStatus);
    		}  // if
    		else
    		{
    			pStatus.setMessage("ERROR", "Invalid Password");
    		}  // else
    	}  // else if
    	else if(pObject.getCommand().equalsIgnoreCase("startSNMPD"))
    	{
    		if(pObject.getCommunity().equalsIgnoreCase("linux"))
    		{
    			CLISNMPOperations.startSNMPD(pStatus);
    		}  // if
    		else
    		{
    			pStatus.setMessage("ERROR", "Invalid Password");
    		}  // else
    	}  // else if
    	else if(pObject.getCommand().equalsIgnoreCase("startRMON"))
    	{
    		if(pObject.getCommunity().equalsIgnoreCase("linux"))
    		{
    			mAdapter.setEnable(true);
    			pStatus.setMessage("SUCCESS", "RMON Enalbe Success");
    		}  // if
    		else
    		{
    			pStatus.setMessage("ERROR", "Invalid Password");
    		}  // else
    	}  // else if
    	else if(pObject.getCommand().equalsIgnoreCase("stopRMON"))
    	{
    		if(pObject.getCommunity().equalsIgnoreCase("linux"))
    		{
    			mAdapter.setEnable(false);
    			pStatus.setMessage("SUCCESS", "RMON Diable Success");
    		}  // if
    		else
    		{
    			pStatus.setMessage("ERROR", "Invalid Password");
    		}  // else
    	}  // else if
    	else if(pObject.getCommand().equalsIgnoreCase("showRMONAlarm"))
    	{
    		lCustomString = true;
    		if(pObject.getCommunity().equalsIgnoreCase("linux"))
    		{
    			lString = mAdapter.getRMONAlarm();
    		}  // if
    		else
    		{
       			RMONAlarmObject lObject = new RMONAlarmObject();
    			lString = "[" + lObject.toString() + "]";
    		}  // else
    	}  // else if
    	else if(pObject.getCommand().equalsIgnoreCase("showRMONEvent"))
    	{
    		lCustomString = true;
    		if(pObject.getCommunity().equalsIgnoreCase("linux"))
    		{
    			lString = mAdapter.getRMONEvent();
    		}  // if
    		else
    		{
    			RMONEventObject lObject = new RMONEventObject();
    			lString = "[" + lObject.toString() + "]";
    		}  // else
    	}  // else if
    	else if(pObject.getCommand().equalsIgnoreCase("setRMONAlarm"))
    	{
    		lCustomString = true;
    		if(pObject.getCommunity().equalsIgnoreCase("linux"))
    		{
    			mAdapter.updateRMONAlarm(pObject.getAlarmObject());
    			lString = mAdapter.getRMONAlarm();
    		}  // if
    		else
    		{
       			RMONEventObject lObject = new RMONEventObject();
    			lString = "[" + lObject.toString() + "]";
    		}  // else
    	}  // else if
    	else if(pObject.getCommand().equalsIgnoreCase("setRMONEvent"))
    	{
    		if(pObject.getCommunity().equalsIgnoreCase("linux"))
    		{
    			mAdapter.updateRMONEvent(pObject.getEventObject());
    			lString = mAdapter.getRMONEvent();
    		}  // if
    		else
    		{
       			RMONEventObject lObject = new RMONEventObject();
    			lString = "[" + lObject.toString() + "]";
    		}  // else
    	}  // else if
    	else if(pObject.getCommand().equalsIgnoreCase("setRMONQueue"))
    	{
    		if(pObject.getCommunity().equalsIgnoreCase("linux"))
    		{
    			if(mAdapter.updateQueueSize(pObject.getQueueSize()))
    			{
    				pStatus.setMessage("Sucess", "success set queue size");
    			}  // if
    			else
    			{
    				pStatus.setMessage("ERROR", "Invalid input");
    			}  // else
    		}  // if
    		else
    		{
    			pStatus.setMessage("ERROR", "Invalid Password");
    		}  // else
    	}  // else if
    	else
    	{
    		pStatus.setMessage("ERROR", "SNMP Command Error");
    	}  // else
    }  // void performSNMPCommand
    
    public boolean getRunning()
    {
    	return mRunning;
    }  // boolean getRunning
    
    public void setRunning(boolean pRunning)
    {
    	mRunning = pRunning;
    }  // void setRunning
}  // class UDPServer
