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

@Entity
@Table(name = "meq_caldav_servers")
@Cacheable(false)
public class MeqCalDavServers {

	@Id
	@GeneratedValue
	@Column(name = "cals_id", nullable = false, unique = true, insertable = false, updatable = false)
	private Long id;

	@Column(name = "cals_name", nullable = false, insertable = false, updatable = false)
	private String name;

	@Column(name = "cals_url", nullable = false, unique = true, insertable = false, updatable = false)
	private String url;

	@Column(name = "cals_username", insertable = false, updatable = false)
	private String username;

	@Column(name = "cals_password", insertable = false, updatable = false)
	private String password;

	@Column(name = "cals_realm", insertable = false, updatable = false)
	private String realm;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, password, realm, url, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MeqCalDavServers)) {
			return false;
		}
		MeqCalDavServers other = (MeqCalDavServers) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(realm, other.realm)
				&& Objects.equals(url, other.url) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "MeqCalDavServers [id=" + id + ", name=" + name + ", url=" + url + ", username=" + username
				+ ", password=" + password + ", realm=" + realm + "]";
	}

	public static MeqCalDavServers buildFromMap(Map<String, Object> m) {
		MeqCalDavServers server = new MeqCalDavServers();
		if (m != null) {
			Object id = m.get("cals_id");
			if (id != null) {
				server.setId(Long.valueOf(String.valueOf(id)));
			}
			Object name = m.get("cals_name");
			if (name != null) {
				server.setName(String.valueOf(name));
			}
			Object url = m.get("cals_url");
			if (url != null) {
				server.setUrl(String.valueOf(url));
			}
			Object user = m.get("cals_username");
			if (user != null) {
				server.setUsername(String.valueOf(user));
			}
			Object pass = m.get("cals_password");
			if (pass != null) {
				server.setPassword(String.valueOf(pass));
			}
			Object realm = m.get("cals_realm");
			if (realm != null) {
				server.setRealm(String.valueOf(realm));
			}
		}
		return server;
	}
}
