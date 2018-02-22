
Get an [API Key](https://customer.cloudkarafka.com/team/api).

```
./mvnw clean package
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