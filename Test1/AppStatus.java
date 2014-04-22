package jnrpe.hadoop.yarn.appstatus;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import it.jnrpe.ICommandLine;
import it.jnrpe.plugins.Metric;
import it.jnrpe.plugins.MetricGatheringException;
import it.jnrpe.plugins.PluginBase;
import it.jnrpe.utils.BadThresholdException;
import it.jnrpe.utils.thresholds.ThresholdsEvaluatorBuilder;

public class AppStatus extends PluginBase {

	@Override
	protected String getPluginName() {

		return "APP_STATUS";
	}
	
    @Override
    public final Collection gatherMetrics(final ICommandLine cl)
            throws MetricGatheringException {
    	
    	int completion = 0;
    	Status s;
    	List res = new ArrayList();
    	
        String appIdList = cl.getOptionValue("applist");	// (1)
        
//        String[] appIds = appIdList.split(" ");
        
        List<String> list = new ArrayList<String>(Arrays.asList(appIdList.split(" ")));  

        for(String id: list) {
        	
        	System.out.println("Application ID: " + id);

        	s = new Status(id);
        	
//        	TODO
//        	GET APP ID STATUS FROM YARN REST API
//        	
//        	
        	
           s.setProgress(50);
           s.setState(" in progess");

        	completion = s.getProgress();

            res.add(new Metric("appstatus", s.toString(), new BigDecimal(completion), new BigDecimal(0), new BigDecimal(100)));  // (4)



        	
        }

        return res;
    }

    @Override
    public void configureThresholdEvaluatorBuilder(
    		ThresholdsEvaluatorBuilder thrb, ICommandLine cl)
    		throws BadThresholdException {
//    	thrb.withLegacyThreshold("appstatus", null, cl.getOptionValue("warning"), cl.getOptionValue("critical"));
    	thrb.withLegacyThreshold("appstatus", null, null, null);
    }

}
