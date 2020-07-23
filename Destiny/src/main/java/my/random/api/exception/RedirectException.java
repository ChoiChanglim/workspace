package my.random.api.exception;

import my.random.api.exception.ExceptionEnum.RedirectType;

public class RedirectException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public static final RedirectException LOGIN_EXCEPTION = new RedirectException(RedirectType.LOGIN_EXCEPTION);
	public static final RedirectException NOT_FOUND_JSP_EXCEPTION = new RedirectException(RedirectType.NOT_FOUND_JSP_EXCEPTION);
	public static final RedirectException NOT_SUPPORT_LANGUAGE = new RedirectException(RedirectType.NOT_SUPPORT_LANGUAGE);
	public static final RedirectException UNACCEPTABLE_PAGE_EXCEPTION = new RedirectException(RedirectType.UNACCEPTABLE_PAGE_EXCEPTION);
	public static final RedirectException REDIRECT_LOGIN_PAGE = new RedirectException(RedirectType.REDIRECT_LOGIN_PAGE);
	public static final RedirectException REDIRECT_KAKAO_STORY_SIGNIN = new RedirectException(RedirectType.REDIRECT_KAKAO_STORY_SIGNIN);
	
	//어드민
	public static final RedirectException ADMIN_NOT_FOUND_JSP_EXCEPTION = new RedirectException(RedirectType.ADMIN_NOT_FOUND_JSP_EXCEPTION);
	public static final RedirectException ADMIN_NO_PERMISSION_EXCEPTION = new RedirectException(RedirectType.ADMIN_NO_PERMISSION_EXCEPTION);
	public static final RedirectException ADMIN_LOGIN_EXCEPTION = new RedirectException(RedirectType.ADMIN_LOGIN_EXCEPTION);
	public static final RedirectException ADMIN_PW_CHANGE_EXCEPTION = new RedirectException(RedirectType.ADMIN_PW_CHANGE_EXCEPTION);
	public static final RedirectException ADMIN_WRONG_PARAMETER_EXCEPTION = new RedirectException(RedirectType.ADMIN_WRONG_PARAMETER_EXCEPTION);

	public static final RedirectException REDIRECT_LOGIN_PAGE_BY_NO_REQUIRED_PARAMETERS = new RedirectException(RedirectType.REDIRECT_LOGIN_PAGE_BY_NO_REQUIRED_PARAMETERS);
	
    private RedirectException(RedirectType redirectType) {
    	super(redirectType.getRedirect_uri());
    }
    
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
    
}
