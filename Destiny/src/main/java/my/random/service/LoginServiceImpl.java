package my.random.service;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpServletResponse;

import my.random.api.constant.SessionScopeBean;
import my.random.api.constant.SupportMember.MemberDivEnum;
import my.random.api.exception.RedirectException;
import my.random.api.util.AesCipher;
import my.random.api.util.RequestUtil;
import my.random.api.util.StringUtil;
import my.random.bean.Member;
import my.random.bean.MemberKey;
import my.random.mapper.MemberMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{
	private static Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);
	public static final String SIGNED_UID_COOKIE_NAME = "ylid";
	public static final String SIGNED_UDIV_COOKIE_NAME = "yldiv";
	public static final String SIGNED_REFERER_COOKIE_NAME = "_pref";
	public static final String SIGNED_COOKIE_CIPHER_KEY = "Loveyourneighbors";
	
	@Autowired
	MemberMapper memberMapper;
	
	@Autowired
	MemberService memberService;
	
	@Inject
    Provider<SessionScopeBean> sessionScopebeanFactory;
	
	public void signin(MemberDivEnum memberDivEnum, HttpServletResponse res, String uid, boolean isAdmin) {
		AesCipher cipher  = new AesCipher(SIGNED_COOKIE_CIPHER_KEY);
		RequestUtil.setCookie(res, SIGNED_UID_COOKIE_NAME, cipher.encrypt(uid), 1);
		RequestUtil.setCookie(res, SIGNED_UDIV_COOKIE_NAME, cipher.encrypt(memberDivEnum.name()), 1);
		
		//세션에도 아이디/구분 세팅
		SessionScopeBean sessionBean = this.sessionScopebeanFactory.get();
		sessionBean.setUid(uid);
		sessionBean.setUdiv(memberDivEnum.name());
		
		Member member = new Member();
		member.setUid(uid);
		member.setUdiv(memberDivEnum.name());
		if(isAdmin == false){
			member.setRecentLoginDate(new Date());
			memberMapper.updateByPrimaryKeySelective(member);
		}
		
	}
	
	/**
	 * 로그인 여부 확인
	 */
	public boolean isSignIn(HashMap<String, String> cookieMap) {
		//uid 확인
		if(cookieMap.containsKey(SIGNED_UID_COOKIE_NAME)== false){
			LOG.debug("아이디가 쿠키에 없음.");
			return false;
		}
		if(StringUtil.nullToBlank(cookieMap.get(SIGNED_UID_COOKIE_NAME)).trim().equals("")){
			LOG.debug("아이디 쿠키가 빈값임.");
			return false;
		}
		AesCipher cipher  = new AesCipher(SIGNED_COOKIE_CIPHER_KEY);
		String uid = cipher.decrypt(cookieMap.get(SIGNED_UID_COOKIE_NAME));
		
		//udiv확인
		if(cookieMap.containsKey(SIGNED_UDIV_COOKIE_NAME)== false){
			LOG.debug("회원 구분값이 쿠키에 없음.");
			return false;
		}
		if(StringUtil.nullToBlank(cookieMap.get(SIGNED_UDIV_COOKIE_NAME)).trim().equals("")){
			LOG.debug("회원 구분값이 빈값임.");
			return false;
		}
		String udiv = cipher.decrypt(cookieMap.get(SIGNED_UDIV_COOKIE_NAME));
		//DB에서도 확인
		MemberDivEnum memberDivEnum = MemberDivEnum.GetMemberDivEnum(udiv);
		if(null == memberDivEnum){
			LOG.debug("회원구분값 쿠키가 잘못되었다.");
			return false;
		}
		Member member = memberService.getUser(uid, memberDivEnum); 
		if(null == member){
			LOG.debug("쿠키의 아이디로 조회해보니 없는 아이디임.");
			return false;
		}
		
		return true;
	}
	
	
	public boolean isPletformMemberValid(MemberDivEnum memberDivEnum, String uid) {
		MemberKey memberKey = new MemberKey();
		memberKey.setUdiv(memberDivEnum.name());
		memberKey.setUid(uid);
		Member member = memberMapper.selectByPrimaryKey(memberKey);
		if(null == member){
			LOG.debug(uid+": 없는 "+memberDivEnum.getMemberDiv());
			return false;
		}
		return true;
	}
	
	public Member getPletformMember(MemberDivEnum memberDivEnum, String uid) {
		MemberKey memberKey = new MemberKey();
		memberKey.setUdiv(memberDivEnum.name());
		memberKey.setUid(uid);
		Member member = memberMapper.selectByPrimaryKey(memberKey);
		return member;
	}
	
	
	
}
