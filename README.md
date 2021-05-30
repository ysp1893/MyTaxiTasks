# MyTaxiTasks

## Steps:
--------------------------------------------------------------------------------

1. Create DataBase with name "MyTexiApp" in MongoDB.
2. Insure MongoDB database run on port 27017 on localhost.
3. Start Application by right click on MytaxiServerApplicantTestApplication.java and run as Java application.
4. Execute api "http://localhost:9192/api/v1/reset/resetAll", it will store 10 cars and 10 driver dummy data.
5. For API refernce check swagger list on "http://localhost:9192/swagger-ui.html".

## Note:
--------------------------------------------------------------------------------
1. For Driver "contact_num" is unique.
2. For Car "license_plate" is unique.
3. Duplicate data not store for both above keys.
