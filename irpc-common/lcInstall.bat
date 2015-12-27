call mvn package -Dmaven.test.skip=true
@echo '========================packge done==============================='
call mvn install:install-file -Dfile=./target/irpc-common-1.0.1.jar -DgroupId=com.uulookingfor.irpc -DartifactId=irpc-common -Dversion=1.0.1 -Dpackaging=jar
@echo '========================install done==============================='
@pause