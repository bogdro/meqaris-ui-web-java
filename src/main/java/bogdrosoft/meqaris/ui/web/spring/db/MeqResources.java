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
 * The entity class for the "meq_resources" table.
 * @author Bogdan Drozdowski
 */
@Entity
@Table(name = "meq_resources")
@Cacheable(false)
public class MeqResources {

	@Id
	@GeneratedValue
	@Column(name = "r_id", nullable = false, unique = true, insertable = false, updatable = false)
	private Long id;

	@Column(name = "r_name", nullable = false, insertable = false, updatable = false)
	private String name;

	@Column(name = "r_email", nullable = false, unique = true, insertable = false, updatable = false)
	private String email;

	@Column(name = "r_description", insertable = false, updatable = false)
	private String description;

	@Column(name = "r_enabled", nullable = false, insertable = false, updatable = false)
	private Boolean enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, email, enabled, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MeqResources)) {
			return false;
		}
		MeqResources other = (MeqResources) obj;
		return Objects.equals(description, other.description) && Objects.equals(email, other.email)
				&& Objects.equals(enabled, other.enabled) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "MeqResources [id=" + id + ", name=" + name + ", email=" + email + ", description=" + description
				+ ", enabled=" + enabled + "]";
	}

	public static MeqResources buildFromMap(Map<String, Object> m) {
		MeqResources r = new MeqResources();
		if (m != null) {
			Object id = m.get("r_id");
			if (id != null) {
				r.setId(Long.valueOf(String.valueOf(id)));
			}
			Object name = m.get("r_name");
			if (name != null) {
				r.setName(String.valueOf(name));
			}
			Object email = m.get("r_email");
			if (email != null) {
				r.setEmail(String.valueOf(email));
			}
			Object desc = m.get("r_description");
			if (desc != null) {
				r.setDescription(String.valueOf(desc));
			}
			Object enabled = m.get("r_enabled");
			if (enabled != null) {
				r.setEnabled(Boolean.valueOf(String.valueOf(enabled)));
			}
		}
		return r;
	}
}
