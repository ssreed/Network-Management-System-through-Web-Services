/**
 * 
 * @author Kai - Ting (Danil) Ko
 *
 * Save view information as UDP packet
 */

package snmpadapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLISNMPOperations 
{
	static void createView(String pCommunity, String pOption, String pViewName, String pOID, NetworkStatus pStatus)
	{	
		String lOption = "";
		if(pOption.equalsIgnoreCase("excluded"))
		{
			lOption = "-Ce";
		}  // if
		getCommandLineInput("CREATEVIEW", "snmpvacm -v1 -c " + pCommunity + " localhost " + lOption +  " createView " + pViewName + " " + pOID , pStatus);
	}  // void createView

	static void stopSNMPD(NetworkStatus pStatus)
	{	
		getCommandLineInput("STOPSNMPD","sudo service snmpd stop", pStatus);
		getCommandLineInput("STOPSNMPD", "sudo service snmpd status", pStatus);
	}  // void stopSNMPD

	static void startSNMPD(NetworkStatus pStatus)
	{	
		getCommandLineInput("STARTSNMPD", "sudo service snmpd start", pStatus);
		getCommandLineInput("STARTSNMPD", "sudo service snmpd status", pStatus);
	}  // void startSNMPD

	
	static void getCommandLineInput( String pTag, String pCommand, NetworkStatus pStatus)
	{
		try {
			Process lRuntimeProcess;
			
			lRuntimeProcess = Runtime.getRuntime().exec(pCommand);

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
