# Family Circle API - Social Networking Application

## Description

This is a Java project using Spring Boot and Maven. It's a social networking application that includes features such as register users, create post, comment, react, share, follow, evaluate physical condition for mommy and baby, and so on. The application also includes security configurations and logging.

## Getting Started

### Prerequisites

-   Java 11 or higher
-   Maven
-   MySQL
-   RabbitMQ
-   Amazon S3 bucket

### Installation

1. Clone the repository
2. Navigate to the project directory - `cd source/social-networking`
3. Set up the environment variables essentially for the database connection, RabbitMQ connection, Email SMTP connection, JWT secret key, Amazon S3 bucket, and AWS credentials.
4. Run `mvn clean install` to build the project

## Usage

Run the project using the command `mvn spring-boot:run`. The application will start and you can interact with it through the exposed endpoints.

## Logging

The application uses Log4j2 for logging. The configuration is located in `log4j2.xml`. Logs are written to the console and also to a file in the `logs` directory.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## Contact

Nguyễn Huỳnh Thanh Toàn - Email: [toannguyenit239@gmail.com](mailto:toannguyenit239@gmail.com)

Project Link: [https://github.com/zakushi2002/social-networking-api](https://github.com/zakushi2002/social-networking-api)

Please replace the placeholders with the actual values.
