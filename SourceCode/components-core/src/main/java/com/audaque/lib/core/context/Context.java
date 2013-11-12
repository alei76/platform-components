/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.audaque.lib.core.context;

/**
 *
 * @author Administrator
 */
public abstract class Context {

    static Integer authenticatedUserId = null;

    public static void setAuthenticatedUserId(Integer authenticatedUser) {
        authenticatedUserId = authenticatedUser;
    }

    public static Integer getAuthenticatedUserId() {
        return authenticatedUserId;
    }
}
