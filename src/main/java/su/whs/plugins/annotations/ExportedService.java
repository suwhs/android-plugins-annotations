package su.whs.plugins.annotations;

/**
 * Created by igor n. boulliev on 14.08.15.
 */

/**
 * used with @ServiceMethod annotation
 *
 * for example
 *
 * package com.example;
 * @ServiceMethod
 * public class TestService {
 *     public TestService(Context context) // required constructor
 *
 *     @ServiceMethod
 *     public void testMethodA(int arg0, int arg2);
 *     @ServiceMethod
 *     public String testMethodB();
 * }
 *
 * produces com.example.ITestService aidl, com.example.ITestService.java (if clientOnly attribute not set)
 * TestServiceExport (extends from adnroid.os.Service) code, that wraps
 *
 */
public @interface ExportedService {
}
