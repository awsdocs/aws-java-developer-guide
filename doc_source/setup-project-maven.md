# Use the SDK with Apache Maven<a name="setup-project-maven"></a>

You can use [Apache Maven](https://maven.apache.org/) to configure and build AWS SDK for Java projects, or to build the SDK itself\.

**Note**  
You must have Maven installed to use the guidance in this topic\. If it isn’t already installed, visit [http://maven\.apache\.org/](http://maven.apache.org/) to download and install it\.

## Create a new Maven package<a name="create-a-new-maven-package"></a>

To create a basic Maven package, open a terminal \(command\-line\) window and run:

```
mvn -B archetype:generate \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DgroupId=org.example.basicapp \
  -DartifactId=myapp
```

Replace *org\.example\.basicapp* with the full package namespace of your application, and *myapp* with the name of your project \(this will become the name of the directory for your project\)\.

By default, creates a project template for you using the [quickstart](http://maven.apache.org/archetypes/maven-archetype-quickstart/) archetype, which is a good starting place for many projects\. There are more archetypes available; visit the [Maven archetypes](https://maven.apache.org/archetypes/index.html) page for a list of archetypes packaged with \. You can choose a particular archetype to use by adding the `-DarchetypeArtifactId` argument to the `archetype:generate` command\. For example:

```
mvn archetype:generate \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DarchetypeArtifactId=maven-archetype-webapp \
  -DgroupId=org.example.webapp \
  -DartifactId=mywebapp
```

**Note**  
Much more information about creating and configuring projects is provided in the [Maven Getting Started Guide](https://maven.apache.org/guides/getting-started/)\.

## Configure the SDK as a Maven dependency<a name="configuring-maven"></a>

To use the AWS SDK for Java in your project, you’ll need to declare it as a dependency in your project’s `pom.xml` file\. Beginning with version 1\.9\.0, you can import [individual components](#configuring-maven-individual-components) or the [entire SDK](#configuring-maven-entire-sdk)\.

### Specifying individual SDK modules<a name="configuring-maven-individual-components"></a>

To select individual SDK modules, use the AWS SDK for Java bill of materials \(BOM\) for Maven, which will ensure that the modules you specify use the same version of the SDK and that they’re compatible with each other\.

To use the BOM, add a `<dependencyManagement>` section to your application’s `pom.xml` file, adding `aws-java-sdk-bom` as a dependency and specifying the version of the SDK you want to use:

```
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.amazonaws</groupId>
      <artifactId>aws-java-sdk-bom</artifactId>
      <version>1.11.1000</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

To view the latest version of the AWS SDK for Java BOM that is available on Maven Central, visit: [https://mvnrepository\.com/artifact/com\.amazonaws/aws\-java\-sdk\-bom](https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk-bom)\. You can also use this page to see which modules \(dependencies\) are managed by the BOM that you can include within the `<dependencies>` section of your project’s `pom.xml` file\.

You can now select individual modules from the SDK that you use in your application\. Because you already declared the SDK version in the BOM, you don’t need to specify the version number for each component\.

```
<dependencies>
  <dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-s3</artifactId>
  </dependency>
  <dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-dynamodb</artifactId>
  </dependency>
</dependencies>
```

You can also refer to the * AWS Code Sample Catalog * to learn what dependencies to use for a given AWS service\. Refer to the POM file under a specific service example\. For example, if you are interested in the dependencies for the AWS S3 service, see the [complete example](https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/java/example_code/s3/src/main/java/aws/example/s3/GetAcl.java) on GitHub\. \(Look at the pom under /java/example\_code/s3\)\.

### Importing all SDK modules<a name="configuring-maven-entire-sdk"></a>

If you would like to pull in the *entire* SDK as a dependency, don’t use the BOM method, but simply declare it in your `pom.xml` like this:

```
<dependencies>
  <dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk</artifactId>
    <version>1.11.1000</version>
  </dependency>
</dependencies>
```

## Build your project<a name="build-your-project"></a>

Once you have your project set up, you can build it using Maven’s `package` command:

```
mvn package
```

This will create your `0jar` file in the `target` directory\.

## Build the SDK with Maven<a name="building-with-maven"></a>

You can use Apache Maven to build the SDK from source\. To do so, [download the SDK code from GitHub](https://github.com/aws/aws-sdk-java), unpack it locally, and then execute the following Maven command:

```
mvn clean install
```