package am.ik.servicebroker.cloudkarafka.servicebroker;

import java.util.HashMap;
import java.util.Map;

import am.ik.servicebroker.cloudkarafka.cloudkarafka.CloudKarafkaClient;
import am.ik.servicebroker.cloudkarafka.cloudkarafka.CloudKarafkaInstance;
import am.ik.servicebroker.cloudkarafka.cloudkarafka.CloudKarafkaPlan;
import am.ik.servicebroker.cloudkarafka.cloudkarafka.CloudKarafkaRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static am.ik.servicebroker.cloudkarafka.cloudkarafka.CloudKarafkaPlan.DUCKY;
import static am.ik.servicebroker.cloudkarafka.cloudkarafka.CloudKarafkaRegion.GOOGLE_COMPUTE_ENGINE_US_CENTRAL1;

@RestController
@RequestMapping("/v2/service_instances/{instanceId}")
public class ServiceInstanceController {
	private static final Logger log = LoggerFactory
			.getLogger(ServiceInstanceController.class);
	private final CloudKarafkaClient cloudKarafkaClient;

	public ServiceInstanceController(CloudKarafkaClient cloudKarafkaClient) {
		this.cloudKarafkaClient = cloudKarafkaClient;
	}

	@PutMapping
	public ResponseEntity<Map<String, Object>> provisioning(
			@PathVariable("instanceId") String instanceId) {
		log.info("Provisioning instanceId={}", instanceId);

		String name = "cf_" + instanceId;
		CloudKarafkaPlan plan = DUCKY;
		CloudKarafkaRegion region = GOOGLE_COMPUTE_ENGINE_US_CENTRAL1;
		CloudKarafkaInstance instance = this.cloudKarafkaClient.createInstance(name, plan,
				region);

		Map<String, Object> body = new HashMap<>();
		body.put("dashboard_url", String.format(
				"https://customer.cloudkarafka.com/instance/%d/sso", instance.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}

	@PatchMapping
	public ResponseEntity<Map<String, Object>> update(
			@PathVariable("instanceId") String instanceId) {
		Map<String, Object> body = new HashMap<>();
		return ResponseEntity.ok(body);
	}

	@DeleteMapping
	public ResponseEntity<?> deprovisioning(
			@PathVariable("instanceId") String instanceId) {
		log.info("Deprovisioning instanceId={}", instanceId);
		Map<String, Object> body = new HashMap<>();

		String name = "cf_" + instanceId;

		return this.cloudKarafkaClient.findByName(name) //
				.map(i -> {
					this.cloudKarafkaClient.deleteInstance(i.getId());
					return ResponseEntity.ok(body);
				}) //
				.orElseGet(() -> ResponseEntity.status(HttpStatus.GONE).body(body));
	}
}
