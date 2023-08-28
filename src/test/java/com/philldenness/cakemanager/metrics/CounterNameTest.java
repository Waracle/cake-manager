package com.philldenness.cakemanager.metrics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CounterNameTest {

	@Test
	void shouldHaveCounterNames() {
		assertEquals("find_all_counter", CounterName.FIND_ALL_COUNTER.toString());
		assertEquals("find_by_id_counter", CounterName.FIND_BY_ID_COUNTER.toString());
		assertEquals("save_counter", CounterName.SAVE.toString());
		assertEquals("update_counter", CounterName.UPDATE.toString());
		assertEquals("delete_counter", CounterName.DELETE.toString());
		assertEquals("unknown_error_counter", CounterName.UNKNOWN_ERROR.toString());
		assertEquals("bad_request_counter", CounterName.BAD_REQUEST.toString());
	}
}