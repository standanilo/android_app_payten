package com.example.myapplication;

public class jsonRequest {

    public class Amounts{
        public String base;
        public String currencyCode;
    }

    public class Financial{
        public String transaction;
        public Id id;
        public Amounts amounts;
        public Options options;
    }

    public class Header{
        public String length;
        public String hash;
        public String version;
    }

    public class Id{
        public String ecr;
    }

    public class Options{
        public String language;
        public String print;
    }

    public class Request{
        public Financial financial;
    }

    public class Root{
        public Header header;
        public Request request;
    }


}
