package rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
	
	static void createView(String pCommunity, String pAddress, String pOption, String pViewName, String pOID, NetworkStatus pStatus)
	{	
		getCommandLineInput("createView" , "snmpvacm -v1 -c " + pCommunity + " " + pAddress + " createView " + pOption + " " + pViewName + " " + pOID , pStatus);
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
}  // class CLISNMPOperations 
