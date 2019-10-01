<h1 align="center">
Taskeren's Internationalization
</h1>

<h4 align="center">
A lightweight, powerful I18n tool for Java.
</h4>

## Get T18n

You can get this project in Jitpack with Maven, Gradle and so on.

### Maven

1. Import the Jitpack source.
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

2. Add the denpendency.
```
	<dependency>
	    <groupId>com.github.nitu2003</groupId>
	    <artifactId>T18n</artifactId>
	    <version>1.5</version>
	</dependency>
```

### Gradle

1. Import the Jitpack source.
```
    repositories {
		...
		maven { url 'https://jitpack.io' }
	}
```

2. Add the dependency.
```
	dependencies {
	    implementation 'com.github.nitu2003:T18n:1.5'
	}
```

### For else
[![](https://jitpack.io/v/nitu2003/T18n.svg)](https://jitpack.io/#nitu2003/T18n)

## Dev with T18n

1. Get the Language Map.
```java
// from file
LanguageMapBuilder.fromFile(new File("res/i18n/en_us.lang"));
// from jar resource
LanguageMapBuilder.fromJarResource("res/i18n/en_us.lang");
// from online content (Deprecated)
LanguageMapBuilder.fromURL("https://example.com/i18n/en.lang");
```

2. Push it to the I18n.
```java
// reset the I18n and push it
T18n.set(theMap);
// add the new to the old
T18n.add(theMap);
```

3. Use it in the project
_en.lang_
```
HelloToUser=Hello %s.
```
_app.java_
```java
System.out.println(I18n.format("HelloToUser", "Taskeren")); // It should be "Hello Taskeren"
```