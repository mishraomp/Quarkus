#Docker Build and Run
1. Run the mongoDB container
    docker run -d -p 27017:27017 --name mongodb -v ~/data:/data/db mongo
2. Build the package
    ./mvnw clean package
3. Build the Docker Image
    docker build -f src/main/docker/Dockerfile.jvm -t employee-mongo-quarkus .
4. Run the docker Image
    docker run --link mongodb -i --rm -p 9080:9080 employee-mongo-quarkus

    

