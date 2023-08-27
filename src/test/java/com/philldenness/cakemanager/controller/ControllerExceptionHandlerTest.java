package com.philldenness.cakemanager.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.philldenness.cakemanager.metrics.CounterManager;
import com.philldenness.cakemanager.metrics.CounterName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

	@InjectMocks
	private ControllerExceptionHandler controllerExceptionHandler;

	@Mock
	private CounterManager counterManager;

	@Test
	void shouldIncrementBadRequestCounter() {
		controllerExceptionHandler.handleIllegalArgumentException();

		verify(counterManager).increment(CounterName.BAD_REQUEST);
	}

	@Test
	void shouldIncrementUnknownErrorCounter() {
		controllerExceptionHandler.handleException(mock(Exception.class));

		verify(counterManager).increment(CounterName.UNKNOWN_ERROR);
	}
}