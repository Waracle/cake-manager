package com.philldenness.cakemanager.metrics;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CounterName {
	FIND_BY_ID_COUNTER("find_by_id_counter"),
	SAVE("save_counter"),
	UPDATE("update_counter"),
	DELETE("delete_counter"),
	UNKNOWN_ERROR("unknown_error_counter"),
	BAD_REQUEST("bad_request_counter"),
	FIND_ALL_COUNTER("find_all_counter");

	private final String counterName;

	@Override
	public String toString() {
		return counterName;
	}
}
