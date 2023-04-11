# Setting the JVM TTL for DNS Name Lookups<a name="java-dg-jvm-ttl"></a>

The Java virtual machine \(JVM\) caches DNS name lookups\. When the JVM resolves a hostname to an IP address, it caches the IP address for a specified period of time, known as the *time\-to\-live* \(TTL\)\.

Because AWS resources use DNS name entries that occasionally change, we recommend that you configure your JVM with a TTL value of no more than 60 seconds\. This ensures that when a resource’s IP address changes, your application will be able to receive and use the resource’s new IP address by requerying the DNS\.

On some Java configurations, the JVM default TTL is set so that it will *never* refresh DNS entries until the JVM is restarted\. Thus, if the IP address for an AWS resource changes while your application is still running, it won’t be able to use that resource until you *manually restart* the JVM and the cached IP information is refreshed\. In this case, it’s crucial to set the JVM’s TTL so that it will periodically refresh its cached IP information\.

**Note**  
The default TTL can vary according to the version of your JVM and whether a [security manager](http://docs.oracle.com/javase/tutorial/essential/environment/security.html) is installed\. Many JVMs provide a default TTL less than 60 seconds\. If you’re using such a JVM and not using a security manager, you can ignore the remainder of this topic\.

## How to Set the JVM TTL<a name="how-to-set-the-jvm-ttl"></a>

To modify the JVM’s TTL, set the [networkaddress\.cache\.ttl](http://docs.oracle.com/javase/7/docs/technotes/guides/net/properties.html) property value\. Use one of the following methods, depending on your needs:
+  **globally, for all applications that use the JVM**\. Set `networkaddress.cache.ttl` in the `$JAVA_HOME/jre/lib/security/java.security` file for Java 8 or `$JAVA_HOME/conf/security/java.security` file for Java 11 or higher:

  ```
  networkaddress.cache.ttl=60
  ```
+  **for your application only**, set `networkaddress.cache.ttl` in your application’s initialization code:

  ```
  java.security.Security.setProperty("networkaddress.cache.ttl" , "60");
  ```