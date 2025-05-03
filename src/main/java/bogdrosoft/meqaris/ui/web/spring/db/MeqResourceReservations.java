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

import java.util.Map;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The entity class for the "meq_resource_reservations" table.
 * @author Bogdan Drozdowski
 */
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
