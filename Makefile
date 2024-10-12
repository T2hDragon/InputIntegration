build:
	mvn -T 1C clean install -Dmaven.test.skip -DskipTests -Dmaven.javadoc.skip=true

run:
	java -jar target/input-integration-1.0-SNAPSHOT-jar-with-dependencies.jar

start: clean build run clean

clean:
	rm -fr target

db-build:
	docker-compose up --build
