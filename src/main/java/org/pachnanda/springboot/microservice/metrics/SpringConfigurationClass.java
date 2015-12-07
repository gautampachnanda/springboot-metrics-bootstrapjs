package org.pachnanda.springboot.microservice.metrics;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@Configuration
@EnableMetrics
public class SpringConfigurationClass extends MetricsConfigurerAdapter {

	@Override
	public void configureReporters(MetricRegistry metricRegistry) {
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		// registerReporter allows the MetricsConfigurerAdapter to
		// shut down the reporter when the Spring context is closed
		registerReporter(ConsoleReporter.forRegistry(metricRegistry).build())
				.start(1, TimeUnit.MINUTES);

		// MetricRegistry metricRegistry = new MetricRegistry();
		final Graphite graphite = new Graphite("graphite", 2003);
		final GraphiteReporter graphiteReporter = GraphiteReporter
				.forRegistry(metricRegistry)
				.prefixedWith("org.pachnanda.springbootbootstrapjs")
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build(graphite);
		graphiteReporter.start(20, TimeUnit.SECONDS);
		graphiteReporter.report();
		System.out.println("Starting reporters");
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	}

}