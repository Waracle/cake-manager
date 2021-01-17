package com.waracle.cakemgr;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Unique constraint violation")
public class ConflictException extends RuntimeException {
}