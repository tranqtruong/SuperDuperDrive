CREATE TABLE IF NOT EXISTS USERS(
    email VARCHAR(254) PRIMARY KEY,
    salt VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    firstname VARCHAR(20) NOT NULL,
    lastname VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS NOTES (
    noteid INT PRIMARY KEY auto_increment,
    notetitle VARCHAR(20),
    notedescription VARCHAR (1000),
    email VARCHAR(254),
    foreign key (email) references USERS(email)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS(
    credentialid INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    keyy VARCHAR,
    password VARCHAR,
    email VARCHAR(254),
    foreign key (email) references USERS(email)
);

CREATE TABLE IF NOT EXISTS FILES (
    fileId INT PRIMARY KEY auto_increment,
    filename VARCHAR,
    contenttype VARCHAR,
    filesize VARCHAR,
    filedata BLOB,
    email VARCHAR(254),
    foreign key (email) references USERS(email)
);




create table if not exists persistent_logins (
    username varchar(64) not null,
	series varchar(64) primary key,
	token varchar(64) not null,
	last_used timestamp not null
);

create table if not exists TOKEN (
    id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR NOT NULL,
	used BOOLEAN DEFAULT FALSE
);