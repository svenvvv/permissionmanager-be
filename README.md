# Permission Manager Back-End

## Setup

### Prerequisites

Install openjdk 17 and maven 3.8.2 from your package manager.

Or if you wish to build and run inside containers then install docker or podman.

### Build and run locally

Execute the following command to build and run:
```
mvn compile spring-boot:run
```

To build JAR artifacts execute the following command:
```
mvn compile package
```

### Build and launch in Docker

**It is highly recommended to use Docker Compose with the files provided in the
[unified repository](https://github.com/svenvvv/permissionmanager) instead of
this setup.**

Set up Docker, navigate to the project directory and build the Docker image with the following command:
```
docker build -t permissionmanager-be .
```

Afterwards the image can be launched using:
```
docker run -it -p 8080:8080 permissionmanager-be
```

