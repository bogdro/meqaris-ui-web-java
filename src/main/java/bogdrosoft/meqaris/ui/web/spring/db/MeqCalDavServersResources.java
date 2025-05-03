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
import javax.persistence.Table;

/**
 * The entity class for the "meq_caldav_servers_resources" table.
 * @author Bogdan Drozdowski
 */
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
