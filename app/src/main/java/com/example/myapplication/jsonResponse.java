package com.example.myapplication;

public class jsonResponse {
    // import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
    public Header header;
    public Response response;
    public static class Card{
        public String bin;
        public String cardInterface;
        public String name;
        public String pan;
    }

    public static class Cryptogram1{
        public String type;
        public String value;
    }

    public static class Cvm{
    }

    public static class Emv{
        public String aid;
        public String aidLabel;
        public Cryptogram1 cryptogram1;
        public String ctq;
        public String ttq;
        public String tvr;
    }

    public static class Financial{
        public Amounts amounts;
        public String dateTime;
        public Emv emv;
        public Flags flags;
        public Id id;
        public Result result;
        public String transaction;
    }

    public static class Flags{
        public Cvm cvm;
    }

    public static class Header{
        public String hash;
        public int length;
        public String version;
    }

    public static class Id{
        public String authorization;
        public String batch;
        public Card card;
        public String ecr;
        public String invoice;
        public String merchant;
        public String reference;
        public String terminal;
    }

    public static class Response{
        public Financial financial;
    }

    public static class Result{
        public String code;
        public String message;
    }

    public static class Amounts{
        public String base;
        public String currencyCode;
        public String total;
    }

}
