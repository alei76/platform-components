package com.jyz.study.jdk.ref;

public class EmployeeCacheTest {

	public static void main(String[] args) {
		EmployeeCache cache = EmployeeCache.getInstance();
		Employee e1 = cache.getEmployee("1");
		Employee e2 = cache.getEmployee("1");
		cache.clearCache();
		Employee e3 = cache.getEmployee("1");
	}
}
