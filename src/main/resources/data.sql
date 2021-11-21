DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks(
  id IDENTITY PRIMARY KEY,
  title VARCHAR(255),
  details VARCHAR(255),
  when VARCHAR(255),
  deadline VARCHAR(255),
  is_project BOOLEAN,
  parent INT,
  done BOOLEAN
); 



INSERT INTO tasks (title, details, when, deadline, is_project, parent, done) VALUES
	('Staff meeting', '', '10:00', '2021-09-10', true, -1, true),
	('App2 Development', '', '20:15', '2021-09-10', true, -1, false),
	('Lunch with friends.', '', '', '2021-09-10', false, -1, false),
	('Finish coding App2', '', '', '2021-09-10', false, 2, false),
	('Run tests on App2', '', '', '2021-09-09', false, -1, false),
	('Put up testing environment', '', '', '2021-09-10', false, 2, true),
	('Submit last month''s expenses', '', '', '2021-09-09', false, -1, false),
	('Get $ for weekend from ATM', '', '', '2021-09-09', false, -1, false),
	('Call Cindy in Accounting', '', '', '2021-09-09', false, -1, false),
	('Schedule doctor visit', 'Maecenas leo odio,', '', '2021-09-09', false, -1, false),
	('Pick up kids after soccer practice', '', '', '2021-09-10', false, -1, false),
	('Personal Tasks', 'details', '', '2021-09-09', true, -1, false),
	('New task', '', '', '2021-09-09', false, -1, false);
