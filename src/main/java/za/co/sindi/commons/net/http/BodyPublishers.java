/**
 * 
 */
package za.co.sindi.commons.net.http;

import java.io.IOException;
import java.net.http.HttpRequest.BodyPublisher;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

import za.co.sindi.commons.utils.URLEncoderUtils;

/**
 * 
 */
public class BodyPublishers {

	/**
	 * 
	 */
	private BodyPublishers() {
		// TODO Auto-generated constructor stub
		throw new AssertionError("Private Constructor");
	}

	public static BodyPublisher ofURLEncodedFormData(final Map<String, Object> formData) {
		return java.net.http.HttpRequest.BodyPublishers.ofString(URLEncoderUtils.formatQueryParametersNatively(formData, '&', StandardCharsets.UTF_8));
	}
	
	public static BodyPublisher ofMultipartFormData(final Map<String, Object> data, final String boundary) throws IOException {
		var byteArrays = new ArrayList<byte[]>();
		byte[] separator = ("\r\n--" + boundary + "\r\nContent-Disposition: form-data; name=").getBytes(StandardCharsets.UTF_8);
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			byteArrays.add(separator);
		
			if (entry.getValue() instanceof Path) {
				var path = (Path) entry.getValue();
				String mimeType = Files.probeContentType(path);
				byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + path.getFileName() + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
				byteArrays.add(Files.readAllBytes(path));
			} else {
				byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue()).getBytes(StandardCharsets.UTF_8));
			}
		}

		byteArrays.add(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
		return java.net.http.HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
	}
}
