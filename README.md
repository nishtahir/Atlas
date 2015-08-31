# Atlas Text

**Atlas Text** is a decent text editor application with syntax highlighting.

![atlas_screenshot_1.png](https://bitbucket.org/repo/d5b8M5/images/3113262366-atlas_screenshot_1.png)

### Why yet another text editor?

* It was originally a learning project to work with SWT. I thought open sourcing it was a good idea.

### Dependencies

* Eclipse SWT
* ANTLR 4.5
* JUnit 4

### Tools

* Apache Maven

### Setup

1. Import the project into your favorite IDE.

2. Run the following command in the root of the project (the folder with *pom.xml*)

   ``` mvn generate-sources ```
   
   This will generate files required to build the project

3. (MacOSX only) In your run configuration add the following VM option

   ``` -XstartOnFirstThread ```

### Contribution guidelines

* All contributions are welcome
* Unit tests are appreciated
* Report bugs using the issue tracker.
* Add feature requests using the issue tracker

#### Testing conventions

* _Ideally_ every Foo.java should have a corresponding FooTest.java class
* Methods in FooTest.java should follow the following convention
    
```java
    public void methodToTest_TestCondition_ExpectedResult() { ... }
```
### TODO

* Auto code formatting
* User preferences and Settings menu

### LICENSE

Copyright 2015 Nish Tahir

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
