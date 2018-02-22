package am.ik.servicebroker.cloudkarafka.cloudkarafka;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CloudKarafkaPlan {
	DUCKY;

	@JsonCreator
	public static CloudKarafkaPlan of(String value) {
		return Arrays.stream(values()).filter(v -> v.name().equalsIgnoreCase(value))
				.findAny().orElse(null);
	}

	@Override
	public String toString() {
		return name().toLowerCase();
	}
}