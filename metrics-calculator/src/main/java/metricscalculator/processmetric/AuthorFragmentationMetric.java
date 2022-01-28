package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import gitapi.CodeSampleFragmentationApi;

/**
 * Name: Author Fragmentation
 * Description: A metric describing how fragmented the work on single source element is across authors.
 * Reference: Linus W. Dietz, Robin Lichtenthälery, Adam Tornhillz and Simon Harrer.
 * 2019. Code Process Metrics in University Programming Education. Software
 * Engineering
 */
public class AuthorFragmentationMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        return CodeSampleFragmentationApi.getFragmentationAcrossDevelopers(codeSample);
    }

    @Override
    public MetricName getName() {
        return MetricName.AUTHOR_FRAGMENTATION;
    }

}
