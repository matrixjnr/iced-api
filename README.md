# Iced API Kotlin

This is the Kotlin API for the backend of Iced Blogging Platform.

## Getting Started

### Prerequisites

- [Java 21 or higher](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Gradle 8.5 or higher](https://gradle.org/install/)

### Installation

1. Clone the repo
   ```sh
   git clone 
    ```
2. Install Gradle dependencies
3. Run the project
   ```sh
   gradle run
   ```
4. The server will be running on port 8080
5. You can access the API documentation on http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
6. You can access the H2 database on http://localhost:8080/h2-console

## Usage

### Authentication

The API uses JWT for authentication. To get a token, you need to send a POST request to `/auth/login` with the following body:

```json
{
  "username": "username",
  "password": "password"
}
```


### Endpoints

#### TODO

## License

Distributed under the MIT License. See `LICENSE` for more information.

