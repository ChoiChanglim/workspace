package my.random.service;

import my.random.api.constant.SupportMember.MemberDivEnum;
import my.random.api.util.StringUtil;
import my.random.bean.Member;
import my.random.bean.MemberKey;
import my.random.mapper.MemberMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberMapper memberMapper;

	@Override
	public Member getUser(String uid, MemberDivEnum memberDivEnum) {
		if(StringUtil.isNull(uid) || null == memberDivEnum){
			return null;
		}
		MemberKey memberKey = new MemberKey();
		memberKey.setUid(uid);
		memberKey.setUdiv(memberDivEnum.name());
		Member member = memberMapper.selectByPrimaryKey(memberKey);
		return member;
	}

	@Override
	public void insert(Member m) {
		memberMapper.insertSelective(m);
		
	}

	@Override
	public void platformNickUpdate(MemberDivEnum udiv, String uid, String nick) {
		Member m = new Member();
        m.setUid(uid);
        m.setUdiv(udiv.name());
        m.setUname(nick);
        memberMapper.updateByPrimaryKeySelective(m);
		
	}

}
