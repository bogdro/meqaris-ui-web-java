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
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meq_config")
@Cacheable(false)
public class MeqConfig {

	@Id
	@Column(name = "c_name", unique = true, nullable = false, insertable = false, updatable = false)
	private String name;

	@Column(name = "c_value", insertable = false, updatable = false)
	private String value;

	@Column(name = "c_description", insertable = false, updatable = false)
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MeqConfig)) {
			return false;
		}
		MeqConfig other = (MeqConfig) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name)
				&& Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return "MeqConfig [name=" + name + ", value=" + value + ", description=" + description + "]";
	}

	public static MeqConfig buildFromMap(Map<String, Object> m) {
		MeqConfig c = new MeqConfig();
		if (m != null) {
			Object name = m.get("c_name");
			if (name != null) {
				c.setName(String.valueOf(name));
			}
			Object value = m.get("c_value");
			if (value != null) {
				c.setValue(String.valueOf(value));
			}
			Object desc = m.get("c_description");
			if (desc != null) {
				c.setDescription(String.valueOf(desc));
			}
		}
		return c;
	}
}
