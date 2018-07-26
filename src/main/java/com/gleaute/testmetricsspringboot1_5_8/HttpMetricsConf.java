package com.gleaute.testmetricsspringboot1_5_8;

import org.springframework.boot.actuate.autoconfigure.ExportMetricReader;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.actuate.metrics.buffer.BufferMetricReader;
import org.springframework.boot.actuate.metrics.buffer.CounterBuffers;
import org.springframework.boot.actuate.metrics.buffer.GaugeBuffers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpMetricsConf {

	@Bean
	public CounterBuffers counterBuffers() {
		return new CounterBuffers();
	}

	@Bean
	@ExportMetricReader
	public BufferMetricReader actuatorMetricReader(CounterBuffers counters) {
		return new BufferMetricReader(counters, new GaugeBuffers());
	}

	@Bean
	public MetricsService metricsService(CounterBuffers writer) {
		return new MetricsService(writer);
	}

	class MetricsService implements GaugeService, CounterService {

		private final CounterBuffers buffers;

		public MetricsService(CounterBuffers buffers) {
			this.buffers = buffers;
		}

		@Override
		public void increment(String metricName) {
			this.buffers.increment("http." + metricName, 1L);
		}

		@Override
		public void decrement(String metricName) {
			// nothing
		}

		@Override
		public void reset(String metricName) {
			// nothing
		}

		@Override
		public void submit(String metricName, double value) {
			this.buffers.increment("http." + metricName, (long) value);
		}
	}
}