package com.colsubsidio.salud.portal.dao.interfaces;

import com.colsubsidio.salud.portal.models.Schedule;
import java.util.List;

/**
 *
 * @author Usuario
 */
public interface IScheduleDAO {

    public void insertSchedule(Schedule schedule);
    public void updateSchedule(Schedule schedule);
    public List<Schedule> selectSchedule();
}
