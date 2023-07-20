package com.example.myapplication;

import com.google.gson.Gson;

public class JSONSaleRequest {

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

    public JSONSaleRequest(int totalCost) {

        this.header = new JSONSaleRequest.Header();
        this.request = new JSONSaleRequest.Request();
        this.request.financial = new JSONSaleRequest.Financial();
        this.request.financial.transaction = "sale";
        this.request.financial.amounts = new JSONSaleRequest.Amounts();
        this.request.financial.id = new JSONSaleRequest.Id();

        this.request.financial.amounts.base = totalCost + ".00";
        this.request.financial.amounts.currencyCode = "RSD";

        this.request.financial.options = new JSONSaleRequest.Options();
        this.request.financial.options.language = "sr";
        this.request.financial.options.print = "true";

//        this.request.financial.id.ecr = "000001";

        String tempRequest = "\"request\":"+new Gson().toJson(this.request);
        String generatedSHA512 = HashUtils.performSHA512(tempRequest);

        this.header.version = "01";
        this.header.length = tempRequest.length();
        this.header.hash = generatedSHA512;
    }
}
