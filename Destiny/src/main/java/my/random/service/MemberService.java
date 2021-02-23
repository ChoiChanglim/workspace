package my.random.service;

import java.util.List;

import my.random.api.constant.SupportMember.MemberDivEnum;
import my.random.bean.Member;
import my.random.bean.MemberKey;

public interface MemberService {

	Member getUser(String uid, MemberDivEnum memberDivEnum);

	void insert(Member m);

	void platformNickUpdate(MemberDivEnum udiv, String uid, String nick);

	boolean IsMember(MemberKey memberKey);

	
}
