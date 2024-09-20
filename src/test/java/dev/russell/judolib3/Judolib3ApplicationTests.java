package dev.russell.judolib3;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
class Judolib3ApplicationTests {
	@Autowired
	private MockMvc mockServlet;

	@Container
	private static final LocalStackContainer mockS3 =
			new LocalStackContainer(
					DockerImageName.parse("localstack/localstack:3.5.0")
			).withServices(S3);

	@DynamicPropertySource
	public static void registerEndpoint(DynamicPropertyRegistry registry) {
		registry.add("localstack.s3.endpoint", mockS3::getEndpoint);
	}

	/*
		This should probably move to an end to end testing file
	 */
	@BeforeAll
	public static void createTestBucket(@Autowired S3Client client) {
		assert(mockS3.isRunning());

		var createBucketRequest = CreateBucketRequest.builder()
				.bucket("judo-blob-3")
				.build();

		var response = client.createBucket(createBucketRequest);
		assert(!response.location().isEmpty());
	}

	@AfterAll
	public static void destroyTestBucket(@Autowired S3Client client) {
		var deleteBucketRequest = DeleteBucketRequest.builder()
				.bucket("judo-blob-3")
				.build();

		var response = client.deleteBucket(deleteBucketRequest);
	}

	@Test
	void contextLoads() {
	}

	@Test
	void checkIndex() throws Exception {
		mockServlet.perform(get(URI.create("/v1/")))
				.andExpect(status().isOk());
	}

	@Test
	void getVideo() throws Exception {
		var response = mockServlet
				.perform(get(
						URI.create("/v1/watch/seoi-nage"))
				)
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void s3VideoList() throws Exception {
		/*
			I think what we need is different configuration files or test files
			for integration, unit, end to end etc that instantiate different components
			and allow for specific testing...

			If you do hard core end to end stuff, you could use test container for
			mongo, too
		 */

	}
}
