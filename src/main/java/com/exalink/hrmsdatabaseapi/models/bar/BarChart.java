
package com.exalink.hrmsdatabaseapi.models.bar;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ankitkverma
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarChart {

    public XAxis xAxis;
    public YAxis yAxis;
    public List<Series> series = null;

}
