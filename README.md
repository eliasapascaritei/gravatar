## Gravatar

A tiny thread-safe Gravatar library for Scala.

### Usage

```
import gravatar._

// simple
Gravatar("john@doe.com").url

// with SSL
Gravatar("john@doe.com").withSSL.url

// with all properties
Gravatar("john@doe.com").withSSL.withSize(100).withRating(R).withDefault(Monster).andForceIt.url
```

More info [at Gravatar](http://gravatar.com/site/implement/images/)

### Use it as dependency

The library does not depend on any third party. It is built and deployed on maven central.

#### With SBT

```
libraryDependencies += "io.github.chrissom" %% "gravatar" % "1.1.0"
```

#### With Maven

```
<dependency>
  <groupId>io.github.chrissom</groupId>
  <artifactId>gravatar_2.11</artifactId>
  <version>1.1.0</version>
</dependency>
```

### About

Forked from [magott](https://github.com/magott/scravatar) but not maintained since 2012. So here is some fresh contributions!

### License

This software is licensed under the Apache 2 license.

Copyright 2012 Morten Andersen-Gott (http://www.andersen-gott.com).

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

