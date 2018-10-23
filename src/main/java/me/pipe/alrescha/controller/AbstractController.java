package me.pipe.alrescha.controller;

public abstract class AbstractController {

	private static String token;

	protected static void setToken(String token) {
		AbstractController.token = token;
	}

	protected static String getToken() {
		return AbstractController.token;
	}

}