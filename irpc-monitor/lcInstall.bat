call mvn package -Dmaven.test.skip=true
@echo '========================packge done==============================='
call mvn install:install-file -Dfile=./target/irpc-monitor-1.0-SNAPSHOT.jar -DgroupId=com.uulookingfor.irpc -DartifactId=irpc-monitor -Dversion=1.0.1 -Dpackaging=jar
@echo '========================install done==============================='
@pause