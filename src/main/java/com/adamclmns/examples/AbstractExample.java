package com.adamclmns.examples;

interface AbstractExample {

    // MongoDB Credentials are managed here -
    String MONGO_HOST = "localhost";
    String MONGO_PORT = "";
    String MONGO_USER = "";
    String MONGO_PASS = "";


    void run();

}
