package rest;

import java.util.ArrayList;

public class NetworkStatus {
    ArrayList <String> lOIDList;
	ArrayList <String> lValueList;
    
    public NetworkStatus()
    {
        lOIDList = new ArrayList<String>();
        lValueList = new ArrayList<String>();
    }  // NetworkStatus
    
    public void setNode(String pOID, String pValue)
    {
    	lOIDList.add(pOID);
    	lValueList.add(pValue);
    }  // public 
    
    @Override
    public String toString()
    {
    	int lNodes = lOIDList.size();
    	StringBuilder lString = new StringBuilder();
    	
    	lString.append("[");
    	
    	for(int lIndex = 0; lIndex < lNodes; lIndex++)
    	{
    		lString.append("{");
    		
    		lString.append("\"OID\":\"" + lOIDList.get(lIndex) + "\",");
    		lString.append("\"Value\":\"" + lValueList.get(lIndex) + "\"");
    		
    		lString.append("}");
    	
    		if(lIndex + 1 < lNodes)
    		{
    			lString.append(",");
    		}  // if
    	}  // for
    	
    	lString.append("]");
        return lString.toString(); 
    }  // String toString
}
