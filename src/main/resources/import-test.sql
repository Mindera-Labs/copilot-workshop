-- Test data for the Todo table
INSERT INTO todo(id, title, description, completed) VALUES (1, 'Learn Quarkus', 'Study the Quarkus documentation and tutorials', false);
INSERT INTO todo(id, title, description, completed) VALUES (2, 'Build a REST API', 'Create a RESTful API using Quarkus', false);
INSERT INTO todo(id, title, description, completed) VALUES (3, 'Write tests', 'Create unit and integration tests', true);
INSERT INTO todo(id, title, description, completed) VALUES (4, 'Implement validation', 'Add Bean Validation to the API', false);
-- Set the sequence to the next value after the inserted IDs
ALTER SEQUENCE todo_seq RESTART WITH 5;