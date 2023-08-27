package com.philldenness.cakemanager.metrics;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.Test;

class CounterManagerTest {

	private final Counter findAllCounter = mock(Counter.class);
	private final Counter findByIdCounter = mock(Counter.class);
	private final Counter saveCounter = mock(Counter.class);
	private final Counter updateCounter = mock(Counter.class);
	private final Counter deleteCounter = mock(Counter.class);
	private final Counter unknownErrorCounter = mock(Counter.class);
	private final Counter badRequestCounter = mock(Counter.class);

	private final CounterManager counterManager = new CounterManager(findAllCounter, findByIdCounter, saveCounter, updateCounter, deleteCounter, unknownErrorCounter, badRequestCounter);

	@Test
	void shouldCallCounterForFindAll() {
		counterManager.increment(CounterName.FIND_ALL_COUNTER);

		verify(findAllCounter).increment();
	}

	@Test
	void shouldCallCounterForFindById() {
		counterManager.increment(CounterName.FIND_BY_ID_COUNTER);

		verify(findByIdCounter).increment();
	}

	@Test
	void shouldCallCounterForSave() {
		counterManager.increment(CounterName.SAVE);

		verify(saveCounter).increment();
	}

	@Test
	void shouldCallCounterForUpdate() {
		counterManager.increment(CounterName.UPDATE);

		verify(updateCounter).increment();
	}

	@Test
	void shouldCallCounterForDelete() {
		counterManager.increment(CounterName.DELETE);

		verify(deleteCounter).increment();
	}

	@Test
	void shouldCallCounterForInternalServerError() {
		counterManager.increment(CounterName.UNKNOWN_ERROR);

		verify(unknownErrorCounter).increment();
	}

	@Test
	void shouldCallCounterForBadRequestError() {
		counterManager.increment(CounterName.BAD_REQUEST);

		verify(badRequestCounter).increment();
	}
}