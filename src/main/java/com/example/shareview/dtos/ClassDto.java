package com.example.shareview.dtos;

import java.util.Date;
import java.util.List;

public record ClassDto(Long id, CourseDto course, List<UserDto> teachers, List<UserDto> students, Date startDate) {
}
