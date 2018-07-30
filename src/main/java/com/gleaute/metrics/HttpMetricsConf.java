package com.gleaute.metrics;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.actuate.autoconfigure.ExportMetricReader;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.actuate.metrics.buffer.BufferCounterService;
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
	public CounterService counterService(CounterBuffers buffers) {
		return new BufferCounterService(buffers);
	}

	@Bean
	public GaugeService gaugeService(CounterBuffers buffers) {
		return new MetricsService(buffers);
	}

	class MetricsService implements GaugeService {

		private final ConcurrentHashMap<String, String> names = new ConcurrentHashMap<String, String>();

		private final CounterBuffers buffers;

		public MetricsService(CounterBuffers buffers) {
			this.buffers = buffers;
		}

		@Override
		public void submit(String metricName, double value) {
			this.buffers.increment(wrap(metricName), (long) value);
		}

		private String wrap(String metricName) {
			String cached = this.names.get(metricName);
			if (cached != null) {
				return cached;
			}
			String name = "total." + metricName;
			this.names.put(metricName, name);
			return name;
		}
	}
}