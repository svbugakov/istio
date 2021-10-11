cd ../
mvn clean package
cd k8s
docker image rm svbugakov/kuber:istio_callme
docker build . -t svbugakov/kuber:istio_callme
#docker login -u svbugakov
docker image push svbugakov/kuber:istio_callme
kubectl delete deployment callme-app-v1
kubectl delete deployment callme-app-v2
kubectl apply -f deployment.yaml