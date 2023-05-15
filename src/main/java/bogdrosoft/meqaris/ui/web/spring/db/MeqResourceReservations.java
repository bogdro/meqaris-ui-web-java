package bogdrosoft.meqaris.ui.web.spring.db;

import java.util.Map;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meq_resource_reservations")
@Cacheable(false)
public class MeqResourceReservations {

	@Id
	@GeneratedValue
	@Column(name = "rr_id", nullable = false, unique = true, insertable = false, updatable = false)
	private Long id;

	@Column(name = "rr_r_id", nullable = false, insertable = false, updatable = false)
	private Long resourceId;

	@Column(name = "rr_interval", nullable = false, insertable = false, updatable = false)
	private Object interval;

	@Column(name = "rr_e_id", nullable = false, insertable = false, updatable = false)
	private Long eventId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Object getInterval() {
		return interval;
	}

	public void setInterval(Object interval) {
		this.interval = interval;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventId, id, interval, resourceId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MeqResourceReservations)) {
			return false;
		}
		MeqResourceReservations other = (MeqResourceReservations) obj;
		return Objects.equals(eventId, other.eventId) && Objects.equals(id, other.id)
				&& Objects.equals(interval, other.interval) && Objects.equals(resourceId, other.resourceId);
	}

	@Override
	public String toString() {
		return "MeqResourceReservations [id=" + id + ", resourceId=" + resourceId + ", interval=" + interval
				+ ", eventId=" + eventId + "]";
	}

	public static MeqResourceReservations buildFromMap(Map<String, Object> m) {
		MeqResourceReservations rr = new MeqResourceReservations();
		if (m != null) {
			Object id = m.get("rr_id");
			if (id != null) {
				rr.setId(Long.valueOf(String.valueOf(id)));
			}
			Object resId = m.get("rr_r_id");
			if (resId != null) {
				rr.setResourceId(Long.valueOf(String.valueOf(resId)));
			}
			Object interval = m.get("rr_interval");
			if (interval != null) {
				rr.setInterval(interval);
			}
			Object evId = m.get("rr_e_id");
			if (evId != null) {
				rr.setEventId(Long.valueOf(String.valueOf(evId)));
			}
		}
		return rr;
	}
}
