## Simple project management // todo list application 

- simple http server written on java.net sockets (processes GET/POST http requests)
- MVC architecture
- persists data in a MySQL database via JDBC 
- custom HTML template engine for view

### How to run development build

```
git clone https://github.com/dmitriydb/simpletodolist
cd simpletodolist 
psql -U postgres -a -f schema.sql;
mvn compile exec:java -Dexec.mainClass=simpletodolist.SimpleToDoList
```

### Features
#### To-do tasks and lists
![Example](https://files.catbox.moe/5befse.png)

#### Storing simple notes
![Example](https://files.catbox.moe/wfi4ef.png)

#### Simple bugtracker
![Example](https://files.catbox.moe/8nu4fq.png)

#### Updates overview at main page

![Example](https://files.catbox.moe/4wt5mg.png)

