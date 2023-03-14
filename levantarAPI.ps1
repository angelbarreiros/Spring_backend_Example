
if (-not (docker images -q db:latest)) {

    docker build -f ./db.dockerfile -t db:latest .
}
docker run -d -p 3306:3306 db:latest
do {
    Start-Sleep -Seconds 25
} until ((Test-NetConnection localhost -Port 3306).TcpTestSucceeded)


mvn package
docker stop $(docker ps -q -f "ancestor=db:latest")


docker-compose up
