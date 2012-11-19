package org.generator;

import java.util.ArrayList;

public class SQLGenerator
{
    ArrayList<String> mOID;
    ArrayList<String> mCurrentOID;
    ArrayList<String> mTranslateName;
    ArrayList<String> mParentTranslateName;
    
    public SQLGenerator()
    {
        mOID = new ArrayList<String>();
        mTranslateName = new ArrayList<String>();
        mParentTranslateName = new ArrayList<String>();
    }  // SQLGenerator
    
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
    
    public String getGeneratedString()
    {
       StringBuilder lJSONBuilder = new StringBuilder();
       
       lJSONBuilder.append("[");
       
       int lSize = mOID.size();
       
       for(int lIndex = 0; lIndex < lSize; lIndex++)
       {
           lJSONBuilder.append("{");
           lJSONBuilder.append("'name' : '" + mTranslateName.get(lIndex)  + "',");
           lJSONBuilder.append("'parent' : '" + mParentTranslateName.get(lIndex)  + "',");
           lJSONBuilder.append("'oid' : '" + mOID.get(lIndex) + "'");
           lJSONBuilder.append("}");
           if(lIndex < lSize)
           {
               lJSONBuilder.append(",");
           }  // if
       }  // for
       
       lJSONBuilder.append("]");
       return lJSONBuilder.toString(); 
   }  // public String getGenerateJSON
}
