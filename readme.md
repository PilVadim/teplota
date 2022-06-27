# Teplota

## Temperature monitoring application
Application allows to collect temperature information for several locations 
with a customizable period for each coordinate pair. 
It also contains service which provides a temperature report with average aggregations for a selected period.

Temperature collecting could be started and stopped with API.

Locations for temperature collecting could be added or updated with API. 

Application also provides access to Swagger.

## Endpoints
### Public
* [GET] <b>/api/v1/places</b> - 
returns full list of current locations;
```
[
    {
        "id": 1,
        "name": "Saint Petersburg",
        "latitude": 59.8944,
        "longitude": 30.2642,
        "period": 20, //in seconds
        "enabled": true
    }
]
```
---
* [GET]
  <b>/api/v1/temperatures?start={start}&end={end}</b> - 
gets list of temperatures for a period with average aggregations (day, week, month), sorted by date
```
Example:
/api/v1/temperatures?start=01.06.2022 00:00:00&end=01.06.2022 00:05:00

Response:
[
    {
        "id": 1,
        "name": "Saint Petersburg",
        "temperatures": {
            "all": [
                {
                    "id": 1,
                    "celsius": 12.01,
                    "placeId": 1,
                    "moment": "01.06.2022 00:00:00"
                },
                {
                    "id": 5,
                    "celsius": 15.21,
                    "placeId": 1,
                    "moment": "01.06.2022 00:05:00"
                }
            ],
            "averageDays": [
                {
                    "celsius": 13.61,
                    "moment": "2022-06-01T00:00:00"
                }
            ],
            "averageWeeks": [
                {
                    "celsius": 13.61,
                    "moment": "2022-05-30T00:00:00"
                }
            ],
            "averageMonths": [
                {
                    "celsius": 13.61,
                    "moment": "2022-06-01T00:00:00"
                }
            ]
        }
    }
]
```

### Secured
[POST] <b>/api/v1/place</b> - validates and Adds new place, returns id of a new place
```
Body example:
{
    "name": "Moscow",
    "latitude": 55.7522, 
    "longitude": 37.6156,
    "period": 10,
    "enabled": true
}
```
---
[PUT] <b>/api/v1/place</b> - validates and Updates existing place, returns id of an existing place
```
Body example:
{
    "id":6,
    "name": "Moscow",
    "latitude": 55.7522, 
    "longitude": 37.6156,
    "period": 10,
    "enabled": true
}
```
---
[POST] <b>/api/v1/start</b> - starts collecting temperatures data, returns report
```
    1) Saint Petersburg - is started
    6) Moscow - is started
```
---
[POST] <b>/api/v1/stop</b> - stops collecting temperatures data

```
    Successfully stopped
```