/**
 * 
 * @author Kai - Ting (Danil) Ko
 *
 * Save view information as UDP packet
 */

package snmpadapter;

import java.io.Serializable;

public class SNMPCommandObject implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mCommunity; 
	private String mOption; 
	private String mViewName;
	private String mOID;
	private String mCommand;
	
	private RMONAlarmObject mAlarmObject;
	private RMONEventObject mEventObject;
	
	private int mQueueSize;
	
	public void setCommand(String pCommand)
	{
		mCommand = pCommand;
	} 
	
	public String getCommand()
	{
		return mCommand;
	} 
	
	public String getCommunity() 
	{
		return mCommunity;
	}
	public void setCommunity(String pCommunity) 
	{
		mCommunity = pCommunity;
	}
	public String getOption() 
	{
		return mOption;
	}
	public void setOption(String pOption) 
	{
		mOption = pOption;
	}
	public String getViewName() 
	{
		return mViewName;
	}
	public void setViewName(String pViewName) 
	{
		mViewName = pViewName;
	}
	public String getOID() 
	{
		return mOID;
	}
	public void setOID(String pOID) 
	{
		mOID = pOID;
	}
	public RMONEventObject getEventObject() 
	{
		return mEventObject;
	}
	public void setEventObject(RMONEventObject pObject) 
	{
		mEventObject = pObject;
	}
	public RMONAlarmObject getAlarmObject() 
	{
		return mAlarmObject;
	}
	public void setAlarmObject(RMONAlarmObject pObject) 
	{
		mAlarmObject = pObject;
	}
	public int getQueueSize() 
	{
		return mQueueSize;
	}
	public void setQueueSize(int pQueueSize) 
	{
		mQueueSize = pQueueSize;
	}
}  // class SNMPViewObject
