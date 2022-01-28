package domain.code;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Metric {

    private final MetricName name;
    private final Number value;
    private final MetricType type;

    public boolean hasTheSameName(MetricName name) {
        return this.name == name;
    }

}
