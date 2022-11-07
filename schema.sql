CREATE USER todolist WITH password 'todolist';

CREATE DATABASE "todolist" WITH OWNER "todolist" ENCODING 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8' template = 'template0';

\c todolist;

CREATE TABLE projects (id serial primary key, name varchar);
CREATE TABLE lists (id serial primary key, name varchar);
CREATE TABLE tasks (id serial primary key, time varchar, text varchar);
CREATE TABLE tasks_in_lists (task_id integer, list_id integer);
CREATE TABLE issues (id serial, status varchar, text varchar, time varchar);
CREATE TABLE issues_in_projects (issue_id integer, project_id integer);
CREATE TABLE notes(title text, id serial, text varchar, time varchar);
GRANT ALL PRIVILEGES ON DATABASE "todolist" to todolist;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO todolist;
GRANT USAGE ON SCHEMA public TO todolist;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO todolist;