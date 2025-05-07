package za.co.sindi.commons.net.sse;

/**
 * @author Buhake Sindi
 * @since 09 November 2024
 */
public class DataMessageEvent implements MessageEvent {

	private final String type;
	private final String data;
	private final String lastEventId;
	private final Integer reconnectionTime;
	
	/**
	 * @param type
	 * @param data
	 * @param lastEventId
	 * @param reconnectionTime
	 */
	public DataMessageEvent(String type, String data, String lastEventId, Integer reconnectionTime) {
		super();
		this.type = type;
		this.data = data;
		this.lastEventId = lastEventId;
		this.reconnectionTime = reconnectionTime;
	}

	/* (non-Javadoc)
	 * @see za.co.sindi.commons.net.sse.MessageEvent#getType()
	 */
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	/* (non-Javadoc)
	 * @see za.co.sindi.commons.net.http.MessageEvent#getData()
	 */
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return data;
	}

	/* (non-Javadoc)
	 * @see za.co.sindi.commons.net.http.MessageEvent#getLastEventId()
	 */
	@Override
	public String getLastEventId() {
		// TODO Auto-generated method stub
		return lastEventId;
	}

	@Override
	public Integer getReconnectionTime() {
		// TODO Auto-generated method stub
		return reconnectionTime;
	}
}
