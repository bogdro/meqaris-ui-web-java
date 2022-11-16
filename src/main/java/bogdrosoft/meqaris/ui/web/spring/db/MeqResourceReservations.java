package bogdrosoft.meqaris.ui.web.spring.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private Object interval; // FIXME

	@Column(name = "rr_organiser", nullable = false, insertable = false, updatable = false)
	private String organiser;

	@Column(name = "rr_summary", insertable = false, updatable = false)
	private String summary;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "rr_dtstamp", insertable = false, updatable = false)
	private Calendar dateTimestamp;

	@Column(name = "rr_uid", nullable = false, insertable = false, updatable = false)
	private String uid;

	@Column(name = "rr_seq", nullable = false, insertable = false, updatable = false)
	private Integer seq;

	@Column(name = "rr_data", insertable = false, updatable = false)
	private String data;

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

	public String getOrganiser() {
		return organiser;
	}

	public void setOrganiser(String organiser) {
		this.organiser = organiser;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Calendar getDateTimestamp() {
		return dateTimestamp;
	}

	public void setDateTimestamp(Calendar dateTimestamp) {
		this.dateTimestamp = dateTimestamp;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, dateTimestamp, id, interval, organiser, resourceId, seq, summary, uid);
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
		return Objects.equals(data, other.data) && Objects.equals(dateTimestamp, other.dateTimestamp)
				&& Objects.equals(id, other.id) && Objects.equals(interval, other.interval)
				&& Objects.equals(organiser, other.organiser) && Objects.equals(resourceId, other.resourceId)
				&& Objects.equals(seq, other.seq) && Objects.equals(summary, other.summary)
				&& Objects.equals(uid, other.uid);
	}

	@Override
	public String toString() {
		return "MeqResourceReservations [id=" + id + ", resourceId=" + resourceId + ", interval=" + interval
				+ ", organiser=" + organiser + ", summary=" + summary + ", dateTimestamp=" + dateTimestamp + ", uid="
				+ uid + ", seq=" + seq + ", data=" + data + "]";
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
			Object organiser = m.get("rr_organiser");
			if (organiser != null) {
				rr.setOrganiser(String.valueOf(organiser));
			}
			Object summary = m.get("rr_summary");
			if (summary != null) {
				rr.setSummary(String.valueOf(summary));
			}
			Object dtstamp = m.get("rr_dtstamp");
			if (dtstamp != null) {
				// e.g. 20220428T220223Z
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'X");
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(0);
				try {
					c.setTime(sdf.parse(String.valueOf(dtstamp)));
				} catch (ParseException e) {
				}
				rr.setDateTimestamp(c);
			}
			Object uid = m.get("rr_uid");
			if (uid != null) {
				rr.setUid(String.valueOf(uid));
			}
			Object seq = m.get("rr_seq");
			if (seq != null) {
				rr.setSeq(Integer.valueOf(String.valueOf(seq)));
			}
			Object data = m.get("rr_data");
			if (data != null) {
				rr.setData(String.valueOf(data));
			}
		}
		return rr;
	}
}
