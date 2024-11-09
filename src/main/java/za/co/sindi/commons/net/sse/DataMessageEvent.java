package za.co.sindi.commons.net.sse;

/**
 * @author Buhake Sindi
 * @since 09 November 2024
 */
public class DataMessageEvent implements MessageEvent {

	private final String type;
	private final String data;
	private final String lastEventId;
	
	/**
	 * @param type
	 * @param data
	 * @param lastEventId
	 */
	public DataMessageEvent(String type, String data, String lastEventId) {
		super();
		this.type = type;
		this.data = data;
		this.lastEventId = lastEventId;
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
}
