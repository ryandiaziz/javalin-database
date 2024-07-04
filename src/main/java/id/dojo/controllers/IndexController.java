package id.dojo.controllers;

import io.javalin.http.Handler;

public class IndexController {
    public static Handler index = context -> context.result("DVD Rental API");
}
