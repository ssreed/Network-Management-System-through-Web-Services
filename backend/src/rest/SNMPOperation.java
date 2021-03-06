/**
 * 
 * @author Kai - Ting (Danil) Ko
 *
 */

package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.json.JSONWithPadding;

import snmp.*;
import snmpadapter.RMONAlarmObject;
import snmpadapter.RMONEventObject;

import java.net.*;

@Path("/snmpoperation")
public class SNMPOperation {
    @Context
    private UriInfo context;

    /**
     * Default constructor. 
     */
    public SNMPOperation() 
    {
    }  // Constructor
    
    /**
     * Retrieves representation of an instance of NetworkDevice
     * @return an HTTP response with content of the updated or created resource in JSON object
     */
    @Path("/api")
    @GET
    @Produces("text/json")
    public JSONWithPadding getJson(@QueryParam("callback") String pCallback) 
    {
    	NetworkStatus lStatus = new NetworkStatus();
        
    	lStatus.setMessage("API", "Under Construction");
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
    }
    
    /**
     * GET method to perform snmpget
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
     * @param pCallBack The callback string required to pass back Javascript calling object
     * @return an HTTP response with content of the updated or created resource in JSON object
     */
    @Path("/status")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding snmpstat(@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
        
        CLISNMPOperations.snmpstatus(pHost, pCommunity, lStatus);
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
    }

    /**
     * GET method to perform snmptranslate
     * @param pOIDs The OIDs that will be snmptranlate will be performed with
     * @param pHost The address of the host for the element
     * @return an HTTP response with content of the updated or created resource in JSON object
     */
    @Path("/translate")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding snmptranslate(@QueryParam("oids") String pOIDs,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
        
        String [] lOIDs = pOIDs.trim().split(";");
    	
    	int lOIDSize = lOIDs.length;
    	
    	for(int lIndex = 0; lIndex < lOIDSize; lIndex++)
    	{
    		if(lOIDs[lIndex].trim() == "")
    		{
    			continue;
    		}  // if
    	
    		CLISNMPOperations.snmptranslate(lOIDs[lIndex], lStatus);
    	}  // for
    	
        return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // snmptranslate
    
    /**
     * GET method to perform snmpnetstatus
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
     * @return an HTTP response with content of the updated or created resource 
     *         in JSON object
     */
    @Path("/netstat")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding snmpnetstat(@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
        
        CLISNMPOperations.snmpnetstat(pHost, pCommunity, lStatus);
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
    }
    
    /**
     * GET method to perform snmpset
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
     * @return an HTTP response with content of the updated or created resource in JSON object
     */
    @Path("/set")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding snmpget(@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("oid") String pOID,
    		@QueryParam("value") String pValue,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
        
        try
        {
        	// Create a communication
        	InetAddress lHostAddress = 
        			InetAddress.getByName(pHost);
        	
        	int lVersion = 0;
        	
        	SNMPv1CommunicationInterface lInterface = new SNMPv1CommunicationInterface(lVersion, lHostAddress, pCommunity);
        	
        	String lSNMPNULL = "class snmp.SNMNULL";
        	String lSNMPOCETSTRING = "class snmp.SNMPOctetString";
        	
        	SNMPVarBindList lSNMPVar = lInterface.getMIBEntry(pOID);
        	SNMPSequence lPair = (SNMPSequence)(lSNMPVar.getSNMPObjectAt(0));
        	SNMPObjectIdentifier lSNMPOID = (SNMPObjectIdentifier)lPair.getSNMPObjectAt(0);
        	SNMPObject lSNMPValue = lPair.getSNMPObjectAt(1);
        	String lSNMPValueType = lSNMPValue.getClass().toString();
        			
        	if(!lSNMPValueType.equals(lSNMPNULL))
        	{
        		lSNMPValue.setValue(pValue);
        		lSNMPVar = lInterface.setMIBEntry(pOID, lSNMPValue);
            	lPair = (SNMPSequence)(lSNMPVar.getSNMPObjectAt(0));
            	lSNMPOID = (SNMPObjectIdentifier)lPair.getSNMPObjectAt(0);
            	lSNMPValue = lPair.getSNMPObjectAt(1);
            	lSNMPValueType = lSNMPValue.getClass().toString();
            	
            	if(lSNMPValueType.equals(lSNMPOCETSTRING))
        		{
        			String lSNMPValueString = lSNMPValue.toString();
        			int lNULLIndex = lSNMPValueString.indexOf('\0');
        			if(lNULLIndex >= 0)
        			{
        				lSNMPValueString = lSNMPValueString.substring(0, lNULLIndex);
        			}  // if
        			lStatus.setMessage("OID Name", "OID: " + lSNMPOID + " Type: " + lSNMPValueType + " Value: " + lSNMPValueString + " (hex: " +((SNMPOctetString)lSNMPValue).toHexString() + ")");      				
        		}  // else
            	else
            	{
            		lStatus.setMessage("OID Name", "OID: " + lSNMPOID + " Type: " + lSNMPValueType + " Value: " + lSNMPValue);
            	}  // else
        	}  // if
        	else
        	{
        		lStatus.setMessage("ERROR", "Cannot set the variable since it cannot be retrieved");
        	}  // else
        	
        lInterface.closeConnection();
        }  // try
        catch(UnknownHostException pException)
        {
        	lStatus.setMessage("ERROR", "Invalid Address");
        }  // catch
        catch(NumberFormatException pException)
        {
        	lStatus.setMessage("ERROR", "Cannot translate " + pValue + " into number format. ");
        	lStatus.setMessage("ERROR", "Expect number input, but get string input");
        }  // catch
        catch(Exception pException)
        {
        	lStatus.setMessage("ERROR", pException.toString());
        }  // catch
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding snmpset

    /**
     * test method to perform snmptest
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
     * @param pOIDs The OIDs that snmptest will be performed with
     * @return an HTTP response with content of the updated or created resource in JSON objects
     */
    @Path("/test")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding snmptest(@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("oids") String pOIDs,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
       
        try
        {
        	// Create a communication
        	InetAddress lHostAddress = 
        			InetAddress.getByName(pHost);
        	
        	int lVersion = 0;
        	
        	SNMPv1CommunicationInterface lInterface = new SNMPv1CommunicationInterface(lVersion, lHostAddress, pCommunity);
        	
        	String lSNMPNULL = "class snmp.SNMNULL";
        	String lSNMPOCETSTRING = "class snmp.SNMPOctetString";
        	
        	String [] lOIDs = pOIDs.trim().split(";");
        	
        	
        	int lOIDSize = lOIDs.length;
        	
        	for(int lIndex = 0; lIndex < lOIDSize; lIndex++)
        	{
        		if(lOIDs[lIndex].trim() == "")
        		{
        			continue;
        		}  // if
        		
        		SNMPVarBindList lSNMPVar = lInterface.getMIBEntry(lOIDs[lIndex]);
        		SNMPSequence lPair = (SNMPSequence)(lSNMPVar.getSNMPObjectAt(0));
        		SNMPObjectIdentifier lSNMPOID = (SNMPObjectIdentifier)lPair.getSNMPObjectAt(0);
        		SNMPObject lSNMPValue = lPair.getSNMPObjectAt(1);
        		String lSNMPValueType = lSNMPValue.getClass().toString();
        			
        		if(!lSNMPValueType.equals(lSNMPNULL))
        		{
        			if(lSNMPValueType.equals(lSNMPOCETSTRING))
        			{
        				String lSNMPValueString = lSNMPValue.toString();
        				int lNULLIndex = lSNMPValueString.indexOf('\0');
        				if(lNULLIndex >= 0)
        				{
        					lSNMPValueString = lSNMPValueString.substring(0, lNULLIndex);
        				}  // if
        					lStatus.setMessage("OID Name", "OID: " + lSNMPOID + " Type: " + lSNMPValueType + " Value: " + lSNMPValueString + " (hex: " +((SNMPOctetString)lSNMPValue).toHexString() + ")");      				
        			}  // else
        			else
        			{
        				lStatus.setMessage("OID Name", "OID: " + lSNMPOID + " Type: " + lSNMPValueType + " Value: " + lSNMPValue);
        			}  // else
        		}  // if
        	}  // for
        	lInterface.closeConnection();
        }  // try
        catch(UnknownHostException pException)
        {
        	lStatus.setMessage("Error", "Invalid Address");
        }  // catch
        catch(Exception pException)
        {
        	lStatus.setMessage("ERROR", "Error in generating data " + pException.toString());
        }  // catch
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding snmptest
    
    /**
     * GET method to perform snmpget
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
     * @param pOID The OID that snmpget with be performed with
     * @return an HTTP response with content of the updated or created resource.
     */
    @Path("/get")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding snmpget(@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("oid") String pOID,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
        
        try
        {
        	// Create a communication
        	InetAddress lHostAddress = 
        			InetAddress.getByName(pHost);
        	
        	int lVersion = 0;
        	
        	SNMPv1CommunicationInterface lInterface = new SNMPv1CommunicationInterface(lVersion, lHostAddress, pCommunity);
        	
        	String lSNMPNULL = "class snmp.SNMNULL";
        	String lSNMPOCETSTRING = "class snmp.SNMPOctetString";

        	SNMPVarBindList lSNMPVar = lInterface.getMIBEntry(pOID);
        	SNMPSequence lPair = (SNMPSequence)(lSNMPVar.getSNMPObjectAt(0));
        	SNMPObjectIdentifier lSNMPOID = (SNMPObjectIdentifier)lPair.getSNMPObjectAt(0);
        	SNMPObject lSNMPValue = lPair.getSNMPObjectAt(1);
        	String lSNMPValueType = lSNMPValue.getClass().toString();
        			
        	if(!lSNMPValueType.equals(lSNMPNULL))
        	{
        		if(lSNMPValueType.equals(lSNMPOCETSTRING))
        		{
        			String lSNMPValueString = lSNMPValue.toString();
        			int lNULLIndex = lSNMPValueString.indexOf('\0');
        			if(lNULLIndex >= 0)
        			{
        				lSNMPValueString = lSNMPValueString.substring(0, lNULLIndex);
        			}  // if
        			lStatus.setMessage("OID Name", "OID: " + lSNMPOID + " Type: " + lSNMPValueType + " Value: " + lSNMPValueString + " (hex: " +((SNMPOctetString)lSNMPValue).toHexString() + ")");      				
        		}  // else
        		else
        		{
        			lStatus.setMessage("OID Name", "OID: " + lSNMPOID + " Type: " + lSNMPValueType + " Value: " + lSNMPValue);
        		}  // else
        	}  // if
        	
        lInterface.closeConnection();
        }  // try
        catch(UnknownHostException pException)
        {
        	lStatus.setMessage("Error", "Invalid Address");
        }  // catch
        catch(Exception pException)
        {
        	lStatus.setMessage("ERROR", "Error in generating data" + pException.toString());
        }  // catch
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding snmpget
    
    /**
     * GET method to perform snmpwalk
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
     * @param pOID The OID that the snmpwalk will be start with
     * @return an HTTP response with content of the updated or created resource in JSON objects
     */
    @Path("/walk")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding snmpWalk(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("oid") String pOID,
    		@QueryParam("callback") String pCallback) 
    {
        
        NetworkStatus lStatus = new NetworkStatus();
        
        CLISNMPOperations.snmpwalk(pOID, pCommunity, pHost, lStatus);
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding snmpWalk
    
    /**
     * GET method to perform startsnmpd
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
     * @return an HTTP response with content of the updated or created resource in JSON objects
     */
    @Path("/startsnmpd")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding startsnmpd(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("callback") String pCallback) 
    {
        return new JSONWithPadding(CLISNMPOperations.startSNMPD(pHost, pCommunity), pCallback);
    }  // JSONWithPadding startsnmpd
    
    /**
     * GET method to perform stopsnmpd
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
     * @return an HTTP response with content of the updated or created resource in JSON objects
     */
    @Path("/stopsnmpd")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding stopsnmpd(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("callback") String pCallback) 
    {
        return new JSONWithPadding(CLISNMPOperations.startSNMPD(pHost, pCommunity), pCallback);
    }  // JSONWithPadding stopsnmpd
    
    @Path("/createsec2group")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding createSec2Group(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("securityname") String pSecurityName,
    		@QueryParam("groupname") String pGroupName,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
    	CLISNMPOperations.createSec2Group(pCommunity, pHost, pSecurityName, pGroupName, lStatus);
    	return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding createSec2Group

    @Path("/deletesec2group")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding deleteSec2Group(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("securityname") String pSecurityName,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
    	CLISNMPOperations.deleteSec2Group(pCommunity, pHost, pSecurityName, lStatus);
    	return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding deleteSec2Group
    
    @Path("/createview")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding createView(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("option") String pOption,
    		@QueryParam("viewname") String pViewName,
    		@QueryParam("oid") String pOID,
    		@QueryParam("callback") String pCallback) 
    {
    	return new JSONWithPadding(
    			CLISNMPOperations.createView(pCommunity, pHost, pOption, pViewName, pOID), 
    			pCallback);
    }  // JSONWithPadding createView
    
    @Path("/deleteview")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding deleteView(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("viewname") String pViewName,
    		@QueryParam("oid") String pOID,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
    	CLISNMPOperations.deleteView(pCommunity, pHost, pViewName, pOID, lStatus);
    	return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding deleteView
    
    @Path("/createaccess")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding createAccess(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("groupname") String pGroupName,
    		@QueryParam("readviewname") String pReadViewName,
    		@QueryParam("writeviewname") String pWriteViewName,
    		@QueryParam("notifyviewname") String pNotifyViewName,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
    	CLISNMPOperations.createAccess(pCommunity, pHost, pGroupName, pReadViewName, pWriteViewName, pNotifyViewName, lStatus);
    	return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding createAccess
    
    @Path("/deleteaccess")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding deleteAccess(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("groupname") String pGroupName,
    		@QueryParam("callback") String pCallback) 
    {
        NetworkStatus lStatus = new NetworkStatus();
    	CLISNMPOperations.deleteAccess(pCommunity, pHost, pGroupName, lStatus);
    	return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding deleteAccess
    
    @Path("/enablesnmpagent")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding enableSNMPAgent(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("option") String pOption,
    		@QueryParam("callback") String pCallback) 
    {
    	if(pOption.equalsIgnoreCase("enable"))
    	{
    		return new JSONWithPadding(
    			CLISNMPOperations.startSNMPD(pHost, pCommunity), 
    			pCallback);
    	}  // if
    	
    	return new JSONWithPadding(
    			CLISNMPOperations.stopSNMPD(pHost, pCommunity), 
    			pCallback);
    }  // JSONWithPadding enableSNMPAgent
    
    @Path("/enablermon")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding enableRMON(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("option") String pOption,
    		@QueryParam("callback") String pCallback) 
    {
    	if(pOption.equalsIgnoreCase("enable"))
    	{
    		return new JSONWithPadding(
    			CLISNMPOperations.startRMON(pHost, pCommunity), 
    			pCallback);
    	}  // if
    	
		return new JSONWithPadding(
			CLISNMPOperations.stopRMON(pHost, pCommunity), 
			pCallback);
    }  // JSONWithPadding enableRMON
    
    @Path("/setrmonqueue")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding setRMONQueue(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("value") int pValue,
    		@QueryParam("callback") String pCallback) 
    {
		return new JSONWithPadding(
				CLISNMPOperations.setRMONQueue(pHost, pCommunity, pValue), 
			pCallback);
    }  // JSONWithPadding enableRMON
    
    @Path("/showrmonevent")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding showRMONEvent(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("callback") String pCallback) 
    {	
		return new JSONWithPadding(
				CLISNMPOperations.showRMONEvent(pHost, pCommunity), 
			pCallback);
    }  // JSONWithPadding enableRMONEvent
    
    @Path("/showrmonalarm")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding showRMONAlarm(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("callback") String pCallback) 
    {	
		return new JSONWithPadding(
				CLISNMPOperations.showRMONAlarm(pHost, pCommunity), 
			pCallback);
    }  // JSONWithPadding enableRMONAlarm
    
    @Path("/setrmonalarm")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding setRMONAlarm(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("alarmindex") int pAlarmIndex,
    		@QueryParam("alarminterval") int pAalarmInterval,
    		@QueryParam("alarmvariable") String pAlarmVariable,
    		@QueryParam("alarmsampletype") String pAlarmSampleType,
    		@QueryParam("alarmstartupalarm") String pAlarmStartupAlarm,
    		@QueryParam("alarmrisingthreshold") int pAalarmRisingThreshold,
    		@QueryParam("alarmfallingthreshold") int pAalarmFallingThreshold,
    		@QueryParam("alarmrisingeventIndex") int pAalarmRisingEventIndex,
    		@QueryParam("alarmfallingeventIndex") int pAalarmFallingEventIndex,
    		@QueryParam("alarmstatus") String pAlarmStatus,
    		@QueryParam("callback") String pCallback) 
    {
    	RMONAlarmObject lObject = new RMONAlarmObject();
    	
    	lObject.setFallingEventIndex(pAalarmFallingEventIndex);
    	lObject.setRisingEventIndex(pAalarmRisingEventIndex);
    	lObject.setIndex(pAlarmIndex);
    	lObject.setRisingThreshold(pAalarmRisingThreshold);
    	lObject.setFallingThreshold(pAalarmFallingThreshold);
    	lObject.setCommunity(pCommunity);
    	lObject.setIntervalOption("delta");
    	lObject.setStatus(pAlarmStatus);
    	lObject.setInverval(pAalarmInterval);
    	
		return new JSONWithPadding(
			CLISNMPOperations.setRMONAlarm(pHost, pCommunity, lObject), 
			pCallback);
    }  // JSONWithPadding enableRMONAlarm
    
    @Path("/setrmonevent")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding setRMONEvent(
    		@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("eventindex") int pEventIndex,
    		@QueryParam("eventdescription") String pEventDescription,
    		@QueryParam("eventtype") String pEventtype,
    		@QueryParam("eventCommunity") String pEventCommunity,
    		@QueryParam("eventlasttimesent") String pEventLastTimeSent,
    		@QueryParam("eventowner") String pEventOwner,
    		@QueryParam("eventstatus") String pEventStatus,
    		@QueryParam("callback") String pCallback) 
    {
    	RMONEventObject lObject = new RMONEventObject();
    	lObject.setCommunity(pEventCommunity);
    	lObject.setIndex(pEventIndex);
    	lObject.setDescription(pEventDescription);
    	lObject.setLastTimeSent(pEventLastTimeSent);
    	lObject.setStatus(pEventStatus);
    	lObject.setOwner(pEventOwner);
    	
		return new JSONWithPadding(
				CLISNMPOperations.setRMONEvent(pHost, pCommunity, lObject), 
			pCallback);
    }  // JSONWithPadding setRMONEvent
}  // class SNMPOperation