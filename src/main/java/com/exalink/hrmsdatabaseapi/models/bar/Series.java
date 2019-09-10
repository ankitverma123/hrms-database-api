
package com.exalink.hrmsdatabaseapi.models.bar;

import java.util.List;

import lombok.Data;

/**
 * @author ankitkverma
 *
 */
@Data
public class Series {

    public List<Integer> data = null;
    public String type="bar";

}
