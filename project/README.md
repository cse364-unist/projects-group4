Part I: Environment setup (30 points) 
After unziping files and making docker, ensure that there is MongoDB.
Also, enter your own parametres into application.properties. I decided to not include my own, because yours might be different. Then, run 'run.sh'.
BTW, I set server.host and port to 0.0.0.0 and 8080 respectively in applaction.properties.

'run.sh' will run MongoDB as background process, do mvn package and finish doing it.

It will take some time because the ratings dataset is huge.
If you want to run it faster, you can reduce the ratings dataset size.
For the sake of testing, I made rating dataset 9K entries.
If you want all million entries, copy data/ratings.dat to resources/ratings.dat.

Part II: Setup Spring Boot and Create REST APIs (50 points) 
For 'curl -X GET http://localhost:8080/employees', returns all employees.
Example: [{"id":1,"name":"Bilbo Baggins","role":"burglar"},{"id":2,"name":"Frodo Baggins","role":"thief"}]

For 'curl -X GET http://localhost:8080/employees/1' it will return employee with this id.
Example: {"id":1,"name":"Bilbo Baggins","role":"burglar"}
If ID does not exist, it will return 'Could not find employee {ID}'.
Example: Could not find employee 111.

For 'curl -X POST http://localhost:8080/employees -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'' it will create new employee, and return it.
Example: {"id":3,"name":"Samwise Gamgee","role":"gardener"} will be output, and it will be stored in DB.

For 'curl -X PUT http://localhost:8080/employees/3 -H 'Content-type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'' it will change employee with id 3, and return the changed one.
Example: {"id":3,"name":"Samwise Gamgee","role":"ring bearer"} will be output.

Part III: Setup Database and Query Database in REST APIs (70 points) 
The interface of REST APIs in quite simple, and I will give some examples of it.

For 'curl -X POST http://localhost:8080/movies -H 'Content-type:application/json' -d '{"title": "Sawawa", "genres": "Drama"}'' it will create new movie in the database.
Example: {"id":"660feb3f2e68011ec680f062","title":"Sawawa","genres":"Drama"} will be output.

For 'curl -X PUT http://localhost:8080/movies/660feb3f2e68011ec680f062 -H 'Content-type:application/json' -d '{"title": "Samama", "genres": "Comede"}'' it will change the movie in the database.
Example: {"id":"660feb3f2e68011ec680f062","title":"Samama","genres":"Comede"} will be output.

And if I try to access wrong id,
'curl -X GET http://localhost:8080/movies/dsaasd' (there is no dsaasd id), then it will return:
Could not find movie dsaasd.

The same thing works for "ratings" and "users".

Now, coming to the list of those movies averagely rated greater than or equal to the given rating score,
I implemented it as 'curl -X GET http://localhost:8080/ratings/minRating?minRating=score',
Where it will return movies that have rating >= score.

Example output when 'curl -X GET http://localhost:8080/ratings/minRating?minRating=4'
...
{"title":"African Queen, The (1951)","genres":"Action|Adventure|Romance|War"},
{"title":"Cat on a Hot Tin Roof (1958)","genres":"Drama"}
....