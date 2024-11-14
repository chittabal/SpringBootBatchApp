package com.chitta.qbs.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.chitta.qbs.entity.Customer;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	public BatchConfig() {

	}

	@Bean
	public FlatFileItemReader<Customer> reader() {
		return new FlatFileItemReaderBuilder<Customer>().name("customerItemReader")
				.resource(new ClassPathResource("data.txt")).delimited()
				.names("customerId", "accountNumber", "description").targetType(Customer.class).build();
	}

	@Bean
	public Job importCustomerJob(Step step) {
		return new JobBuilder("importCustomerJob")
				.start(step).build();
	}
	@Bean
	public Step step(FlatFileItemReader<Customer> reader, ItemWriter<Customer> writer) {
		return new StepBuilder("step")
				.<Customer, Customer>chunk(10).reader(reader).writer(writer).build();
	}
}
