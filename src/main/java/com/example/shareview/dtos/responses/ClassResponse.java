package com.example.shareview.dtos.responses;

import java.util.Date;
import java.util.List;

public record ClassResponse(Long id, Long courseId, List<Long> teachersId, List<Long> studentsId, Date startDate) {
}
