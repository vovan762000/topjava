#### Get all meals:
curl http://localhost:8080/topjava/rest/meals
#### Get meal with id=100003:
curl http://localhost:8080/topjava/rest/meals/100003
#### Create new meal
curl -H "Content-Type: application/json" -X POST -d '{"dateTime":"2022-01-30T10:02:00","description":"New breakfest","calories":999}' http://localhost:8080/topjava/rest/meals
#### Update meal with id=100003
curl -H "Content-Type: application/json" -X PUT -d '{"dateTime":"2020-01-30T10:02:00","description":"Updated breakfest","calories":200}' http://localhost:8080/topjava/rest/meals/100003
#### Get filtered meals
curl http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=00:00:00&endDate=2020-01-31&endTime=21:00:00
#### Delete meal with id=100003
curl -X DELETE http://localhost:8080/topjava/rest/meals/100003

