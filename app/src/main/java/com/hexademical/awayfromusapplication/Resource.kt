package com.hexademical.awayfromusapplication

class Resource(val name: String, val dayLeft: Int, val ipSynced: String) {

    fun isIPSynced(): Boolean {
        return ipSynced != null;
    }

    fun isPermanently(): Boolean {
        return dayLeft == -1;
    }

}