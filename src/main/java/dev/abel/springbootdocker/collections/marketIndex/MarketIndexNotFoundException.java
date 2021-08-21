package dev.abel.springbootdocker.collections.marketIndex;

public class MarketIndexNotFoundException extends RuntimeException{

    public MarketIndexNotFoundException(String msg) {
        super(msg);
    }
}