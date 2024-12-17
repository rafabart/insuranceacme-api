package br.com.insuranceacme.insuranceacme_api.tool;

import java.util.concurrent.atomic.AtomicLong;

public class NumericIdGenerator {
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    public static Long generateUniqueId() {
        return counter.incrementAndGet();
    }
}
