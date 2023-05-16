package bogdrosoft.meqaris.ui.web.spring.db;

import java.util.Map;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "meq_caldav_servers_resources")
@Cacheable(false)
public class MeqCalDavServersResources {

	@Column(name = "calres_cals_id", nullable = false, insertable = false, updatable = false)
	private Long serverId;

	@Column(name = "calres_r_id", nullable = false, insertable = false, updatable = false)
	private Long resourceId;

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(resourceId, serverId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MeqCalDavServersResources)) {
			return false;
		}
		MeqCalDavServersResources other = (MeqCalDavServersResources) obj;
		return Objects.equals(resourceId, other.resourceId) && Objects.equals(serverId, other.serverId);
	}

	@Override
	public String toString() {
		return "MeqCalDavServersResources [serverId=" + serverId + ", resourceId=" + resourceId + "]";
	}

	public static MeqCalDavServersResources buildFromMap(Map<String, Object> m) {
		MeqCalDavServersResources serverResMap = new MeqCalDavServersResources();
		if (m != null) {
			Object calsId = m.get("calres_cals_id");
			if (calsId != null) {
				serverResMap.setServerId(Long.valueOf(String.valueOf(calsId)));
			}
			Object resId = m.get("calres_r_id");
			if (resId != null) {
				serverResMap.setResourceId(Long.valueOf(String.valueOf(resId)));
			}
		}
		return serverResMap;
	}
}
