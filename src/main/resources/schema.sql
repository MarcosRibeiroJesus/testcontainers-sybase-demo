sp_dboption 'rh', 'select into', true
use rh;
DROP TABLE IF EXISTS people;

create table people(
    uuid integer not null primary key,
    name varchar(20),
    age integer
);


INSERT INTO people (uuid, name, age) VALUES (001, 'John Doe', 30);