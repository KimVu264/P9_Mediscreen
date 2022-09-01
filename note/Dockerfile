#Use the OpenJDK 11 image as the base image
FROM openjdk:11

#Create a new app directory for my application files
RUN mkdir /app

#Set the direcotry for excution future commands
WORKDIR /app

#Copy the jar from host machine to image filesystem
COPY target/note-0.0.1-SNAPSHOT.jar note.jar


ENTRYPOINT ["java","-jar","note.jar"]