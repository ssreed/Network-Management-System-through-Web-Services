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

    @Path("/connect")
    @GET
    @Produces("text/json")
    public String connect(@QueryParam("host") String pHost, 
    		@QueryParam("community") String pCommunity)
    {

        StringBuilder lMessage = new StringBuilder();
        lMessage.append("[{");
        
        
        try
        {
        	// Create a communication
        	InetAddress lHostAddress = 
        			InetAddress.getByName(pHost);
        	
        	int lVersion = 0;
        	
        	SNMPv1CommunicationInterface lInterface = new SNMPv1CommunicationInterface(lVersion, lHostAddress, pCommunity);
        	
        	lInterface.closeConnection();
        	
        	lMessage.append("{Status: Success}");
        }  // try
        catch(UnknownHostException pException)
        {
        	lMessage.append("{ERROR: Invalid Address}");
        }  // catch
        catch(Exception pException)
        {
        	lMessage.append("{ERROR: Error in generating data" +
        			pException.toString() +
        			"}");
        }  // catch 
        
        lMessage.append("}]");
        
        return lMessage.toString();
    }  // 
    
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
        	SNMPVarBindList lVar = lInterface.getMIBEntry(pOID);
        	SNMPSequence lPair = (SNMPSequence)(lVar.getSNMPObjectAt(0));
        	
        	SNMPObjectIdentifier lOID = (SNMPObjectIdentifier)lPair.getSNMPObjectAt(0);
        	String lValue = lPair.getSNMPObjectAt(1).toString();
        	
        	lStatus.setNode(lOID.toString(), lValue);
        	
        	lInterface.closeConnection();
        }  // try
        catch(UnknownHostException pException)
        {
        	lStatus.setNode("ERROR", "Invalid Address");
        }  // catch
        catch(SNMPBadValueException pException)
        {
        	lStatus.setNode("ERROR", "Fail to communicate with the device. (Incorrect community?)");
        }  // catch
        catch(Exception pException)
        {
        	lStatus.setNode("ERROR", "Error in generating data");
        }  // catch
        
        return new JSONWithPadding(lStatus.toString(), pCallback);
        }

}