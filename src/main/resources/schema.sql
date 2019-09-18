CREATE TABLE usage_rate (
	year int primary key,
	usage_rate real
);

CREATE TABLE device (
	device_id int auto_increment,
	device_name varchar(128),
	primary key (device_id)
);

CREATE TABLE device_usage_rate (
	year int,
	device_id int,
	rate real,
	foreign key (device_id) references device(device_id),
	primary key(year, device_id)
);
