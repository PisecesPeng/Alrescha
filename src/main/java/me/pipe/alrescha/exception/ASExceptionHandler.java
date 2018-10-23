package me.pipe.alrescha.exception;

import me.pipe.alrescha.util.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ASExceptionHandler {

/// 暂时不需要

//	@ExceptionHandler(ASException.class)
//	public R handleASException(ASException e) {
//		R r = new R();
//		r.put("code", e.getCode());
//		r.put("msg", e.getMessage());
//		return r;
//	}
//
//	@ExceptionHandler(Exception.class)
//	public R handleException(Exception e) {
//		return R.error();
//	}

}
