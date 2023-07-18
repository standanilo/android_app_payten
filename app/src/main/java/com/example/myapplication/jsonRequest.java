package com.example.myapplication;

public class jsonRequest {

    public Header header;
    public Request request;

    public static class Amounts{
        public String base;
        public String currencyCode;
    }

    public static class Financial{
        public String transaction;
        public Id id;
        public Amounts amounts;
        public Options options;
    }

    public static class Header{
        public int length;
        public String hash;
        public String version;
    }

    public static class Id{
        public String ecr;
    }

    public static class Options{
        public String language;
        public String print;
    }

    public static class Request{
        public Financial financial;
    }


}
