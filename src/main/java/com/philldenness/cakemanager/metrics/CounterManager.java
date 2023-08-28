package com.philldenness.cakemanager.metrics;

import java.util.HashMap;
import java.util.Map;

import io.micrometer.core.instrument.Counter;
import org.springframework.stereotype.Component;

@Component
public class CounterManager {

	private final Map<CounterName, Counter> counterNameCounterMap;

	public CounterManager(Counter findAllCounter,
						  Counter findByIdCounter,
						  Counter saveCounter,
						  Counter updateCounter,
						  Counter deleteCounter,
						  Counter unknownErrorCounter,
						  Counter badRequestCounter) {
		counterNameCounterMap = new HashMap<>();
		counterNameCounterMap.put(CounterName.FIND_ALL_COUNTER, findAllCounter);
		counterNameCounterMap.put(CounterName.FIND_BY_ID_COUNTER, findByIdCounter);
		counterNameCounterMap.put(CounterName.SAVE, saveCounter);
		counterNameCounterMap.put(CounterName.UPDATE, updateCounter);
		counterNameCounterMap.put(CounterName.DELETE, deleteCounter);
		counterNameCounterMap.put(CounterName.UNKNOWN_ERROR, unknownErrorCounter);
		counterNameCounterMap.put(CounterName.BAD_REQUEST, badRequestCounter);
	}

	public void increment(CounterName counterName) {
		counterNameCounterMap.get(counterName).increment();
	}
}
