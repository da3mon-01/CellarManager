package hu.pte.schafferg.cellarManager.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
/**
 * 
 * @author Da3mon
 *	This class prints trace level logs based on the spring-aop.xml config.
 */
public class TraceInterceptor extends CustomizableTraceInterceptor {
	
	protected static Logger logger4j = Logger.getLogger(TraceInterceptor.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7557746551460667554L;

	@Override
	protected void writeToLog(Log logger, String message, Throwable ex) {
		if(ex != null){
			logger4j.trace("ERROR: "+message, ex);
		}else{
			logger4j.trace(message);
		}
	}

	@Override
	protected boolean isInterceptorEnabled(MethodInvocation invocation,
			Log logger) {
		return true;
	}
	
	

	
	

}
