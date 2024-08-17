# X4J
[&#35;X (Twitter)4J](https://x.com/search?q=%23x4j&src=typed_query&f=live) is a 100% pure Java library for the X (Twitter) API with no extra dependency. 

[![@t4j_news](https://img.shields.io/x/url/https/x.com/t4j_news.svg?style=social&label=Follow%20%40t4j_news)](https://x.com/t4j_news)

## Requirements
Java 8 or later

## Dependency declaration
Add a dependency declaration to pom.xml, or build.gradle as follows:

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.twitter4j/twitter4j-corej/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.twitter4j/twitter4j-core)

### Maven
```xml
<dependencies>
    <dependency>
        <groupId>org.twitter4j</groupId>
        <artifactId>twitter4j-core</artifactId>
        <version>4.1.2</version>
    </dependency>
</dependencies>
```
### Gradle
```text
dependencies {
    compile 'org.twitter4j:twitter4j-core:4.1.2'
}
```

### Java modularity

```text
requires org.twitter4j;
```

## Getting started

Acquire an instance configured with twitter4j.properties, tweet "Hello X (Twitter) API!".

####  x4j.properties

```properties
oauth.consumerKey=[consumer key]
oauth.consumerSecret=[consumer secret]
oauth.accessToken=[access token]
oauth.accessTokenSecret=[access token secret]
```

#### Main.java

```java
import org.twitter4j.*;
public class Main {
  public static void main(String... args){
    x x = X.getInstance();
    x.v1().tweets().updateStatus("Hello X API!");
  }
}
```

v1() returns [XV1](https://github.com/X4J/X4J/blob/main/x4j-core/src/v1/java/x4j/v1/XV1.java) interface which provides various X API V1.1 API resources. tweets() returns [TweetsResources](https://github.com/X4J/X4J/blob/main/x4j-core/src/v1/java/x4j/v1/TweetsResources.java). 


You can also get a builder object from newBuilder() method to configure the instance with code:

#### Main.java

```java
import org.twitter4j.*;
public class Main {
  public static void main(String... args){
    var x = X.newBuilder()
      .oAuthConsumer("consumer key", "consumer secret")
      .oAuthAccessToken("access token", "access token secret")
      .build();
    x.v1().tweets().updateStatus("Hello X API!");
  }
}
```

## License
Apache License Version 2.0

![Java CI with Gradle](https://github.com/X4J/X4J/workflows/Java%20CI%20with%20Gradle/badge.svg)
