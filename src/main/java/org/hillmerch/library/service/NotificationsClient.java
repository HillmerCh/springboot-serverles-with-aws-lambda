package org.hillmerch.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

@Service
public class NotificationsClient {

	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey;

	@Value("${amazon.aws.region}")
	private String amazonAWSRegion;

	@Value("${amazon.aws.amazonAWSFunctionARN}")
	private String amazonAWSFunctionARN;

	public String sendNotification(String name, String phone, String message) {

		Region region = Region.of( amazonAWSRegion );

		//Creating de AWS Lambda Client
		LambdaClient awsLambda = LambdaClient.builder()
				.credentialsProvider( StaticCredentialsProvider.create( AwsBasicCredentials.create( amazonAWSAccessKey, amazonAWSSecretKey ) ) )
				.region( region ).build();

		//Need a SdkBytes instance for the payload
		SdkBytes payload = SdkBytes.fromUtf8String(
				"{ \"message\": " +
						"{\n" +
						" \"name\": \"" + name + "\",\n" +
						" \"phone\": \"" + phone + "\",\n" +
						" \"message\": \"" + message + "\"\n" +
						"}"
						+ "}" );

		//Setup an InvokeRequest
		InvokeRequest request = InvokeRequest.builder()
				.functionName( amazonAWSFunctionARN )
				.payload( payload )
				.build();

		//Invoke the Lambda function
		InvokeResponse res = awsLambda.invoke( request );

		//Get the response
		String value = res.payload().asUtf8String();

		return value;

	}
}
