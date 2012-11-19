package org.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextPane;

public class Backend extends Thread
{
    boolean mRunning;
    String mCommand;
    
    String mOID;
    String mCurrentOID;
    String mTranslateName;
    String mParentTranslateName;
    String mParentCurrentOID;
    String mNEXTOID;
    String mValueType;
    String mValue;
    String mReadCommunity;
    String mWriteCommunity;
    
    FileGenerator lFGenerator;
    JSONGenerator lJGenerator;
    
    private JTextPane mTxpSQL;
    private JTextPane mTxpJSON;
    private JLabel mLblStatus;
    
    public Backend(JTextPane pTxpSQL,JTextPane pTxpJSON, JLabel pLblStatus)
    {
        mRunning = true;
        lFGenerator = new FileGenerator();
        lJGenerator = new JSONGenerator();
        mTxpJSON = pTxpJSON;
        mTxpJSON = pTxpSQL;
        mLblStatus = pLblStatus;
        mCommand = "";
    }  // void 
    
    public void run()
    {
        while(mRunning)
        {
            try
            {
                if(mCommand != "")
                {
                    mLblStatus.setText("Processing....");
                    if(mCommand.equalsIgnoreCase("UpdateInsert"))
                    {
                        setUpdateInsert();
                        mTxpJSON.setText(lJGenerator.getGeneratedString());
                        //mTxpSQL.setText("Need to be implemented");
                    }  // if
                    else if(mCommand.equalsIgnoreCase("Remove"))
                    {
                        setRemove();
                        mTxpJSON.setText(lJGenerator.getGeneratedString());
                        //mTxpSQL.setText("Need to be implemented");
                    }  // if
                    else if(mCommand.equalsIgnoreCase("Save"))
                    {
                        save();
                    }  // if
                    else if(mCommand.equalsIgnoreCase("Open"))
                    {
                        open();
                        mTxpJSON.setText(lJGenerator.getGeneratedString());
                        //mTxpSQL.setText("Need to be implemented");
                    }  // if
                    mCommand = "";
                    mLblStatus.setText("Complete");
                }  // if
                Thread.sleep(500);
            }   // try
            catch (InterruptedException pException)
            {
                pException.printStackTrace();
                break;
            }  // catch
        }  // while
    }  // void run
    
    public void setRunning(boolean pRunning)
    {
        mRunning = pRunning;
    }  // void setRunning
    
    public void setUpdateInsert(
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
        mOID = pOID;
        mCurrentOID = pCurrentOID;
        mTranslateName = pTranslateName;
        mParentTranslateName = pParentTranslateName;
        mParentCurrentOID = pParentCurrentOID;
        mNEXTOID = pNEXTOID;
        mValueType = pValueType;
        mValue = pValue;
        mReadCommunity = pReadCommunity;
        mWriteCommunity = pWriteCommunity;
        
        mCommand = "UpdateInsert";
    }  // void setUpdateInsert
    
    private void setUpdateInsert()
    {
        if(mParentTranslateName.equalsIgnoreCase(""))
        {
            mParentTranslateName = "$EMPTY";
        }  // if
        
        if(mParentCurrentOID.equalsIgnoreCase(""))
        {
            mParentCurrentOID = "$EMPTY";
        }  // if
        
        if(mNEXTOID.equalsIgnoreCase(""))
        {
           mNEXTOID = "$EMPTY";
        }  // if
        
        if (mValueType.equalsIgnoreCase(""))
        {
            mValueType = "$EMPTY";
        }  // if
        
        if (mValue.equalsIgnoreCase(""))
        {
            mValue = "$EMPTY";
        }  // if
        
        if (mReadCommunity.equalsIgnoreCase(""))
        {
            mReadCommunity = "$EMPTY";
        }  // if
        
        if (mWriteCommunity.equalsIgnoreCase(""))
        {
            mWriteCommunity = "$EMPTY";
        }  // if
        
        lFGenerator.checkInsertUpdate(
                mOID,
                mCurrentOID,
                mTranslateName,
                mParentTranslateName,
                mParentCurrentOID,
                mNEXTOID,
                mValueType,
                mValue,
                mReadCommunity,
                mWriteCommunity
                );
        
        if(!mParentTranslateName.equalsIgnoreCase("$EMPTY"))
        {
            mParentTranslateName = mParentTranslateName + "\\\\(" + mParentCurrentOID + "\\\\)";
        }  // else
        
        lJGenerator.checkInsertUpdate(
                mOID,
                mTranslateName + "(" + mCurrentOID + ")",
                mParentTranslateName
                );
    }  // void UpdateInsert
    
    public void setRemove(String pOID)
    {
        mOID = pOID;
        mCommand = "Remove";
    }   // void setRemove
    
    private void setRemove()
    {
        lFGenerator.remove(mOID);
        lJGenerator.remove(mOID);
    }   // void setRemove
    
    public void setOpen()
    {
        mCommand = "Open";
    }  // void setOpen
    
    private void open()
    {
        try
        {
            BufferedReader lReader = new BufferedReader(new FileReader("Output_Original_Format.txt"));
            String lLine;
            
            lLine = lReader.readLine();
            
            while(lLine != null)
            {
                String [] lResult = lLine.split(":");
                lFGenerator.insert(
                        lResult[0].trim(),
                        lResult[1].trim(),
                        lResult[2].trim(),
                        lResult[3].trim(),
                        lResult[4].trim(),
                        lResult[5].trim(),
                        lResult[6].trim(),
                        lResult[7].trim(),
                        lResult[8].trim(),
                        lResult[9].trim()
                        );
                
                if(!lResult[3].trim().equalsIgnoreCase("$EMPTY"))
                {
                    lResult[3] = lResult[3].trim() + "\\\\(" + lResult[4].trim() + "\\\\)";
                }  // else
                
                lJGenerator.insert(
                        lResult[0].trim(),
                        lResult[2].trim() + "(" + lResult[1].trim() + ")",
                        lResult[3].trim()
                        );
                
                lLine = lReader.readLine();
            }  // while
            
            lReader.close();
        } 
        catch (FileNotFoundException pException)
        {
            pException.printStackTrace();
        }  // catch
        catch (IOException pException)
        {
            pException.printStackTrace();
        }  // catch
    }  // void open
    
    public void setSave()
    {
        mCommand = "Save";
    }  // void setSave
    
    private void save()
    {
        try
        {
            BufferedWriter lWriter = new BufferedWriter(new FileWriter("Output_Original_Format.txt"));
            lWriter.write(lFGenerator.getGeneratedString());
            lWriter.close();
            
            lWriter =  new BufferedWriter(new FileWriter("Output_JSON_Format.txt"));
            lWriter.write(lJGenerator.getGeneratedString());
            lWriter.close();
        } 
        catch (FileNotFoundException pException)
        {
            pException.printStackTrace();
        }  // catch
        catch (IOException pException)
        {
            pException.printStackTrace();
        }  // catch
    }  // void remove
}
