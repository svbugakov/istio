cd ../
gradle build
cd k8s
docker image rm svbugakov/kuber:istio_caller
docker build . -t svbugakov/kuber:istio_caller
#docker login -u svbugakov
docker image push svbugakov/kuber:istio_caller
kubectl delete deployment caller-app
kubectl apply -f deployment.yaml