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

	String url;
	@Override
	protected String getPluginName() {

		return "APP_STATUS";
	}

    @Override
    public final Collection gatherMetrics(final ICommandLine cl)
            throws MetricGatheringException {
    	
    	ProgressCalculator p = new ProgressCalculator("localhost", "8088", "v1","cluster/app");
    	
    	String AppId = cl.getOptionValue("AppID");	// (1)
        
    	int completion = 0;
    	
        p.calculateProgress(AppId);
        
        completion = p.getProgress();
        
        String msg = p.getMessage(); 
        
        List res = new ArrayList();
        	
        res.add(new Metric("appstatus", msg, new BigDecimal(completion), new BigDecimal(0), new BigDecimal(100)));  // (4)
      
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
