all: clean compile 

clean:
	rm -rf build/*
	rm -f dist/app.jar
	find . -name "*.class" -exec rm -rf {} \;

app: compile
	cd build ; jar -cvfe ../dist/app.jar GuessWhoServer .

test: compile app
	java -cp dist/restlet.jar:dist/restlet-json.jar:dist/restlet-jackson.jar:dist/jackson-core-2.8.3.jar:dist/jackson-annotations-2.8.3.jar:dist/jackson-dataformat-smile-2.8.3.jar:dist/jackson-databind-2.8.3.jar:dist/jackson-dataformat-xml-2.8.3.jar:dist/jackson-dataformat-yaml-2.8.3.jar:dist/jackson-dataformat-csv-2.8.3.jar:dist/app.jar api.GuessWhoServer

compile: 
	javac -cp \
	dist/json.jar:\
	dist/restlet.jar:\
	dist/restlet-json.jar:\
	dist/restlet-jackson.jar:\
	dist/jackson-core-2.8.3.jar:\
	dist/jackson-annotations-2.8.3.jar \
	-d build \
	src/GuessWhoClasses/*.java \
	src/api/*.java

docker-build: app
	docker build -t guesswho .
	docker images

docker-clean:
	docker stop guesswho
	docker rm guesswho
	docker rmi guesswho

docker-run:
	docker run --name guesswho -td guesswho
	docker ps

docker-run-host:
	docker run --name guesswho -td --net=host guesswho
	docker ps

docker-run-bridge:
	docker run --name guesswho -td -p 80:8080 guesswho
	docker ps

docker-network:
	docker network inspect host
	docker network inspect bridge

docker-stop:
	docker stop guesswho
	docker rm guesswho

docker-shell:
	docker exec -it guesswho bash 

run:
	echo Starting Service at:  http://localhost:8080
	java -cp build:dist/restlet.jar:dist/restlet-json.jar:dist/json.jar:dist/restlet-jackson.jar:dist/jackson-core-2.8.3.jar:dist/jackson-annotations-2.8.3.jar:dist/jackson-dataformat-smile-2.8.3.jar:dist/jackson-databind-2.8.3.jar:dist/jackson-dataformat-xml-2.8.3.jar:dist/jackson-dataformat-yaml-2.8.3.jar:dist/jackson-dataformat-csv-2.8.3.jar api.GuessWhoServer


