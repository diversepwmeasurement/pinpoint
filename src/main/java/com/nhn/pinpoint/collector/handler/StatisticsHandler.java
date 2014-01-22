package com.nhn.pinpoint.collector.handler;

import com.nhn.pinpoint.collector.dao.ApplicationMapStatisticsCalleeDao;
import com.nhn.pinpoint.collector.dao.ApplicationMapStatisticsCallerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author netspider
 * 
 */
@Service
public class StatisticsHandler {

	@Autowired
	private ApplicationMapStatisticsCalleeDao applicationMapStatisticsCalleeDao;

	@Autowired
	private ApplicationMapStatisticsCallerDao applicationMapStatisticsCallerDao;

    /**
     * tomcat에서 mysql을 호출하였을 경우 아래와 같이 로그가 남는다. <br/>
     * emeroad-app (TOMCAT) -> MySQL_DB_ID (MYSQL)[10.25.149.61:3306] <br/>
     * <br/>
     * callee에서는 <br/>
     * MySQL (MYSQL) <- emeroad-app (TOMCAT)[localhost:8080]
     * @param callerApplicationName
     * @param callerServiceType
     * @param calleeApplicationName
     * @param calleeServiceType
     * @param calleeHost
     * @param elapsed
     * @param isError
     */
	public void updateCaller(String callerApplicationName, short callerServiceType, String calleeApplicationName, short calleeServiceType, String calleeHost, int elapsed, boolean isError) {
		applicationMapStatisticsCallerDao.update(callerApplicationName, callerServiceType, calleeApplicationName, calleeServiceType, calleeHost, elapsed, isError);
	}

    /**
     * tomcat에서 mysql을 호출하였을 경우 아래와 같이 로그가 남는다. <br/>
     * MySQL_DB_ID (MYSQL) <- emeroad-app (TOMCAT)[localhost:8080] <br/>
     * <br/><br/>
     * caller에서는 <br/>
     * emeroad-app (TOMCAT) -> MySQL (MYSQL)[10.25.149.61:3306]
     * @param callerApplicationName
     * @param callerServiceType
     * @param calleeApplicationName
     * @param calleeServiceType
     * @param callerHost
     * @param elapsed
     * @param isError
     */
	public void updateCallee(String calleeApplicationName, short calleeServiceType, String callerApplicationName, short callerServiceType, String callerHost, int elapsed, boolean isError) {
		applicationMapStatisticsCalleeDao.update(calleeApplicationName, calleeServiceType, callerApplicationName, callerServiceType, callerHost, elapsed, isError);
	}
}
