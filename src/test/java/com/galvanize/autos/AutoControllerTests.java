package com.galvanize.autos;

public class AutoControllerTests {
    //- GET: /api/autos Returns list of all autos in db when autos exist
    //- GET: /api/autos Returns 204 when no autos found when no autos in db
    //- GET: /api/autos?color=blue Returns list of all blue cars in db
    //- GET: /api/autos?make=Volkswagen Returns list of all Volkswagens in db
    //- GET: /api/autos?make=Volkswagen&color=blue Returns list of all blue Volkswagens in db

    //- POST: /api/autos Returns the created entry
    //- POST: /api/autos Returns 400 for bad request. e.g. insufficient request body info.

    //- GET: /api/autos/{vin} Returns the requested auto with vin number if it exist in db
    //- GET: /api/autos{vin} Returns 204 when no autos with corresponding vin number in db

    //- PATCH: /api/autos/{vin} Returns the requested auto after PATCH if it exists in db
    //- PATCH: /api/autos{vin} Returns 204 when no autos with corresponding vin number in db
    //- PATCH: /api/autos{vin} Returns 400 for bad request

    //- DELETE: /api/autos/{vin} Returns 202 for successful deletion
    //- DELETE: /api/autos{vin} Returns 204 when no autos with corresponding vin number in db

}
