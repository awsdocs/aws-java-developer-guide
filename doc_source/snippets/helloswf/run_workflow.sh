# Examples without the -cp option
export CLASSPATH='target/helloswf-1.0.jar:/path/to/sdk/libs/*'
java example.swf.hello.HelloTypes
java example.swf.hello.ActivityWorker &
java example.swf.hello.WorkflowWorker &
java example.swf.hello.WorkflowStarter

# An example of running with input data.
java example.swf.hello.WorkflowStarter "Thelonious"

# Examples with the -cp option
java -cp target/helloswf-1.0.jar:/path/to/sdk/libs/* example.swf.hello.HelloTypes

