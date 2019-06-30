CREATE TABLE `work_time_tracker`.`users` (
  `user_ID` INT NOT NULL AUTO_INCREMENT,
  `Username` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(256) NOT NULL,
  `Email_address` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`user_ID`),
  UNIQUE INDEX `user_ID_UNIQUE` (`user_ID` ASC) VISIBLE,
  UNIQUE INDEX `Username_UNIQUE` (`Username` ASC) VISIBLE,
  UNIQUE INDEX `Email_address_UNIQUE` (`Email_address` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 1;

describe new_table1;

insert into new_table1 (username, password, email_address) values ('myName','mypass','myEmail');

select * from new_table1;

drop table new_table1;

CREATE TABLE IF NOT EXISTS school(regno int, name varchar(20), marks int);

select * from school;

insert into school values(23,'sfs',4);

CREATE TABLE `work_time_tracker`.`new_table2` (
  `name` VARCHAR(30) NOT NULL,
  `dob` DATE NOT NULL);
    
insert into new_table2 values('Sally', '1998-4-2');

commit;

select * from new_table2;

set global time_zone='-1:00';

select * from new_table2 where dob between '2017-01-01' and '2020-12-31';

insert into users (username, password) values ('Bob@gmail.com', 'aslfashdklfjhasljhfskljhdf');

select * from entries;

rollback;
select password from users where username='Matt@sdfg';

CREATE TABLE `work_time_tracker`.`entries` (
  `User_ID` INT NOT NULL,
  `Project` VARCHAR(75) NOT NULL,
  `Description` VARCHAR(500) NOT NULL,
  `date` date NOT NULL,
  `time` int)
ENGINE = InnoDB
AUTO_INCREMENT = 1;

describe entries;

select ID from users where username='test123@blah';

select project from entries where user_id=(select ID from users where username='test123@blah') group by project;

commit;

select ID from users where username='test1@test';

select * from entries where user_id=(select ID from users where username='test1@test') and project='another project';

select description, sum(time) from entries where user_id=(select ID from users where username='test123@blah') and project='test project' group by description;

ALTER TABLE `work_time_tracker`.`entries` 
ADD COLUMN `entry_id` INT NULL AUTO_INCREMENT AFTER `time`,
ADD UNIQUE INDEX `entry_id_UNIQUE` (`entry_id` ASC);

delete from entries where entry_id=1;

select * from entries;

