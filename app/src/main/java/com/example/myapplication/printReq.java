package com.example.myapplication;

import java.util.ArrayList;

public class printReq {

    public Header header;
    public Request request;

    public static class Command{
        public Printer printer;
    }

    public static class Header{
        public int length;
        public String hash;
        public String version;
    }

    public static class Printer{
        public String type;
        public ArrayList<PrintLine> printLines;
    }

    public static class PrintLine{
        public String type;
        public String style;
        public String content;
    }

    public static class Request{
        public Command command;
    }

}
