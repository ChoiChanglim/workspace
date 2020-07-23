package my.random.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.random.api.constant.SupportMember.MemberDivEnum;
import my.random.bean.Member;

public interface LoginService {
	void signin(MemberDivEnum memberDivEnum, HttpServletResponse res, String uid, boolean isAdmin);
	boolean isSignIn(HashMap<String, String> cookieMap);
	boolean isPletformMemberValid(MemberDivEnum memberDivEnum, String uid);
	Member getPletformMember(MemberDivEnum memberDivEnum, String uid);
	
}
