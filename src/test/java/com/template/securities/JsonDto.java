package com.template.securities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class JsonDto {

    @JsonProperty(value = "A")
    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "JsonDto{" +
                "a=" + a +
                '}';
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class A{



        @JsonProperty(value = "B-3")
        private List<C> b = new ArrayList<>();

        public List<C> getB() {
            return b;
        }

        public void setB(List<C> b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return "A{" +
                    "b=" + b +
                    '}';
        }
    }

    static class C {
        @JsonProperty(value = "C-1")
        private String c1;
        @JsonProperty(value = "C-2")
        private String c2;

        public String getC1() {
            return c1;
        }

        public void setC1(String c1) {
            this.c1 = c1;
        }

        public String getC2() {
            return c2;
        }

        public void setC2(String c2) {
            this.c2 = c2;
        }

        @Override
        public String toString() {
            return "C{" +
                    "c1='" + c1 + '\'' +
                    ", c2='" + c2 + '\'' +
                    '}';
        }
    }
}
