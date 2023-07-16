package com.example.myapplication;

public class jsonResponse {
    
    public class Card{
        public String bin;
        public String cardInterface;
        public String name;
        public String pan;
    }

    public class Cryptogram1{
        public String type;
        public String value;
    }

    public class Cryptogram2{
        public String type;
        public String value;
    }

    public class Cvm{
        public String pinOnline;
    }

    public class Emv{
        public String aid;
        public String aidLabel;
        public Cryptogram1 cryptogram1;
        public Cryptogram2 cryptogram2;
        public String tvr;
    }

    public class Financial{
        public String dateTime;
        public Emv emv;
        public Flags flags;
        public Id id;
        public Result result;
    }

    public class Flags{
        public Cvm cvm;
    }

    public class Header{
        public String hash;
        public int length;
        public String version;
    }

    public class Id{
        public String authorization;
        public String batch;
        public Card card;
        public String invoice;
        public String merchant;
        public String reference;
        public String terminal;
    }

    public class Response{
        public Financial financial;
    }

    public class Result{
        public String code;
        public String message;
    }

    public class Root{
        public Header header;
        public Response response;
    }


}
