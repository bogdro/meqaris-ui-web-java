insert into meqaris.meq_resources (r_id, r_name, r_email, r_description)
values (1, 'Room 403', 'room403@localhost', 'Room 403');

insert into meqaris.meq_events (e_id, e_organiser, e_summary, e_dtstamp, e_uid, e_seq)
values (1, 'meqaris@localhost', 'Meeting', '2100-01-01 01:02:03+01', 'uid007', 1);

insert into meqaris.meq_resource_reservations (rr_r_id, rr_interval, rr_e_id)
values (1, '[2100-01-01 01:02:03+01, 2100-01-01 02:02:03+01)', 1);

insert into meqaris.meq_caldav_servers (cals_id, cals_name, cals_url)
values (1, 'CalDAV 1', 'http://localhost/caldav1');

insert into meqaris.meq_caldav_servers_resources (calres_cals_id, calres_r_id)
values (1, 1);

commit;
