select * from NRNAStudentCommittee.users;

Update NRNAStudentCommittee.users set is_admin = true where id = 1;

Update NRNAStudentNetwork.users set appl = false where id =3;

select * from NRNAStudentNetwork.university;

select * from NRNAStudentCommittee.news;	

Select * from NRNAStudentCommittee.university_outreach;

CREATE TABLE NRNAStudentNetwork.university_outreach (
    id int,
    FullName varchar(255),
    PhoneNumber varchar(255),
    Email varchar(255),
    AssociatedUniversity varchar(255),
    isNSU varchar(255)
);

Delete from NRNAStudentNetwork.university_outreach where id = 3;

Update NRNAStudentNetwork.users set associate_universities = "Baltimore City College, <br>Hood College" where id =3;

INSERT INTO NRNAStudentCommittee.university_outreach(id, full_name, phone_number, email, associated_universities, is_nsu)
VALUES (1, "Ramesh Bhatta", 2409936466, "rameshbhatta@gmail.com", "Baltimore City College", "Yes");
INSERT INTO NRNAStudentCommittee.university_outreach(id, full_name, phone_number, email, associated_universities, is_nsu)
VALUES (2, "Santosh Thapa", 2409936467, "santoshthapa@gmail.com", "Baltimore Community College", "No");
INSERT INTO NRNAStudentCommittee.university_outreach(id, full_name, phone_number, email, associated_universities, is_nsu)
VALUES (5, "Badal Thapa", 2483453534, "badalThapa@gmail.com", "Baltimore Community College <br>Baltimore City College <br>Bowie College", "No");
INSERT INTO NRNAStudentCommittee.university_outreach(id, full_name, phone_number, email, associated_universities, is_nsu)
VALUES (4, "Badal Thapa", 2483453534, "badalThapa@gmail.com", "Baltimore Community College <br>Baltimore City College <br>Hood College", "No");

Update NRNAStudentNetwork.news Set persist_date = "2024-12-06" where id between 1 and 10;

Update NRNAStudentCommittee.news Set news_date = "4 hours ago" where id between 620 and 640;

Update NRNAStudentCommittee.news Set news_date = "3 minutes ago" where id between 641 and 660;

Update NRNAStudentCommittee.news Set news_date = "5 minute ago" where id between 661 and 690;

Update NRNAStudentCommittee.news Set news_date = "2 days ago" where id between 51 and 100;

Update NRNAStudentCommittee.news Set news_date = "2 months ago" where id between 71 and 100;

Update NRNAStudentCommittee.news Set news_date = "2 hours ago" where id between 61 and 80;

Delete from NRNAStudentCommittee.news where id between 6 and 8;

select * from NRNAStudentNetwork.tokens;

select * from NRNAStudentNetwork.hibernate_sequence;

drop table NRNAStudentNetwork.tokens;

drop table NRNAStudentNetwork.hibernate_sequence;

select * from NRNAStudentNetwork.tokens;

CREATE TABLE university (
    id int,
    name varchar(255),
    city varchar(255),
    state varchar(255),
    is_public varchar(255),
    ranking_type varchar(255),
    ranking varchar(255),
    average_cost int,
    acceptance_rate int,
    enrollment int
);

INSERT INTO university (id, name, city, state, is_public, ranking_type, ranking, average_cost, acceptance_rate, enrollment)
VALUES (1, "St. Cloud State University", "Saint Cloud", "Minnesota", true, "US News Best Colleges", "33", 10117, 95.3, 8013),
(2, "Saginaw Valley State University", "University Center", "Michigan", true, "Top Public Schools", "36", 28752, 76.4, 16724),
(3, "University of Louisiana", "Monroe", "Louisiana", true, "Best School In Louisiana", "10", 21679, 73, 8277),
(4, "University of Idaho", "Moscow", "Idaho", true, "Top Public Schools", "97", 29784, 79, 12301),
(5, "University of Texas", "Arlington", "Texas", true, "Top Public Schools", "126", 12208, 81, 41613),
(6, "Caldwell University", "Caldwell", "New Jersey", false, "Best Value Schools", "15", 39450, 66.4, 2214),
(7, "University of South Dakota", "Vermillion", "South Dakota", true, "Top Public Schools", "152", 12942, 99, 10619),
(8, "McNeese State University", "Lake Charles", "Louisiana", true, "Top Public Schools", "38 (Tie)", 11000, 71.2, 7626),
(9, "University of Central Oklahoma", "Edmond", "OK", true, "Top Public Schools", "38 (Tie)", 27000, 81.2, 12554),
(10, "University of North Texas", "Denton", "Texas", true, "Top Public Schools", "119", 23692, 72, 46000);

INSERT INTO university (id, name, city, state, average_cost, acceptance_rate, enrollment)
VALUES (2, "Saginaw Valley State University", "University Center", "Michigan", 28752, 76.4, 16724);

INSERT INTO university (id, name, city, state, average_cost, acceptance_rate, enrollment)
VALUES (3, "University of Louisiana", "Monroe", "Louisiana", 21679, 73, 8277);

INSERT INTO university (id, name, city, state, average_cost, acceptance_rate, enrollment)
VALUES (4, "University of Idaho", "Moscow", "Idaho",  29784, 79, 12301);

INSERT INTO university (id, name, city, state, average_cost, acceptance_rate, enrollment)
VALUES (5, "University of Texas", "Arlington", "Texas", 12208, 81, 41613);

INSERT INTO university (id, name, city, state, average_cost, acceptance_rate, enrollment)
VALUES (6, "Caldwell University", "Caldwell", "New Jersey", 39450, 66.4, 2214);

INSERT INTO university (id, name, city, state, average_cost, acceptance_rate, enrollment)
VALUES (7, "University of South Dakota", "Vermillion", "South Dakota", 12942, 99, 10619);

INSERT INTO university (id, name, city, state, average_cost, acceptance_rate, enrollment)
VALUES (8, "McNeese State University", "Lake Charles", "Louisiana", 11000, 71.2, 7626);

INSERT INTO university (id, name, city, state, average_cost, acceptance_rate, enrollment)
VALUES (9, "University of Central Oklahoma", "Edmond", "OK", 27000, 81.2, 12554);

INSERT INTO university (id, name, city, state, average_cost, acceptance_rate, enrollment)
VALUES (10, "University of North Texas", "Denton", "Texas", 23692, 72, 46000);

