package za.co.sindi.commons.net.sse;

/**
 * @author Buhake Sindi
 * @since 09 November 2024
 */
public class CommentEvent implements Event {

	private final String comment;

	/**
	 * @param comment
	 */
	public CommentEvent(String comment) {
		super();
		this.comment = comment;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
}
