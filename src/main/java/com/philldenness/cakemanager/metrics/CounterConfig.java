package com.philldenness.cakemanager.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CounterConfig {

	@Bean
	public Counter findAllCounter(MeterRegistry registry) {
		return Counter.builder(CounterName.FIND_ALL_COUNTER.toString())
				.description("Number of find all requests")
				.register(registry);
	}

	@Bean
	public Counter findByIdCounter(MeterRegistry registry) {
		return Counter.builder(CounterName.FIND_BY_ID_COUNTER.toString())
				.description("Number of find by id requests")
				.register(registry);
	}

	@Bean
	public Counter saveCounter(MeterRegistry registry) {
		return Counter.builder(CounterName.SAVE.toString())
				.description("Number of save requests")
				.register(registry);
	}

	@Bean
	public Counter updateCounter(MeterRegistry registry) {
		return Counter.builder(CounterName.UPDATE.toString())
				.description("Number of update requests")
				.register(registry);
	}

	@Bean
	public Counter deleteCounter(MeterRegistry registry) {
		return Counter.builder(CounterName.DELETE.toString())
				.description("Number of delete requests")
				.register(registry);
	}

	@Bean
	public Counter unknownErrorCounter(MeterRegistry registry) {
		return Counter.builder(CounterName.UNKNOWN_ERROR.toString())
				.description("Number of unknown errors")
				.register(registry);
	}

	@Bean
	public Counter badRequestCounter(MeterRegistry registry) {
		return Counter.builder(CounterName.BAD_REQUEST.toString())
				.description("Number of bad request errors")
				.register(registry);
	}
}
