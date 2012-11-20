package rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLISNMPOperations 
{
	static void snmpnetstat(String pCommunity, String pAddress, NetworkStatus pStatus)
	{	
		try {
			Process lRuntimeProcess = Runtime.getRuntime().exec("snmpnetstat -v 2c -c " + pAddress + " -Ca " + pCommunity);
			
			BufferedReader lInputReader = new BufferedReader(new InputStreamReader(lRuntimeProcess.getInputStream()));
			BufferedReader lErrorReader = new BufferedReader(new InputStreamReader(lRuntimeProcess.getErrorStream()));
		
			String lTemp = lInputReader.readLine();
			
			while(lTemp != null)
			{
				pStatus.setMessage("NETSTAT", lTemp);
				lTemp = lInputReader.readLine();
			}  // while
			
			lTemp = lErrorReader.readLine();
			
			while(lTemp != null)
			{
				pStatus.setMessage("NETSTAT ERROR", lTemp);
				lTemp = lErrorReader.readLine();
			}  // while
			
			lInputReader.close();
			lErrorReader.close();
		}  // try
		catch (IOException pException) {
			pStatus.setMessage("NETSTAT ERROR", pException.toString());
			pException.printStackTrace();
		}  // catch
	}

	static void snmpstatus(String pCommunity, String pAddress, NetworkStatus pStatus)
	{	
		try {
			Process lRuntimeProcess = Runtime.getRuntime().exec("snmpstatus -v 2c -c " + pAddress + " " + pCommunity);
			
			BufferedReader lInputReader = new BufferedReader(new InputStreamReader(lRuntimeProcess.getInputStream()));
			BufferedReader lErrorReader = new BufferedReader(new InputStreamReader(lRuntimeProcess.getErrorStream()));
		
			String lTemp = lInputReader.readLine();
			
			while(lTemp != null)
			{
				pStatus.setMessage("STATUS", lTemp);
				lTemp = lInputReader.readLine();
			}  // while
			
			lTemp = lErrorReader.readLine();
			
			while(lTemp != null)
			{
				pStatus.setMessage("STATUS ERROR", lTemp);
				lTemp = lErrorReader.readLine();
			}  // while
			
			lInputReader.close();
			lErrorReader.close();
		}  // try
		catch (IOException pException) {
			pStatus.setMessage("STATUS ERROR", pException.toString());
			pException.printStackTrace();
		}  // catch
	}
	
	static void snmptranslate(String pOID, NetworkStatus pStatus)
	{	
		try {
			Process lRuntimeProcess = Runtime.getRuntime().exec("snmptranslate " + pOID);
			
			BufferedReader lInputReader = new BufferedReader(new InputStreamReader(lRuntimeProcess.getInputStream()));
			BufferedReader lErrorReader = new BufferedReader(new InputStreamReader(lRuntimeProcess.getErrorStream()));
		
			String lTemp = lInputReader.readLine();
			
			while(lTemp != null)
			{
				pStatus.setMessage("TRANSLATE", lTemp);
				lTemp = lInputReader.readLine();
			}  // while
			
			lTemp = lErrorReader.readLine();
			
			while(lTemp != null)
			{
				pStatus.setMessage("TRANSLATE ERROR", lTemp);
				lTemp = lErrorReader.readLine();
			}  // while
			
			lInputReader.close();
			lErrorReader.close();
		}  // try
		catch (IOException pException) {
			pStatus.setMessage("TRANSLATE ERROR", pException.toString());
			pException.printStackTrace();
		}  // catch
	}  // void snmptranslate
}
