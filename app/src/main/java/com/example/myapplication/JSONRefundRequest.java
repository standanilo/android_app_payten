package com.example.myapplication;

import com.google.gson.Gson;

public class JSONRefundRequest {

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

    public JSONRefundRequest(int price, String invoice) {
        this.header = new JSONRefundRequest.Header();
        this.request = new JSONRefundRequest.Request();
        this.request.financial = new JSONRefundRequest.Financial();
        this.request.financial.transaction = "refund";
        this.request.financial.amounts = new JSONRefundRequest.Amounts();
        this.request.financial.id = new JSONRefundRequest.Id();

        this.request.financial.amounts.base = price + ".00";
        this.request.financial.amounts.currencyCode = "RSD";

        this.request.financial.options = new JSONRefundRequest.Options();
        this.request.financial.options.language = "sr";
        this.request.financial.options.print = "true";

        this.request.financial.id.invoice = invoice;

        String tempRequest = "\"request\":"+new Gson().toJson(this.request);
        String generatedSHA512 = HashUtils.performSHA512(tempRequest);

        this.header.version = "01";
        this.header.length = tempRequest.length();
        this.header.hash = generatedSHA512;
    }
}
