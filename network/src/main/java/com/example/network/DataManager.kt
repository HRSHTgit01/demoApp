package com.example.network

interface DataManager {
    fun of(tag: String): DataManager
    fun putString(key: String, value: String)
    fun putInt(key: String, value: Int)
    fun putLong(key: String, value: Long)
    fun putBoolean(key: String, value: Boolean)
    fun putFloat(key: String, value: Float)
    fun getInt(key: String, defaultValue: Int): Int
    fun getString(key: String, defaultValue: String?): String?
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun getFloat(key: String, defaultValue: Float): Float
    fun getLong(key: String, defaultValue: Long): Long
    fun putData(key: String, value: Any)
    fun getData(key: String): Any?
    fun putData(key: Any, value: Any)
    fun getData(key: Any): Any?
    fun hasKey(key: Any): Boolean
    fun removeKey(key: String)
    fun removeAllKeys()
}