package my.random.api.scheduler;

import my.random.service.DestinyService;
import my.random.service.ManageService;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CronQuartzWeekly extends QuartzJobBean {
	private static Logger logger = LoggerFactory.getLogger(CronQuartzWeekly.class);

	private ManageService manageService;

	public void setManageService(ManageService manageService) {
		this.manageService = manageService;
	}

	private DestinyService destinyService;

    public void setDestinyService(DestinyService destinyService) {
        this.destinyService = destinyService;
    }

	@Override
	protected void executeInternal(JobExecutionContext ex) throws JobExecutionException {

		logger.info("Destiny Manage Service Weekly Scheduler...");

		try {
			logger.info("Crawler Start");
			manageService.crawling();
			logger.info("Crawler Finish");
		} catch (Exception e) {
			logger.error("error: " + e);
		}

	}

}
