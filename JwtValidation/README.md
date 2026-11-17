# JWT Validation - STS 4 Project

## Requirements
- Spring Tool Suite 4
- JDK 21 installed and selected in STS
- Internet connection for the first Maven dependency download

## Import into STS 4
1. Extract `JwtValidation_STS4.zip`.
2. Open STS 4.
3. Select **File > Import > Maven > Existing Maven Projects**.
4. Browse to the extracted `JwtValidation_STS4` folder.
5. Select the `pom.xml` and click **Finish**.
6. If needed, right-click the project > **Maven > Update Project**.

## Run
1. Expand `src/main/java`.
2. Open `com.example.jwtvalidation.JwtValidationApplication`.
3. Right-click it > **Run As > Spring Boot App**.
4. Wait for `Tomcat started on port 8080`.

## Swagger UI
Open: http://localhost:8080/swagger-ui/index.html

## Test 1: Generate a valid token
POST `http://localhost:8080/api/jwt/generate`

Optional query parameters:
- `username` (default: manvith)
- `role` (default: ROLE_USER)
- `expired` (default: false)

Example: `http://localhost:8080/api/jwt/generate?username=manvith&role=ROLE_USER`

Copy the `token` value from the response.

## Test 2: Validate the token
POST `http://localhost:8080/api/jwt/validate`

JSON body:
```json
{
  "token": "PASTE_TOKEN_HERE"
}
```

A valid token returns HTTP 200 with username and roles.

## Test 3: Altered token
Change one or more characters in the copied token and submit it to `/api/jwt/validate`.
It should return HTTP 401 with `Invalid Token`.

## Test 4: Expired token
Generate an expired token using:
`http://localhost:8080/api/jwt/generate?username=manvith&role=ROLE_USER&expired=true`

Submit that token to `/api/jwt/validate`. It should return HTTP 401.

## Note
The `/generate` endpoint and the hard-coded demo secret are for this classroom experiment only.
Do not use them in a production application.
