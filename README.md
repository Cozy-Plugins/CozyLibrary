A library used to help create spigot plugins.

**Maven**
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.Cozy-Plugins</groupId>
    <artifactId>CozyLibrary</artifactId>
    <version>Tag</version>
</dependency>
```

**Gradle**
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
```gradle
dependencies {
    implementation 'com.github.Cozy-Plugins:CozyLibrary:-SNAPSHOT'
}
```