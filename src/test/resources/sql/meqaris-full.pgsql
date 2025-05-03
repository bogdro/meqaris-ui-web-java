/*
 * Meqaris - the full database script.
 *
 * Copyright (C) 2022-2025 Bogdan 'bogdro' Drozdowski, bogdro (at) users . sourceforge . net
 *
 * This file is part of Meqaris (Meeting Equipment and Room Invitation System),
 *  software that allows booking meeting rooms and other resources using
 *  e-mail invitations.
 * Meqaris homepage: https://meqaris.sourceforge.io/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
createdb meqaris
*/

create schema meqaris;
comment on schema meqaris is 'The main schema for the Meqaris application';

/* Enabled by default since 9.0+ and we require 9.2 anyway:
create language plpgsql;
*/

/*
Required for indices, triggers and their comments.
*/
set schema 'meqaris';

/*
Required for GiST indices with integer columns.
*/
create extension if not exists btree_gist;

------------------ MEETING RESOURCES ---------------------

create table meqaris.meq_resources
(
	r_id serial constraint res_pk primary key,
	r_name varchar(1000) not null,
	r_email varchar(1000) not null
		constraint r_email_unique unique
		constraint r_email_syntax check (r_email ~* '^[a-z0-9_][a-z0-9_.\-]*@[a-z0-9_.\-]+$'),
	r_description varchar(1000),
	r_enabled bool not null default true
);

comment on table meqaris.meq_resources is 'The table for meeting resources (rooms, equipment)';
comment on column meqaris.meq_resources.r_id is 'Meeting resource''s ID (assigned automatically)';
comment on column meqaris.meq_resources.r_name is 'Meeting resource''s name';
comment on column meqaris.meq_resources.r_email is 'Meeting resource''s e-mail address';
comment on column meqaris.meq_resources.r_description is 'Meeting resource''s description';
comment on column meqaris.meq_resources.r_enabled is 'Whether or not this meeting resource is enabled';

------------------ MEETING EVENTS ------------------

create table meqaris.meq_events
(
	e_id serial constraint ev_pk primary key,
	e_entry_date timestamp with time zone not null default now(),
	e_organiser varchar(1000) not null,
	e_summary varchar(1000),
	e_dtstamp timestamp with time zone,
	e_uid varchar(1000) not null, /* not unique for finer-grained manipulation of events with multiple resources */
	e_seq int not null default 0 constraint e_seq_nonneg check (e_seq >= 0),
	e_data text
);

comment on table meqaris.meq_events is 'The table for meeting events';
comment on column meqaris.meq_events.e_id is 'Event ID (assigned automatically)';
comment on column meqaris.meq_events.e_entry_date is 'Event database entry timestamp'; /* for partitioning, if wanted */
comment on column meqaris.meq_events.e_organiser is 'Event organiser';
comment on column meqaris.meq_events.e_summary is 'Event summary (title)';
comment on column meqaris.meq_events.e_dtstamp is 'Event date/time stamp (sequential ID)';
comment on column meqaris.meq_events.e_uid is 'Event unique ID';
comment on column meqaris.meq_events.e_seq is 'Event sequential ID (for later updates)';
comment on column meqaris.meq_events.e_data is 'Event data (iCalendar)';

create index e_uid_index on meq_events (e_uid);
comment on index e_uid_index is 'The index for searching events by UID';

------------------ MEETING RESOURCE RESERVATIONS / BOOKINGS ------------------

create table meqaris.meq_resource_reservations
(
	rr_id serial constraint rr_pk primary key,
	rr_r_id int constraint rr_fk not null references meqaris.meq_resources (r_id),
	rr_interval tstzrange not null,
	rr_e_id int constraint rr_e_fk not null references meqaris.meq_events (e_id) on delete cascade,
	-- this is the constraint/index that does all the work:
	constraint rr_interval_excl exclude using gist (rr_r_id with =, rr_interval with &&)
);

comment on table meqaris.meq_resource_reservations is 'The table for meeting resource reservations/bookings';
comment on column meqaris.meq_resource_reservations.rr_id is 'Reservation ID (assigned automatically)';
comment on column meqaris.meq_resource_reservations.rr_r_id is 'Reservation resource''s ID';
comment on column meqaris.meq_resource_reservations.rr_interval is 'Reservation time interval';
comment on column meqaris.meq_resource_reservations.rr_e_id is 'Reservation event''s ID';

create index meq_resource_reservations_resource_fk on meq_resource_reservations (rr_r_id);
comment on index meq_resource_reservations_resource_fk is 'The index for the reservation''s resource foreign key';

create index meq_resource_reservations_events_fk on meq_resource_reservations (rr_e_id);
comment on index meq_resource_reservations_events_fk is 'The index for the reservation''s event foreign key';

/* A trigger is better than a constraint for data migration or updating
   new columns after adding them (a constraint would prevent an update,
   even of another column). */
create or replace function meqaris.trg_force_interval_in_future()
returns trigger as
$trg_force_interval_in_future$
begin
	raise 'Inserting events in the past not allowed'
		using hint = 'Make sure both ends of the rr_interval column are in the future (constraint rr_interval_in_future)',
		errcode = 'prohibited_sql_statement_attempted';
	return null;
end;
$trg_force_interval_in_future$ language plpgsql;

comment on function meqaris.trg_force_interval_in_future() is
'Function for the trigger that prevents inserting new events in the past.';

create trigger trg_rr_interval_in_future
before insert or update of rr_interval
on meqaris.meq_resource_reservations
for each row
when (lower(new.rr_interval) < now() or upper(new.rr_interval) < now())
execute procedure meqaris.trg_force_interval_in_future();

comment on trigger trg_rr_interval_in_future on meqaris.meq_resource_reservations is
 'Trigger that prevents inserting new events in the past.';

------------------ CALDAV SERVERS ------------------

create table meqaris.meq_caldav_servers
(
	cals_id serial constraint cals_pk primary key,
	cals_name varchar(1000) not null,
	cals_url varchar(1000) not null constraint cals_url_unique unique,
	cals_username varchar(1000),
	cals_password varchar(1000),
	cals_realm varchar(1000)
);

comment on table meqaris.meq_caldav_servers is 'The table for CalDAV servers';
comment on column meqaris.meq_caldav_servers.cals_id is 'CalDAV server ID (assigned automatically)';
comment on column meqaris.meq_caldav_servers.cals_name is 'CalDAV server name';
comment on column meqaris.meq_caldav_servers.cals_url is 'CalDAV server URL';
comment on column meqaris.meq_caldav_servers.cals_username is 'CalDAV server username (if needed)';
comment on column meqaris.meq_caldav_servers.cals_password is 'CalDAV server password (if needed)';
comment on column meqaris.meq_caldav_servers.cals_realm is 'CalDAV server access realm (if needed)';

------------------ CALDAV SERVER-TO-RESOURCE MAPPING ------------------

create table meqaris.meq_caldav_servers_resources
(
	calres_cals_id int constraint calres_cals_fk not null
		references meqaris.meq_caldav_servers (cals_id) on delete cascade,
	calres_r_id int constraint calres_r_fk not null
		references meqaris.meq_resources (r_id) on delete cascade,
	constraint calres_unique unique (calres_cals_id, calres_r_id)
);

comment on table meqaris.meq_caldav_servers_resources is 'The table for CalDAV server-to-resource mapping';
comment on column meqaris.meq_caldav_servers_resources.calres_cals_id is 'CalDAV server ID';
comment on column meqaris.meq_caldav_servers_resources.calres_r_id is 'Resource ID';

create index meq_caldav_servers_resources_cal_fk on meq_caldav_servers_resources (calres_cals_id);
comment on index meq_caldav_servers_resources_cal_fk is 'The index for the calendar server foreign key in CalDAV server-to-resource mapping';

create index meq_caldav_servers_resources_resource_fk on meq_caldav_servers_resources (calres_r_id);
comment on index meq_caldav_servers_resources_resource_fk is 'The index for the resource foreign key in CalDAV server-to-resource mapping';

------------------ CONFIGURATION ------------------

create table meqaris.meq_config
(
	c_name varchar(1000) constraint conf_pk primary key,
	c_value varchar(1000),
	c_description varchar(1000)
);

comment on table meqaris.meq_config is 'The table for Meqaris configuration';
comment on column meqaris.meq_config.c_name is 'Configuration parameter name';
comment on column meqaris.meq_config.c_value is 'Configuration parameter value';
comment on column meqaris.meq_config.c_description is 'Configuration parameter description';

insert into meqaris.meq_config (c_name, c_value, c_description)
values ('mail_server', null, 'The address of the mail server to use (NULL = default)');

insert into meqaris.meq_config (c_name, c_value, c_description)
values ('mail_server_port', null, 'The port on the mail server to use (NULL = default)');

insert into meqaris.meq_config (c_name, c_value, c_description)
values ('mail_sending_method', null, 'The method to send mail replies: NULL=default, Mail::Internet, mail_command');

insert into meqaris.meq_config (c_name, c_value, c_description)
values ('mail_command', null, 'The command to pipe mail replies to when mail_sending_method="mail_command" (NULL = none)');

insert into meqaris.meq_config (c_name, c_value, c_description)
values ('reply_detail_level', '0', 'An integer saying how much detailed will the replies be. 0 = the default.');

insert into meqaris.meq_config (c_name, c_value, c_description)
values ('parser_tmp_dir', '/tmp', 'The path to a temporary directory for files with message parts. Default = /tmp. NULL means use the default.');

insert into meqaris.meq_config (c_name, c_value, c_description)
values ('db_version', '7', 'The current version of the Meqaris database');
