# Get Achievements
curl -X POST http://localhost:8080/users -H 'Content-type:application/json' \
    -d '{"gender": "F", "occupation": 2, "age": 20, "zipCode": "060000"}'
curl -X POST http://localhost:8080/ratings -H 'Content-type:application/json' \
    -d '{"userId": "6634cb343d74894b17a19311", "movieId": 10, "rating": 5, "timestamp": 10}'
curl -X POST http://localhost:8080/ratings -H 'Content-type:application/json' \
    -d '{"userId": "10", "movieId": 10, "rating": 5, "timestamp": 10}'


curl -X POST http://localhost:8080/ratings -H 'Content-type:application/json' -d '{"userId": "666eb4f7ff8d220b4f55663f", "movieId": 10, "rating": 5, "timestamp": 10}'

curl -X GET http://localhost:8080/achievements
curl -X POST http://localhost:8080/achievements -H 'Content-type:application/json' \
    -d '{"id": {"userId": "1", "achievement": "ReviewOne"}, "progress": 100, "timestamp": 12}'
curl -X PUT http://localhost:8080/achievements/1/ReviewOne -H 'Content-type:application/json' \
    -d '{"progress": 0, "timestamp": 12}'
curl -X DELETE http://localhost:8080/achievements/1/ReviewOne


# Get Recap (Done)
curl -X GET http://localhost:8080/recap/1?currentTime=999999999\&deltaTime=100000000

# Get Summary (Done)
curl -X POST http://localhost:8080/summaries/ -H 'Content-type:application/json' \
    -d '{"movieId": "1", "timestamp": 1, "summaryText": "dsadsa"}'
curl -X GET http://localhost:8080/summaries/
curl -X PUT http://localhost:8080/summaries/1 -H 'Content-type:application/json' \
    -d '{"movieId": "23", "timestamp": 23, "summaryText": "23"}'
curl -X DELETE http://localhost:8080/summaries/1

curl -X GET http://localhost:8080/ratings/summary/aa