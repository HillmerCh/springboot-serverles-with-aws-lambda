# Library SpringBoot - AWS Lambdas Functions

This repository uses Java, SpringBoot with AWS Lambdas Functions 


* An AWS Lambda Function is required to run the test/app

* To use a Lambda Function is necessary to update the AWS properties in the files `src/main/resources/application.properties` and `src/main/resources/application-dev.properties` 

        #AWS Keys
        amazon.aws.accesskey=<KEY>
        amazon.aws.secretkey=<SECRET_KEY>
        amazon.aws.region=<REGION>
        amazon.aws.amazonAWSFunctionARN=<FUNCTION_RESOURCE_NAME>

* To test execute `./mvnw clean test`

* To run the app execute `./mvnw spring-boot:run`

* Rest endpoints 

    * **GET:** http://localhost:8080/api/notificacions/{name}/{phone}/{message}: Invoke the AWS Lambda Function
    
## The code

In this repository to use AWS Lambdas in Java, it is necessary to add some dependencies, create 
a Lambda Client class

### Dependencies (pom.xml file)
  
    com.amazonaws::aws-java-sdk-s3
    software.amazon.awssdk:lambda

### The AWS Lambda Client class (org.hillmerch.library.service.NotificationClient)

A LambdaClient is created using AWS Account keys

    	LambdaClient awsLambda = LambdaClient.builder()
        				.credentialsProvider( StaticCredentialsProvider.create( AwsBasicCredentials.create( amazonAWSAccessKey, amazonAWSSecretKey ) ) )
        				.region( region ).build();

Then, the payload with the parameters is created

    SdkBytes payload = SdkBytes.fromUtf8String(
				"{ \"message\": " +
						"{\n" +
						" \"name\": \"" + name + "\",\n" +
						" \"phone\": \"" + phone + "\",\n" +
						" \"message\": \"" + message + "\"\n" +
						"}"
						+ "}" );

Then, an InvokeRequest using the function name, the parameters are send with the method payload    				
	
		InvokeRequest request = InvokeRequest.builder()
				.functionName( amazonAWSFuntionARN )
				.payload( payload )
				.build();
Finally, the  Lambda function is invoked

		InvokeResponse res = awsLambda.invoke( request );
        
        
### The Lambda Function 

This example uses a Function created with Node, in this case the function return a string 

    exports.handler = async (event) => {
        const response = {
            statusCode: 200,
            body: JSON.stringify('Message sent: ' + event.message.name + '  ' + event.message.phone + ' ' + event.message.message),
        };
        return response;
    };
