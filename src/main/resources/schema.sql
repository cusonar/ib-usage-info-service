DROP TABLE IF EXISTS usage_rate;
DROP TABLE IF EXISTS device;
DROP TABLE IF EXISTS device_usage_rate;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;

CREATE TABLE usage_rate (
	year int,
	usage_rate real,
	primary key (year)
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
