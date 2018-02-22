package am.ik.servicebroker.cloudkarafka.cloudkarafka;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CloudKarafkaInstance implements Serializable {
	private Integer id;
	private String name;
	private CloudKarafkaPlan plan;
	private CloudKarafkaRegion region;
	private String ca;
	private String brokers;
	private String username;
	private String password;
	private String topicPrefix;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CloudKarafkaPlan getPlan() {
		return plan;
	}

	public void setPlan(CloudKarafkaPlan plan) {
		this.plan = plan;
	}

	public CloudKarafkaRegion getRegion() {
		return region;
	}

	public void setRegion(CloudKarafkaRegion region) {
		this.region = region;
	}

	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}

	public String getBrokers() {
		return brokers;
	}

	public void setBrokers(String brokers) {
		this.brokers = brokers;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTopicPrefix() {
		return topicPrefix;
	}

	public void setTopicPrefix(String topicPrefix) {
		this.topicPrefix = topicPrefix;
	}

	@Override
	public String toString() {
		return "CloudKarafkaInstance{" + "id=" + id + ", name='" + name + '\'' + ", plan="
				+ plan + ", region=" + region + ", ca='" + ca + '\'' + ", brokers='"
				+ brokers + '\'' + ", username='" + username + '\'' + ", password='"
				+ password + '\'' + ", topicPrefix='" + topicPrefix + '\'' + '}';
	}

	public Credential credential() {
		return new Credential(name, brokers, username, password, topicPrefix, ca);
	}

	public static class Credential {
		private final String name;
		private final String brokers;
		private final String username;
		private final String password;
		private final String topicPrefix;
		private final String ca;

		public Credential(String name, String brokers, String username, String password,
				String topicPrefix, String ca) {
			this.name = name;
			this.brokers = brokers;
			this.username = username;
			this.password = password;
			this.topicPrefix = topicPrefix;
			this.ca = ca;
		}

		public String getName() {
			return name;
		}

		public String getBrokers() {
			return brokers;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public String getTopicPrefix() {
			return topicPrefix;
		}

		public String getCa() {
			return ca;
		}
	}
}
