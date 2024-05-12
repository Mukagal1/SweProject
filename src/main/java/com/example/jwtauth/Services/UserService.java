package com.example.jwtauth.Services;

import com.example.jwtauth.Repositories.ClassRep;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final ClassRep classRep;

    public UserService(ClassRep classRep) {
        this.classRep = classRep;
    }

    public int getClassesWithHighAbsentStatus(Long userId) {
        int count = 0;
        Map<String, Integer> totalAttendances = new HashMap<>();
        Map<String, Integer> absentAttendances = new HashMap<>();

        List<Object[]> totalAttendancesResult = classRep.getTotalAttendances(userId);
        for (Object[] result : totalAttendancesResult) {
            String classId = (String) result[0];
            Integer total = ((Number) result[1]).intValue();
            totalAttendances.put(classId, total);
        }

        List<Object[]> absentAttendancesResult = classRep.getAbsentAttendances(userId);
        for (Object[] result : absentAttendancesResult) {
            String classId = (String) result[0];
            Integer absent = ((Number) result[1]).intValue();
            absentAttendances.put(classId, absent);
        }

        for (String classId : totalAttendances.keySet()) {
            if ((double) absentAttendances.getOrDefault(classId, 0) / totalAttendances.get(classId) > 0.3) {
                count++;
            }
        }

        return count;
    }
}