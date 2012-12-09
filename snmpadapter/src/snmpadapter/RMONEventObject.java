package snmpadapter;

public class RMONEventObject 
{
	private int mIndex;
	private String mTrap;
	private String mCommunity;
	private String mLastTimeSent;
	private String mDescription;
	private String mStatus;
	private String mOwner;
	
	public int getIndex() 
	{
		return mIndex;
	}

	public void setIndex(int pIndex) 
	{
		mIndex = pIndex;
	}

	public String getTrap() 
	{
		return mTrap;
	}

	public void setTrap(String pTrap) 
	{
		mTrap = pTrap;
	}

	public String getCommunity() 
	{
		return mCommunity;
	}

	public void setCommunity(String pCommunity) 
	{
		mCommunity = pCommunity;
	}

	public String getLastTimeSent() 
	{
		return mLastTimeSent;
	}

	public void setLastTimeSent(String pLastTimeSent) 
	{
		mLastTimeSent = pLastTimeSent;
	}

	public String getDescription() 
	{
		return mDescription;
	}

	public void setDescription(String mDescription) 
	{
		this.mDescription = mDescription;
	}

	public String getOwner() 
	{
		return mOwner;
	}

	public void setOwner(String pOwner) 
	{
		mOwner = pOwner;
	}
	
	public String getStatus() 
	{
		return mStatus;
	}

	public void setStatus(String pStatus) 
	{
		mStatus = pStatus;
	}
	
	public String toString()
	{
		StringBuilder lString = new StringBuilder();
		lString.append("{");
		lString.append("\"eventindex\":\"" + mIndex + "\",");
		lString.append("\"eventtrap\":\"" + mTrap + "\",");
		lString.append("\"eventcommunity\":\"" + mCommunity + "\",");
		lString.append("\"eventlasttimesent\":\"" + mLastTimeSent  + "\",");
		lString.append("\"eventdescription\":\"" + mDescription + "\",");
		lString.append("\"eventstatus\":\"" + mStatus + "\",");
		lString.append("\"eventowner\":\"" +  mOwner + "\"");		
		lString.append("}");

		return lString.toString();
	}  // String toString
}
