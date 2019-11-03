#!/bin/bash
if [ $# -ne 1 ]; then
  echo "version not supplied"
  exit 1
fi

./gradlew.bat clean
./gradlew.bat jar

rm -r maven/tmp/
mkdir maven/tmp/

cp maven/api/pom.xml maven/tmp/pom.xml
sed -i "s/@VERSION@/$1/g" maven/tmp/pom.xml
mvn deploy:deploy-file -DgroupId=me.piggypiglet -DartifactId=metrics-api -Dversion=$1 -Dpackaging=jar -Dfile=api/build/libs/api-$1.jar -DpomFile=maven/tmp/pom.xml -DrepositoryId=piggypiglet -Durl=https://repo.piggypiglet.me/repository/maven-releases
rm maven/tmp/pom.xml
