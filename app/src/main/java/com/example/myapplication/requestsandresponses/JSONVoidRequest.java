package com.example.myapplication.requestsandresponses;

import com.example.myapplication.tables.Order;
import com.google.gson.Gson;

public class JSONVoidRequest {

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
        public String invoice;
    }

    public static class Options{
        public String language;
        public String print;
    }

    public static class Request{
        public Financial financial;
    }

    public JSONVoidRequest(Order order) {
        this.header = new JSONVoidRequest.Header();
        this.request = new JSONVoidRequest.Request();
        this.request.financial = new JSONVoidRequest.Financial();
        this.request.financial.transaction = "void";
        this.request.financial.amounts = new JSONVoidRequest.Amounts();
        this.request.financial.id = new JSONVoidRequest.Id();

        this.request.financial.amounts.base = order.getPrice() + ".00";
        this.request.financial.amounts.currencyCode = "RSD";

        this.request.financial.options = new JSONVoidRequest.Options();
        this.request.financial.options.language = "sr";
        this.request.financial.options.print = "true";

        this.request.financial.id.invoice = order.getInvoice();

        String tempRequest = "\"request\":"+new Gson().toJson(this.request);
        String generatedSHA512 = HashUtils.performSHA512(tempRequest);

        this.header.version = "01";
        this.header.length = tempRequest.length();
        this.header.hash = generatedSHA512;
    }
}
