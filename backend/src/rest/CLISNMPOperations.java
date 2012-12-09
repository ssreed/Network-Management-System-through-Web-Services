/**
 * 
 * @author Kai - Ting (Danil) Ko
 *
 */

package rest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import rest.NetworkStatus;
import snmpadapter.RMONAlarmObject;
import snmpadapter.RMONEventObject;
import snmpadapter.SNMPCommandObject;

public class CLISNMPOperations 
{
	static void createSec2Group(String pCommunity, String pAddress, String pSecurityName, String pGroupName, NetworkStatus pStatus)
	{	
		getCommandLineInput("createSec2Group" , "snmpvacm -v1 -c " + pCommunity + " " + pAddress + " createSec2Group 1 " + pSecurityName + " " + pGroupName , pStatus);
	}  // void createView

	static void deleteSec2Group(String pCommunity, String pAddress, String pSecurityName, NetworkStatus pStatus)
	{	
		getCommandLineInput("deleteSec2Group" , "snmpvacm -v1 -c " + pCommunity + " " + pAddress + " deleteSec2Group 1 " + pSecurityName, pStatus);
	}  // void createView
	
	static String createView(String pCommunity, String pAddress, String pOption, String pViewName, String pOID)
	{	
		//getCommandLineInput("createView" , "snmpvacm -v1 -c " + pCommunity + " " + pAddress + " createView " + pOption + " " + pViewName + " " + pOID , pStatus);
		SNMPCommandObject lObject = new SNMPCommandObject();
		
		lObject.setCommand("createView");
		lObject.setCommunity(pCommunity);
		lObject.setOID(pOID);
		lObject.setOption(pOption);
		lObject.setViewName(pViewName);
		
		return sendUDPSNMPCommand(lObject, pAddress);
	}  // void createView

	static void deleteView(String pCommunity, String pAddress, String pViewName, String pOID, NetworkStatus pStatus)
	{	
		getCommandLineInput("deleteView" , "snmpvacm -v1 -c " + pCommunity + " " + pAddress + " deleteView " + " " + pViewName + " " + pOID , pStatus);
	}  //  void deleteView
	
	static void createAccess(String pCommunity, String pAddress, String pGroupName, String pReadViewName, String pWriteViewName, String pNotifyViewName, NetworkStatus pStatus)
	{	
		getCommandLineInput("createAccess" , "snmpvacm -v1 -c " + pCommunity + " " + pAddress + " createAccess " + pGroupName + " 1 1 1 " + pReadViewName + " " + pWriteViewName + " " + pNotifyViewName, pStatus);
	}  //  void createAccess
	
	static void deleteAccess(String pCommunity, String pAddress, String pGroupName, NetworkStatus pStatus)
	{	
		getCommandLineInput("deleteAccess" , "snmpvacm -v1 -c " + pCommunity + " " + pAddress + " deleteAccess " + pGroupName + " 1 1" , pStatus);
	}  //  void deleteAccess
	
	static void snmpwalk(String pOID, String pCommunity, String pAddress, NetworkStatus pStatus)
	{	
		getCommandLineInput("OID" , "snmpwalk -v1 -c " + pCommunity + " " + pAddress + " " + pOID , pStatus);
	}  // snmpwalk
	
	static void snmpnetstat(String pCommunity, String pAddress, NetworkStatus pStatus)
	{	
		getCommandLineInput("NETSTAT" , "snmpnetstat -v 2c -c " + pAddress + " -Ca " + pCommunity, pStatus);
	}  // snmpnetstat

	static void snmpstatus(String pCommunity, String pAddress, NetworkStatus pStatus)
	{	
		getCommandLineInput("STATUS" , "snmpstatus -v 2c -c " + pAddress + " " + pCommunity, pStatus);
	}  // void snmpstatus
	
	static void snmptranslate(String pOID, NetworkStatus pStatus)
	{	
		getCommandLineInput("TRANSLATE" , "snmptranslate " + pOID, pStatus);
	}  // void snmptranslate
	
	static void getCommandLineInput(String pTag, String pCommand, NetworkStatus pStatus)
	{
		try {
			Process lRuntimeProcess = Runtime.getRuntime().exec(pCommand);
			
			BufferedReader lInputReader = new BufferedReader(new InputStreamReader(lRuntimeProcess.getInputStream()));
			BufferedReader lErrorReader = new BufferedReader(new InputStreamReader(lRuntimeProcess.getErrorStream()));
		
			String lTemp = lInputReader.readLine();
			
			while(lTemp != null)
			{
				pStatus.setMessage(pTag, lTemp);
				lTemp = lInputReader.readLine();
			}  // while
			
			lTemp = lErrorReader.readLine();
			
			while(lTemp != null)
			{
				pStatus.setMessage(pTag + " ERROR", lTemp);
				lTemp = lErrorReader.readLine();
			}  // while
			
			lInputReader.close();
			lErrorReader.close();
		}  // try
		catch (IOException pException) {
			pStatus.setMessage(pTag + "ERROR", pException.toString());
			pException.printStackTrace();
		}  // catch
	}  // void getCommandLineInput
	
	static String stopSNMPD(String pAddress, String pPassword)
	{	
		SNMPCommandObject lObject = new SNMPCommandObject();
		
		lObject.setCommand("stopSNMPD");
		lObject.setCommunity(pPassword);
		
		return sendUDPSNMPCommand(lObject, pAddress);
	}  // void stopSNMPD
	
	static String startSNMPD(String pAddress, String pPassword)
	{	
		SNMPCommandObject lObject = new SNMPCommandObject();
		
		lObject.setCommand("startSNMPD");
		lObject.setCommunity(pPassword);
		
		return sendUDPSNMPCommand(lObject, pAddress);
	}  // void stopSNMPD
	
	static String stopRMON(String pAddress, String pPassword)
	{	
		SNMPCommandObject lObject = new SNMPCommandObject();
		
		lObject.setCommand("stopRMON");
		lObject.setCommunity(pPassword);
		
		return sendUDPSNMPCommand(lObject, pAddress);
	}  // void stopRMON

	static String setRMONQueue(String pAddress, String pPassword, int pQueueSize)
	{	
		SNMPCommandObject lObject = new SNMPCommandObject();
		
		lObject.setCommand("setRMONQueue");
		lObject.setCommunity(pPassword);
		lObject.setQueueSize(pQueueSize);
		
		return sendUDPSNMPCommand(lObject, pAddress);
	}  // void stopRMON

	
	static String startRMON(String pAddress, String pPassword)
	{	
		SNMPCommandObject lObject = new SNMPCommandObject();
		
		lObject.setCommand("startRMON");
		lObject.setCommunity(pPassword);
		
		return sendUDPSNMPCommand(lObject, pAddress);
	}  // void startRMON
	
	static String showRMONAlarm(String pAddress, String pPassword)
	{	
		SNMPCommandObject lObject = new SNMPCommandObject();
		
		lObject.setCommand("showRMONAlarm");
		lObject.setCommunity(pPassword);
		
		return sendUDPSNMPCommand(lObject, pAddress);
	}  // void showRMONAlarm
	
	static String showRMONEvent(String pAddress, String pPassword)
	{	
		SNMPCommandObject lObject = new SNMPCommandObject();
		
		lObject.setCommand("showRMONEvent");
		lObject.setCommunity(pPassword);
		
		return sendUDPSNMPCommand(lObject, pAddress);
	}  // void startRMONEvent
	
	static String setRMONAlarm(String pAddress, String pPassword, RMONAlarmObject pObject)
	{	
		SNMPCommandObject lObject = new SNMPCommandObject();
		
		lObject.setCommand("setRMONAlarm");
		lObject.setCommunity(pPassword);
		lObject.setAlarmObject(pObject);
		
		return sendUDPSNMPCommand(lObject, pAddress);
	}  // void setRMONAlarm
	
	static String setRMONEvent(String pAddress, String pPassword,RMONEventObject pObject)
	{	
		SNMPCommandObject lObject = new SNMPCommandObject();
		
		lObject.setCommand("setRMONEvent");
		lObject.setCommunity(pPassword);
		lObject.setEventObject(pObject);
		
		return sendUDPSNMPCommand(lObject, pAddress);
	}  // void setRMONEvent
	
	
	public static String sendUDPSNMPCommand(SNMPCommandObject pObject, String pAddress)
	{
		String lResultStatus;
		DatagramSocket mSocket = null;
		
        try
        {
        	// Create a communication
        	InetAddress lHostAddress = 
        			InetAddress.getByName(pAddress);
        	
        	mSocket = new DatagramSocket();
        	
        	ByteArrayOutputStream lOutputStream = new ByteArrayOutputStream();
        	
        	ObjectOutput lObjectOutput = new ObjectOutputStream(lOutputStream);
        	lObjectOutput.writeObject(pObject);
        	lObjectOutput.close();
        	
        	byte [] lSeralizedArray = lOutputStream.toByteArray();
        
            DatagramPacket lPacket = new DatagramPacket(lSeralizedArray,  lSeralizedArray.length, lHostAddress, 8889);
            
            lPacket = new DatagramPacket(lSeralizedArray,  lSeralizedArray.length, lHostAddress, 8889);
            mSocket.send(lPacket);
            
            byte[] lBuffer = new byte[20480];
            lPacket = new DatagramPacket(lBuffer, lBuffer.length);
            mSocket.setSoTimeout(6000);
            mSocket.receive(lPacket);
            
            lResultStatus = new String(lPacket.getData(), 0, lPacket.getLength());
        }  // try
        catch(Exception pException)
        {
            NetworkStatus lStatus = new NetworkStatus();
            lStatus.setMessage("ERROR", pException.toString());
            lResultStatus = lStatus.toString();
        }  // catch
        
        if(mSocket != null)
        {
        	mSocket.close();
        }  // if
        
        return lResultStatus;
	}  // String
}  // class CLISNMPOperations 
