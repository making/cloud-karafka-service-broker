package am.ik.servicebroker.cloudkarafka.cloudkarafka;

import java.util.Base64;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "cloudkarafka")
@Component
public class CloudKarafka {
	private String uri = "https://customer.cloudkarafka.com";
	private String apiKey = "";

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String authorization() {
		return "Basic "
				+ Base64.getEncoder().encodeToString((this.apiKey + ":").getBytes());
	}
}
