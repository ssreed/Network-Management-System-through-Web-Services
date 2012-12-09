package snmpadapter;

public class RMONAlarmObject 
{
	private int mIndex;
	private String mVariable;
	private int mInverval;
	private String mIntervalOption;
	private int mRisingThreshold;
	private int mRisingEventIndex;
	private int mFallingThreshold;
	private int mFallingEventIndex;
	private String mCommunity;
	private String mStatus;
	private String mOwner;
	
	
	public int getIndex() 
	{
		return mIndex;
	}

	public void setIndex(int mIndex) 
	{
		this.mIndex = mIndex;
	}

	public String getVariable() 
	{
		return mVariable;
	}

	public void setmVariable(String pVariable) 
	{
		mVariable = pVariable;
	}

	public int getInverval() 
	{
		return mInverval;
	}

	public void setInverval(int pInverval) 
	{
		mInverval = pInverval;
	}

	public String getIntervalOption() 
	{
		return mIntervalOption;
	}

	public void setIntervalOption(String pIntervalOption) 
	{
		mIntervalOption = pIntervalOption;
	}

	public int getRisingThreshold() {
		return mRisingThreshold;
	}

	public void setRisingThreshold(int pRisingThreshold) 
	{
		mRisingThreshold = pRisingThreshold;
	}

	public int getRisingEventIndex() {
		return mRisingEventIndex;
	}

	public void setRisingEventIndex(int pRisingEventIndex) 
	{
		mRisingEventIndex = pRisingEventIndex;
	}

	public int getFallingThreshold() 
	{
		return mFallingThreshold;
	}

	public void setFallingThreshold(int pFallingThreshold) {
		mFallingThreshold = pFallingThreshold;
	}

	public int getFallingEventIndex() {
		return mFallingEventIndex;
	}

	public void setFallingEventIndex(int pFallingEventIndex) 
	{
		mFallingEventIndex = pFallingEventIndex;
	}

	public String getOwner() 
	{
		return mOwner;
	}

	public void setOwner(String pOwner) 
	{
		mOwner = pOwner;
	}

	public void setStatus(String pStatus)
	{
		mStatus = pStatus;
	} 
	
	public String getStatus()
	{
		return mStatus;
	} 
	
	public void setCommunity(String pCommunity)
	{
		mCommunity = pCommunity;
	} 
	
	public String getCommunity()
	{
		return mCommunity;
	} 
	
	public String toString()
	{
		StringBuilder lString = new StringBuilder();
		lString.append("{");
		lString.append("\"alarmindex\":\"" + mIndex + "\",");
		lString.append("\"alarmvariable\":\"" + mVariable + "\",");
		lString.append("\"alarminverval\":\"" + mInverval + "\",");
		lString.append("\"alarmintervaloption\":\"" + mIntervalOption + "\",");
		lString.append("\"alarmrisingthreshold\":\"" + mRisingThreshold + "\",");
		lString.append("\"alarmrisingeventindex\":\"" + mRisingEventIndex + "\",");
		lString.append("\"alarmfallingthreshold\":\"" + mFallingThreshold + "\",");
		lString.append("\"alarmfallingeventindex\":\"" + mFallingEventIndex + "\",");
		lString.append("\"alarmstatus\":\"" + mStatus + "\",");
		lString.append("\"alarmowner\":\"" + mOwner + "\"");
		lString.append("}");

		return lString.toString();
	}  // String toString
}
