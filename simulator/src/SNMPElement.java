
public class SNMPElement
{
    private String mName;
    private long mUPTime;
    private String mCurrentStatus;
    
    public SNMPElement()
    {
        mName = "ND1";
        mUPTime = 0;
    }  // Constructor
    
    public String getName()
    {
       return mName;
    }  // getName()
    
    public long getUPTime()
    {
        mUPTime = mUPTime + (System.currentTimeMillis() - mUPTime);
        return mUPTime;
    }  // getUPTime
    
    public String getStatus()
    {
        return mCurrentStatus;
    }  // getUPTime
    
    public void setStatus(String pStatus)
    {
        mCurrentStatus = pStatus;
    }  // void setStatus(String pStatus)
}
