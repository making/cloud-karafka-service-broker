package am.ik.servicebroker.cloudkarafka.cloudkarafka;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CloudKarafkaRegion {
	GOOGLE_COMPUTE_ENGINE_US_CENTRAL1("google-compute-engine::us-central1"), //
	AMAZON_WEB_SERVICES_US_EAST_1("amazon-web-services::us-east-1"), //
	AMAZON_WEB_SERVICES_EU_WEST_1("amazon-web-services::eu-west-1");

	private final String value;

	CloudKarafkaRegion(String value) {
		this.value = value;
	}

	@JsonCreator
	public static CloudKarafkaRegion of(String value) {
		return Arrays.stream(values()).filter(v -> v.value.equals(value)).findAny()
				.orElse(null);
	}

	@Override
	public String toString() {
		return this.value;
	}
}