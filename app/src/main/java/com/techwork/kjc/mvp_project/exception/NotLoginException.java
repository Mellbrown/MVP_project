package com.techwork.kjc.mvp_project.exception;

/**
 * Created by mlyg2 on 2018-06-11.
 */

public class NotLoginException extends Exception {
    public NotLoginException() {
        super("FirebaseAuth.getCurrentUser() is Null, 사용자가 로그인 되어 있지 않습니다.");
    }
}
