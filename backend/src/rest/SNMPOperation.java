package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.codehaus.jackson.map.util.JSONPObject;

import com.sun.jersey.api.json.JSONWithPadding;

import snmp.*;


import java.util.*;
import java.math.*;
import java.net.*;

@Path("/snmpoperation")
public class SNMPOperation {
    @Context
    private UriInfo context;

    /**
     * Default constructor. 
     */
    public SNMPOperation() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Retrieves representation of an instance of NetworkDevice
     * @return an instance of String
     */
    @GET
    @Produces("text/json")
    public String getJson() {

        String lURI = "http://localhost:8080/CS158B_WEBSERVICES/rest/networkdevice/snmpwalk/{host}/{community}";
        
        return "SNMPWALK URI: " + lURI +"}";
    }

    /**
     * GET method to perform snmpget
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
     * @return an HTTP response with content of the updated or created resource.
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
        	
        	SNMPVarBindList lSNMPVar = lInterface.getNextMIBEntry(pOID);
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
        	lStatus.setMessage("Error", "Invalid Address");
        }  // catch
        catch(Exception pException)
        {
        	lStatus.setMessage("ERROR", pException.toString());
        }  // catch
        
        System.out.println(lStatus.toString());
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding snmpget
    
    /**
     * GET method to perform snmpget
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
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

        	SNMPVarBindList lSNMPVar = lInterface.getNextMIBEntry(pOID);
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
        
        System.out.println(lStatus.toString());
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
    }  // JSONWithPadding snmpget
    
    /**
     * GET method to perform snmpwalk
     * @param pHost The address of the host for the element
     * @param pCommunity The community of the host for the element
     * @return an HTTP response with content of the updated or created resource.
     */
    @Path("/walk")
    @GET
    @Produces("application/javascript")
    public JSONWithPadding snmpWalk(@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity,
    		@QueryParam("oid") String pOID,
    		@QueryParam("callback") String pCallback) {
        
        NetworkStatus lStatus = new NetworkStatus();
        
        try
        {
        	// Create a communication
        	InetAddress lHostAddress = 
        			InetAddress.getByName(pHost);
        	
        	int lVersion = 0;
        	
        	SNMPv1CommunicationInterface lInterface = new SNMPv1CommunicationInterface(lVersion, lHostAddress, pCommunity);
        	String lRetrievedOID = pOID;
        	
        	String lSNMPNULL = "class snmp.SNMNULL";
        	String lSNMPOCETSTRING = "class snmp.SNMPOctetString";
        	do
        	{
        		try
        		{
        			SNMPVarBindList lSNMPVar = lInterface.getNextMIBEntry(lRetrievedOID);
        			SNMPSequence lPair = (SNMPSequence)(lSNMPVar.getSNMPObjectAt(0));
        			SNMPObjectIdentifier lSNMPOID = (SNMPObjectIdentifier)lPair.getSNMPObjectAt(0);
        			SNMPObject lSNMPValue = lPair.getSNMPObjectAt(1);
        			lRetrievedOID = lSNMPOID.toString();
        			String lSNMPValueType = lSNMPValue.getClass().toString();
        			
        			if(lSNMPValueType.equals(lSNMPNULL))
        			{
        				break;
        			}  // if
        			else if(lSNMPValueType.equals(lSNMPOCETSTRING))
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
        		}  // try
        		catch(Exception pException)
        		{
        			lStatus.setMessage("ERROR", pException.toString());
        			break;
        		}  // catch
        	}while(true);
        	
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
        
        System.out.println(lStatus.toString());
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
        }  // JSONWithPadding snmpWalk

}