package org.generator;

import java.util.ArrayList;

public class JSONGenerator
{
    ArrayList<String> mOID;
    ArrayList<String> mTranslateName;
    ArrayList<String> mParentTranslateName;
    
    public JSONGenerator()
    {
        mOID = new ArrayList<String>();
        mTranslateName = new ArrayList<String>();
        mParentTranslateName = new ArrayList<String>();
    }  // JSONGenerator
    
    public void clearData()
    {
        mOID.clear();
        mTranslateName.clear();
        mParentTranslateName.clear();
    }  // void clearData
    
    public void insert(String pOID, String pTranslateName, String pParentTranslateName)
    {
        mOID.add(pOID);
        mTranslateName.add(pTranslateName);
        mParentTranslateName.add(pParentTranslateName);
    }  // void insert
    
    public void checkInsertUpdate(
            String pOID,
            String pTranslateName,
            String pParentTranslateName
            )
    {
        int lSize = mOID.size();
        int lIndex = 0;
        for(; lIndex < lSize; lIndex++)
        {
            if(mOID.get(lIndex).equalsIgnoreCase(pOID))
            {
                break;
            }  // if
        }  // for
        
        if(lIndex >= lSize)
        {
            insert(
                    pOID,
                    pTranslateName,
                    pParentTranslateName
                    );
        }  // if
        else
        {
            update(
                    lIndex,
                    pTranslateName,
                    pParentTranslateName
                    );
        }  // else
    }  // void checkInsertUpdate
    
    void update(int pIndex,
            String pTranslateName,
            String pParentTranslateName
            )
    {
        mTranslateName.set(pIndex, pTranslateName);
        mParentTranslateName.set(pIndex, pParentTranslateName);
    }  // void update
    
    public void remove(String pOID)
    {
        int lSize = mOID.size();
        int lIndex = 0;
        for(; lIndex < lSize; lIndex++)
        {
            if(mOID.get(lIndex).equalsIgnoreCase(pOID))
            {
                break;
            }  // if
        }  // for
        
        if(lIndex >= lSize)
        {
            return;
        }  // if
        
        mOID.remove(lIndex);
        mTranslateName.remove(lIndex);
        mParentTranslateName.remove(lIndex);
    }  // void remove
    
    public String getGeneratedString()
    {
       StringBuilder lJSONBuilder = new StringBuilder();
       
       lJSONBuilder.append("[");
       
       int lSize = mOID.size();
       
       for(int lIndex = 0; lIndex < lSize; lIndex++)
       {
           lJSONBuilder.append("\n{\n");
           lJSONBuilder.append("'name' : '" + mTranslateName.get(lIndex)  + "',\n");
           if(mParentTranslateName.get(lIndex).equalsIgnoreCase("$EMPTY"))
           {
               lJSONBuilder.append("'parent' : '',\n");
           }  // if
           else
           {
               lJSONBuilder.append("'parent' : '" + mParentTranslateName.get(lIndex)  + "',\n");
           }  // else
           lJSONBuilder.append("'oid' : '" + mOID.get(lIndex) + "'\n");
           lJSONBuilder.append("}");
           if(lIndex + 1 < lSize)
           {
               lJSONBuilder.append(",");
           }  // if
       }  // for
       
       lJSONBuilder.append("\n]");
       return lJSONBuilder.toString(); 
   }  // public String getGenerateJSON
}
