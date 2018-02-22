package am.ik.servicebroker.cloudkarafka.servicebroker;

import java.util.HashMap;
import java.util.Map;

import am.ik.servicebroker.cloudkarafka.cloudkarafka.CloudKarafkaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/service_instances/{instanceId}/service_bindings/{bindingId}")
public class ServiceInstanceBindingController {
	private static final Logger log = LoggerFactory
			.getLogger(ServiceInstanceBindingController.class);

	private final CloudKarafkaClient cloudKarafkaClient;

	public ServiceInstanceBindingController(CloudKarafkaClient cloudKarafkaClient) {
		this.cloudKarafkaClient = cloudKarafkaClient;
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> bind(
			@PathVariable("instanceId") String instanceId,
			@PathVariable("bindingId") String bindingId) {
		log.info("bind instanceId={}, bindingId={}", instanceId, bindingId);
		String name = "cf_" + instanceId;

		Map<String, Object> body = new HashMap<>();
		this.cloudKarafkaClient.findByName(name)
				.ifPresent(i -> body.put("credentials", i.credential()));
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}

	@DeleteMapping
	public ResponseEntity<Map<String, Object>> unbind(
			@PathVariable("instanceId") String instanceId,
			@PathVariable("bindingId") String bindingId) {
		log.info("unbind instanceId={}, bindingId={}", instanceId, bindingId);
		Map<String, Object> body = new HashMap<>();
		return ResponseEntity.ok(body);
	}
}
