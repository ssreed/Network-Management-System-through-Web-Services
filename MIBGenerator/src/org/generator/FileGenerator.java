package org.generator;

import java.util.ArrayList;

public class FileGenerator
{
    ArrayList<String> mOID;
    ArrayList<String> mCurrentOID;
    ArrayList<String> mTranslateName;
    ArrayList<String> mParentTranslateName;
    ArrayList<String> mParentCurrentOID;
    ArrayList<String> mNEXTOID;
    ArrayList<String> mValueType;
    ArrayList<String> mValue;
    ArrayList<String> mReadCommunity;
    ArrayList<String> mWriteCommunity;
    
    public FileGenerator()
    {
        mOID = new ArrayList<String>();
        mCurrentOID = new ArrayList<String>();
        mTranslateName = new ArrayList<String>();
        mParentTranslateName = new ArrayList<String>();
        mParentCurrentOID = new ArrayList<String>();
        mNEXTOID = new ArrayList<String>();
        mValueType = new ArrayList<String>();
        mValue = new ArrayList<String>();
        mReadCommunity = new ArrayList<String>();
        mWriteCommunity = new ArrayList<String>();
    }  // Generator
    
    public void clear()
    {
        mOID.clear();
        mCurrentOID.clear();
        mTranslateName.clear();
        mParentTranslateName.clear();
        mParentCurrentOID.clear();
        mNEXTOID.clear();
        mValueType.clear();
        mValue.clear();
        mReadCommunity.clear();
        mWriteCommunity.clear();
    }  // clear
    
    public void checkInsertUpdate(
            String pOID,
            String pCurrentOID,
            String pTranslateName,
            String pParentTranslateName,
            String pParentCurrentOID,
            String pNEXTOID,
            String pValueType,
            String pValue,
            String pReadCommunity,
            String pWriteCommunity
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
                    pCurrentOID,
                    pTranslateName,
                    pParentTranslateName,
                    pParentCurrentOID,
                    pNEXTOID,
                    pValueType,
                    pValue,
                    pReadCommunity,
                    pWriteCommunity
                    );
        }  // if
        else
        {
            update(
                    lIndex,
                    pCurrentOID,
                    pTranslateName,
                    pParentTranslateName,
                    pParentCurrentOID,
                    pNEXTOID,
                    pValueType,
                    pValue,
                    pReadCommunity,
                    pWriteCommunity
                    );
        }  // else
    }  // void checkInsertUpdate
    
    public void insert(
            String pOID,
            String pCurrentOID,
            String pTranslateName,
            String pParentTranslateName,
            String pParentCurrentOID,
            String pNEXTOID,
            String pValueType,
            String pValue,
            String pReadCommunity,
            String pWriteCommunity
            )
    {
        mOID.add(pOID);
        mCurrentOID.add(pCurrentOID);
        mTranslateName.add(pTranslateName);
        mParentTranslateName.add(pParentTranslateName);
        mParentCurrentOID.add(pParentCurrentOID);
        mNEXTOID.add(pNEXTOID);
        mValueType.add(pValueType);
        mValue.add(pValue);
        mReadCommunity.add(pReadCommunity);
        mWriteCommunity.add(pWriteCommunity);
    }  // insert
    
    void update(int pIndex,
            String pCurrentOID,
            String pTranslateName,
            String pParentTranslateName,
            String pParentCurrentOID,
            String pNEXTOID,
            String pValueType,
            String pValue,
            String pReadCommunity,
            String pWriteCommunity
            )
    {
        mCurrentOID.set(pIndex, pCurrentOID);
        mTranslateName.set(pIndex, pTranslateName);
        mParentTranslateName.set(pIndex, pParentTranslateName);
        mParentCurrentOID.set(pIndex, pParentCurrentOID);
        mNEXTOID.set(pIndex, pNEXTOID);
        mValueType.set(pIndex, pValueType);
        mValue.set(pIndex, pValue);
        mReadCommunity.set(pIndex, pReadCommunity);
        mWriteCommunity.set(pIndex, pWriteCommunity);
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
        mCurrentOID.remove(lIndex);
        mTranslateName.remove(lIndex);
        mParentTranslateName.remove(lIndex);
        mParentCurrentOID.remove(lIndex);
        mNEXTOID.remove(lIndex);
        mValueType.remove(lIndex);
        mValue.remove(lIndex);
        mReadCommunity.remove(lIndex);
        mWriteCommunity.remove(lIndex);
    }  // void remove
    
    public String getGeneratedString()
    {
       StringBuilder lFileBuilder = new StringBuilder();
       
       int lSize = mOID.size();
       
       for(int lIndex = 0; lIndex < lSize; lIndex++)
       {
           lFileBuilder.append(mOID.get(lIndex));
           lFileBuilder.append(" : ");
           lFileBuilder.append(mCurrentOID.get(lIndex));
           lFileBuilder.append(" : ");
           lFileBuilder.append(mTranslateName.get(lIndex));
           lFileBuilder.append(" : ");
           lFileBuilder.append(mParentTranslateName.get(lIndex));
           lFileBuilder.append(" : ");
           lFileBuilder.append(mParentCurrentOID.get(lIndex));
           lFileBuilder.append(" : ");
           lFileBuilder.append(mNEXTOID.get(lIndex));
           lFileBuilder.append(" : ");
           lFileBuilder.append(mValueType.get(lIndex));
           lFileBuilder.append(" : ");
           lFileBuilder.append(mValue.get(lIndex));
           lFileBuilder.append(" : ");
           lFileBuilder.append(mReadCommunity.get(lIndex));
           lFileBuilder.append(" : ");
           lFileBuilder.append(mWriteCommunity.get(lIndex));
           lFileBuilder.append('\n');
       }  // for
       return lFileBuilder.toString(); 
   }  // public String getGeneratedString
}  // FileGenerator
