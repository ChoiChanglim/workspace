package my.random.service;

import my.random.api.constant.SupportMember.MemberDivEnum;
import my.random.bean.Member;

public interface MemberService {

	Member getUser(String uid, MemberDivEnum memberDivEnum);

	void insert(Member m);

	void platformNickUpdate(MemberDivEnum kakao, String kid, String nick);
	
}
