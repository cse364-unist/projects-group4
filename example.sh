# Get Achievements
curl -X GET http://localhost:8080/achievements 
curl -X POST http://localhost:8080/achievements -H 'Content-type:application/json' \
    -d '{"id": {"userId": "1", "achievement": "Win20"}, "progress": 0, "timestamp": 12}'

curl -X POST http://localhost:8080/ratings -H 'Content-type:application/json' \
    -d '{"userId": 10, "movieId": 10, "rating": 5, "timestamp": 10}'