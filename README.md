# SCOOTER APPLICATION INSTRUCTIONS

## TO START THE APPLICATION FOLLOW THESE STEPS.
You need to be connected to internet to start the app.
### 1st step: START MCSV-CONFG

### 2nd step: START MCSV-EUREKA

### 3rd step: START MCSV-GATEWAY

### 4th step: START MCSV-AUTH

### 5th step: START ALL REMAINING MCSV

#### CREATE USERS FOR LOGIN

Examples:

{
    "username": "super-admin",
    "password": "password",
    "roles": ["ADMIN,MAINTENANCE,USER"]
}

{
    "username": "admin",
    "password": "password",
    "roles": ["ADMIN"]
}

{
    "username": "maintenance",
    "password": "password",
    "roles": ["MAINTENANCE"]
}

{
    "username": "common-user",
    "password": "password",
    "roles": ["USER"]
}

#### LOGIN

Only use credentials.

{
    "username": "super-admin",
    "password": "password"
}