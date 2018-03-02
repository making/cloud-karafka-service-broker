
Get an [API Key](https://customer.cloudkarafka.com/team/api).


### Deploy on Cloud Foundry

```
mkdir target
wget http://repo1.maven.org/maven2/am/ik/servicebroker/cloud-karafka-service-broker/0.0.3/cloud-karafka-service-broker-0.0.3.jar -o cloud-karafka-service-broker.jar
cf push --no-start
cf set-env cloud-karafka-service-broker CLOUDKARAFKA_API_KEY xxxxxxxxxxxx
cf set-env cloud-karafka-service-broker SPRING_SECURITY_USER_PASSWORD xxxxxxxxxxxx
cf start cloud-karafka-service-broker
```


```
cf create-service-broker cloudkarafka admin xxxxxxxxxxxx https://cloud-karafka-service-broker.<apps domain>
cf enable-service-access cloudkarafka
```

or  (unless you are an admin)

```
cf create-service-broker cloudkarafka admin xxxxxxxxxxxx https://cloud-karafka-service-broker.<apps domain> --space-scoped
```

![image](https://user-images.githubusercontent.com/106908/36542160-683ba906-1823-11e8-8108-dfb26897d8e4.png)


```
cf create-service cloudkarafka duck my-kafka
```

#### Deploy sample consumer

```
git clone git@github.com:making/demo-kafka-consumer.git
cd demo-kafka-consumer
./mvnw clean package -DskipTests=true

cf push
```

Receive messages

```
curl -H "Accept: text/event-stream" -k https://demo-kafka-consumer.<app domain>/messages
```

#### Deploy sample producer

```
git clone git@github.com:making/demo-kafka-producer.git
cd demo-kafka-producer
./mvnw clean package -DskipTests=true

cf push
```

Send messages

```
while true;do curl -k -s https://demo-kafka-producer.<app domain>/messages -s -H "content-type: text/plain" -d Hello-$(uuidgen) -w '\n';sleep 1;done
```

### Deploy on Kubernetes

#### Set up service catalog 
```
./k8/install-service-catalog.sh
kubectl apply -f k8s/namespace.yml
```

#### Deploy service broker

```
cp k8s/secret.yml.old k8s/secret.yml
# Edit secret.yml for your environment

kubectl apply -f k8s/secret.yml
kubectl apply -f k8s/deployment.yml
kubectl apply -f k8s/cluster-service-broker.yml
```

#### Create and bind a service instance

```
kubectl apply -f k8s/sample/service-instance.yml
kubectl apply -f k8s/sample/service-binding.yml
```

#### Deploy sample consumer

```
kubectl apply -f k8s/sample/consumer.yml
```

#### Deploy sample producer

```
kubectl apply -f k8s/sample/producer.yml
```