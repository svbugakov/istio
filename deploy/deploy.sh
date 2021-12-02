cd ../
cd inkass-data
gradle build
cd ../
cd caller-service/k8s
./deploy.sh
cd ../
cd ../
cd callme-service/k8s
./deploy.sh