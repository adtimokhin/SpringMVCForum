package com.adtimokhin.services.report;

import com.adtimokhin.models.report.Cause;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adtimokhin
 * 14.06.2021
 **/

@Service
public interface CauseService {

    void addCause(String causeText);

    List<Cause> getAllCauses();

    Cause getOrAddCause(String causeText);

    List<Cause> getAllBasicCauses();

    void makeCauseBasic(Cause cause);

    void makeCauseBasic(long causeId);
}
