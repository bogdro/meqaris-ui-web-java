/*
 * Copyright (C) 2022-2025 Bogdan 'bogdro' Drozdowski, bogdro (at) users . sourceforge . net
 *
 * This file is part of Meqaris (Meeting Equipment and Room Invitation System),
 *  software that allows booking meeting rooms and other resources using
 *  e-mail invitations.
 * Meqaris homepage: https://meqaris.sourceforge.io/
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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

/**
 * The entity class for the "meq_events" table.
 * @author Bogdan Drozdowski
 */
@Entity
@Table(name = "meq_events")
@Cacheable(false)
public class MeqEvents {

	@Id
	@GeneratedValue
	@Column(name = "e_id", nullable = false, unique = true, insertable = false, updatable = false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "e_entry_date", nullable = false, insertable = false, updatable = false)
	private Calendar entryDate;

	@Column(name = "e_organiser", nullable = false, insertable = false, updatable = false)
	private String organiser;

	@Column(name = "e_summary", insertable = false, updatable = false)
	private String summary;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "e_dtstamp", insertable = false, updatable = false)
	private Calendar dateTimestamp;

	@Column(name = "e_uid", nullable = false, insertable = false, updatable = false)
	private String uid;

	@Column(name = "e_seq", nullable = false, insertable = false, updatable = false)
	private Integer seq;

	@Column(name = "e_data", insertable = false, updatable = false)
	private String data;

	private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {

		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		}
	};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Calendar entryDate) {
		this.entryDate = entryDate;
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
		return Objects.hash(data, dateTimestamp, entryDate, id, organiser, seq, summary, uid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MeqEvents)) {
			return false;
		}
		MeqEvents other = (MeqEvents) obj;
		return Objects.equals(data, other.data) && Objects.equals(dateTimestamp, other.dateTimestamp)
				&& Objects.equals(entryDate, other.entryDate) && Objects.equals(id, other.id)
				&& Objects.equals(organiser, other.organiser) && Objects.equals(seq, other.seq)
				&& Objects.equals(summary, other.summary) && Objects.equals(uid, other.uid);
	}

	@Override
	public String toString() {
		return "MeqEvents [id=" + id + ", entryDate=" + entryDate + ", organiser=" + organiser + ", summary=" + summary
				+ ", dateTimestamp=" + dateTimestamp + ", uid=" + uid + ", seq=" + seq + ", data=" + data + "]";
	}

	public static MeqEvents buildFromMap(Map<String, Object> m) {
		MeqEvents ev = new MeqEvents();
		if (m == null) {
			return ev;
		}
		Object id = m.get("e_id");
		if (id != null) {
			ev.setId(Long.valueOf(String.valueOf(id)));
		}
		Object entryDate = m.get("e_entry_date");
		if (entryDate != null) {
			ev.setEntryDate(parseDate(entryDate));
		}
		Object organiser = m.get("e_organiser");
		if (organiser != null) {
			ev.setOrganiser(String.valueOf(organiser));
		}
		Object summary = m.get("e_summary");
		if (summary != null) {
			ev.setSummary(String.valueOf(summary));
		}
		Object dtstamp = m.get("e_dtstamp");
		if (dtstamp != null) {
			ev.setDateTimestamp(parseDate(dtstamp));
		}
		Object uid = m.get("e_uid");
		if (uid != null) {
			ev.setUid(String.valueOf(uid));
		}
		Object seq = m.get("e_seq");
		if (seq != null) {
			ev.setSeq(Integer.valueOf(String.valueOf(seq)));
		}
		Object data = m.get("e_data");
		if (data != null) {
			ev.setData(String.valueOf(data));
		}
		return ev;
	}

	private static Calendar parseDate(Object date) {

		Calendar c = Calendar.getInstance();
		try {
			c.setTime(DATE_FORMAT.get().parse(String.valueOf(date)));
		} catch (ParseException e) {
			c.setTimeInMillis(0);
		}
		return c;
	}
}
