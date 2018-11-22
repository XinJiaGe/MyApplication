package com.lixinjia.myapplication.model;

import android.bluetooth.BluetoothDevice;

import java.util.UUID;

/**
 * 作者：李忻佳
 * 日期：2018/11/8
 * 说明：
 */

public class ParsedAdEntity {
    private BluetoothDevice device;
    private String localName;
    private UUID uuids;
    private short manufacturer;
    private byte flags;

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public UUID getUuids() {
        return uuids;
    }

    public void setUuids(UUID uuids) {
        this.uuids = uuids;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public short getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(short manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "ParsedAdEntity{" +
                "localName='" + localName + '\'' +
                ", device=" + device.getAddress() +
                ", uuids=" + uuids +
                ", manufacturer=" + manufacturer +
                ", flags=" + flags +
                '}';
    }
}
