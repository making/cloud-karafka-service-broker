package am.ik.servicebroker.cloudkarafka.cloudkarafka;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Component
public class CloudKarafkaClient {
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;

	public CloudKarafkaClient(CloudKarafka cloudKarafka, RestTemplateBuilder builder,
			ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.restTemplate = builder.basicAuthentication(cloudKarafka.getApiKey(), "")
				.rootUri(cloudKarafka.getUri()).build();
	}

	public List<CloudKarafkaInstance> listInstances() {
		LinkedCaseInsensitiveMap[] instances = this.restTemplate
				.getForObject("/api/instances", LinkedCaseInsensitiveMap[].class);
		return instances == null ? emptyList()
				: Arrays.stream(instances).map(this::caseInsensitiveConvert)
						.collect(toList());
	}

	public CloudKarafkaInstance getInstance(Integer id) {
		LinkedCaseInsensitiveMap json = this.restTemplate
				.getForObject("/api/instances/{id}", LinkedCaseInsensitiveMap.class, id);
		return this.caseInsensitiveConvert(json);
	}

	public CloudKarafkaInstance createInstance(String name, CloudKarafkaPlan plan,
			CloudKarafkaRegion region) {
		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("name", name);
		body.add("plan", plan.toString());
		body.add("region", region.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		LinkedCaseInsensitiveMap json = this.restTemplate
				.exchange("/api/instances", HttpMethod.POST,
						new HttpEntity<>(body, headers), LinkedCaseInsensitiveMap.class)
				.getBody();
		return this.caseInsensitiveConvert(json);
	}

	public Optional<CloudKarafkaInstance> findByName(String name) {
		return this.listInstances().stream() //
				.map(i -> this.getInstance(i.getId())) //
				.filter(i -> Objects.equals(name, i.getName())) //
				.findAny();
	}

	public void deleteInstance(Integer id) {
		this.restTemplate.delete("/api/instances/{id}", id);
	}

	CloudKarafkaInstance caseInsensitiveConvert(LinkedCaseInsensitiveMap json) {
		// Some API return lower snake keys but some API return upper snake keys
		CloudKarafkaInstance instance = new CloudKarafkaInstance();
		instance.setId((Integer) json.get("id"));
		instance.setName((String) json.get("name"));
		instance.setPlan(CloudKarafkaPlan.of((String) json.get("plan")));
		instance.setRegion(CloudKarafkaRegion.of((String) json.get("region")));
		instance.setCa((String) json.get("ca"));
		instance.setBrokers((String) json.get("brokers"));
		instance.setPassword((String) json.get("password"));
		instance.setUsername((String) json.get("username"));
		instance.setTopicPrefix((String) json.get("topic_prefix"));
		return instance;
	}
}
