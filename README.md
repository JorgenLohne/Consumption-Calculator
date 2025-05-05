# Norwegian household consuption calculator microservice

A REST API microservice written in Kotlin to compare a norwegian households income with the average, based on an integration towards [Statistisk sentralbyrå](https://www.ssb.no/).

Lets Norweigan households compare their spending to the national average (numbers are from 2022).

Submission for [Stacc - Budgeting and savings](https://github.com/stacc/stacc-challenge-public/tree/budgeting-and-savings)


## Run locally

Requires: [Docker](https://www.docker.com/) 

1. Clone the repository to your machine using git
2. Navigagte to the project root directory and build a docker image with `docker build -t some-tag-name .`
3. Run with docker using `docker run -d -p 8080:8080 --name consumption-calc some-tag-name`

The service will be avaiable on `localhost:8080`


## Deployment

Production enviroment is hosted as a public API on [Railway](https://railway.com) (until free trail credits run out..)
Automaticly deplpoyed on merge to the `main` branch.

The Public endpoint is: `http://consumption-calculator-production.up.railway.app`


## Service

There is only one endpoint:
POST `/api/v0/compareConsumption`
which expects a body of:
```json
{
  "commodityAndServiceGroup": {
    "foodAndBeverages": 0,
    "alcoholTobaccoNarcotics": 0,
    "clothingAndFootwear": 0,
    "housing": 0,
    "housingAndUtilities": 0,
    "furnishingsAndHouseholdEquipment": 0,
    "health": 0,
    "transport": 0,
    "communication": 0,
    "recreationAndCulture": 0,
    "education": 0,
    "restaurantsAndHotels": 0,
    "insuranceAndFinancialServices": 0,
    "other": 0
  }
}
```

where the nummber `0` should be replaced with how much the household spends for a year, for that category.

The service will respond with a json body:
```json
{
  "foodAndBeverages": 0,
  "alcoholTobaccoNarcotics": 0,
  "clothingAndFootwear": 0,
  "housing": 0,
  "housingAndUtilities": 0,
  "furnishingsAndHouseholdEquipment": 0,
  "health": 0,
  "transport": 0,
  "communication": 0,
  "recreationAndCulture": 0,
  "education": 0,
  "restaurantsAndHotels": 0,
  "insuranceAndFinancialServices": 0,
  "other": 0
}
```
where the numbers for each cateogry indicates the difference between the submitted households income, and the average (for the year 2022) for Norway in NOK. Negative numbers indicate that the household income submitted has a lower spending than average, while positive number mean that they have a higher spending than average. 


## Potential future improvements

- Add tests (unit & integration)
- Make service more consumer friendly:
  - Add helpful error messages.
  - Make the currency more obvious.
  - Have request take consuption by month instead for the full year.
- Add openAPI / swagger spec
- Cache responses from SSB.
- add a frontend
