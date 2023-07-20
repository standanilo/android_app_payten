package com.example.myapplication;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class JSONPrintRequest {

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

    public JSONPrintRequest(ArrayList<JSONPrintRequest.PrintLine> lines) {
        this.header = new JSONPrintRequest.Header();
        this.request = new JSONPrintRequest.Request();
        this.request.command = new JSONPrintRequest.Command();
        this.request.command.printer = new JSONPrintRequest.Printer();
        this.request.command.printer.type = "JSON";
        this.request.command.printer.printLines = lines;

        String tempRequest = "\"request\":"+new GsonBuilder().disableHtmlEscaping().create().toJson(this.request);
        String generatedSHA512 = HashUtils.performSHA512(tempRequest);

        this.header.version = "01";
        this.header.length = tempRequest.length();
        this.header.hash = generatedSHA512;
    }
}
