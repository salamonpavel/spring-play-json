# spring boot with custom json serializers

This application demonstrates how to define custom serializers for http endpoints in spring boot application of version 2.6.6.

The solution is prepared for two most common json libraries in Scala community - play-json and circe.

In order to define custom serializer one needs to:

- bring desired json dependencies into pom.xml
- define reads/writes (play-json) and/or encoders/decoders (circe) for used types
- define HttpMessageConverter for the json library of choice
- add converter into a list of converters in spring configuration
