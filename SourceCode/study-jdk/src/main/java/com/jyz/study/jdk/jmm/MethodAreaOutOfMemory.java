package com.jyz.study.jdk.jmm;

import java.lang.reflect.Method;
 

/**
 * @Described：方法区溢出测试
 * 使用技术 CBlib
 * @VM args : -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class MethodAreaOutOfMemory {

    public static void main(String[] args) {
       while(true){
           Enhancer enhancer = new Enhancer();
           enhancer.setSuperclass(TestCase.class);
           enhancer.setUseCache(false);
           enhancer.setCallback(new MethodInterceptor() {
              @Override
              public Object intercept(Object arg0, Method arg1, Object[] arg2,
                     MethodProxy arg3) throws Throwable {
                  return arg3.invokeSuper(arg0, arg2);
              }
           });
           enhancer.create();
       }
    }
}

class TestCase{
    
}
