# Nurmail

Nurmail is a Java library that makes it easy to define and use HTML email templates.
It provides a simple API and mustache-like templating syntax. Email templates are defined in HTML,
where title part is automatically parsed as subject and the rest as body.

## Usage

First, create an HTML template for your email like so:

resources/welcome.html:

```html
<title>{{ name }}, Welcome!</title>

<p>Dear {{ name }}</p>,

<p>It's nice to see you on our platform!</p>

<p>
    Best regrads,</br>
    {{ company }}
</p>
```

Then, you can build it like so. The placeholders will be replaced with the actual values.

```java
var template = Nurmail
       .fromResource("welcome.html")
       .with("name", "John Doe")
       .with("company", "Acme Inc.")
       .build();

template.subject(); // John Doe, Welcome!
template.body(); // <...>
```

## Installation

Gradle:

```groovy
implementation 'az.nuran:nurmail:0.0.1'
```

Maven:

```xml
<dependency>
    <groupId>az.nuran</groupId>
    <artifactId>nurmail</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Contributions

Anyone is welcome to extend or improve this library.

## License

This project is licensed under the Apache 2.0 License.