package com.graduacionunal.backend.dto;

import java.util.List;

public class SemesterPlanResponse {
    private final int minSemesters;
    private final boolean hasCycle;
    private final List<SemesterResponse> semesters;

    public SemesterPlanResponse(int minSemesters, boolean hasCycle, List<SemesterResponse> semesters) {
        this.minSemesters = minSemesters;
        this.hasCycle = hasCycle;
        this.semesters = semesters;
    }

    public int getMinSemesters() {
        return minSemesters;
    }

    public boolean isHasCycle() {
        return hasCycle;
    }

    public List<SemesterResponse> getSemesters() {
        return semesters;
    }

    public static class SemesterResponse {
        private final int semesterNumber;
        private final List<MateriaPlanDTO> materias;

        public SemesterResponse(int semesterNumber, List<MateriaPlanDTO> materias) {
            this.semesterNumber = semesterNumber;
            this.materias = materias;
        }

        public int getSemesterNumber() {
            return semesterNumber;
        }

        public List<MateriaPlanDTO> getMaterias() {
            return materias;
        }
    }
}
