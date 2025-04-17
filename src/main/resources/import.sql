-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- Create the hibernate_sequence
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

-- Sample data for Todo table
INSERT INTO todo(id, title, description, completed) VALUES (nextval('hibernate_sequence'), 'Learn Quarkus', 'Study the Quarkus framework and its features', false);
INSERT INTO todo(id, title, description, completed) VALUES (nextval('hibernate_sequence'), 'Build a REST API', 'Create a RESTful API with Quarkus', false);
INSERT INTO todo(id, title, description, completed) VALUES (nextval('hibernate_sequence'), 'Connect to PostgreSQL', 'Configure and use PostgreSQL with Panache', true);
INSERT INTO todo(id, title, description, completed) VALUES (nextval('hibernate_sequence'), 'Write unit tests', 'Add unit tests for the application', false);
INSERT INTO todo(id, title, description, completed) VALUES (nextval('hibernate_sequence'), 'Deploy application', 'Deploy the Quarkus application to production', false);