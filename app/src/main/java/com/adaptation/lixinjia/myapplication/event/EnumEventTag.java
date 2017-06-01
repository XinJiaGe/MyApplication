package com.adaptation.lixinjia.myapplication.event;

public enum EnumEventTag {

    /**
     * 需要更新
     */
    UPDATE;


    public static EnumEventTag valueOf(int index) {
        if (index >= 0 && index < values().length) {
            return values()[index];
        } else {
            return null;
        }
    }
}
