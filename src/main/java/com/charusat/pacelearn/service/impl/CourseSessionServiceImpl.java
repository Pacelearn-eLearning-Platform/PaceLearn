package com.charusat.pacelearn.service.impl;

import com.charusat.pacelearn.domain.CourseSession;
import com.charusat.pacelearn.repository.CourseRepository;
import com.charusat.pacelearn.repository.CourseSectionRepository;
import com.charusat.pacelearn.repository.CourseSessionRepository;
import com.charusat.pacelearn.service.CourseSessionService;
import com.charusat.pacelearn.service.dto.CourseSessionDTO;
import com.charusat.pacelearn.service.mapper.CourseSessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CourseSession}.
 */
@Service
@Transactional
public class CourseSessionServiceImpl implements CourseSessionService {

    private final Logger log = LoggerFactory.getLogger(CourseSessionServiceImpl.class);

    private final CourseSessionRepository courseSessionRepository;
    private final CourseSectionRepository courseSectionRepository;
    private final CourseRepository courseRepository;

    private final CourseSessionMapper courseSessionMapper;

    public CourseSessionServiceImpl(
        CourseSessionRepository courseSessionRepository,
        CourseSectionRepository courseSectionRepository,
        CourseRepository courseRepository,
        CourseSessionMapper courseSessionMapper
    ) {
        this.courseSessionRepository = courseSessionRepository;
        this.courseSectionRepository = courseSectionRepository;
        this.courseRepository = courseRepository;
        this.courseSessionMapper = courseSessionMapper;
    }

    @Override
    public CourseSessionDTO save(CourseSessionDTO courseSessionDTO) {
        log.debug("Request to save CourseSession : {}", courseSessionDTO);
        CourseSession courseSession = courseSessionMapper.toEntity(courseSessionDTO);
        courseSession = courseSessionRepository.save(courseSession);
        return courseSessionMapper.toDto(courseSession);
    }

    @Override
    public Optional<CourseSessionDTO> partialUpdate(CourseSessionDTO courseSessionDTO) {
        log.debug("Request to partially update CourseSession : {}", courseSessionDTO);

        return courseSessionRepository
            .findById(courseSessionDTO.getId())
            .map(existingCourseSession -> {
                courseSessionMapper.partialUpdate(existingCourseSession, courseSessionDTO);

                return existingCourseSession;
            })
            .map(courseSessionRepository::save)
            .map(courseSessionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CourseSessions");
        return courseSessionRepository.findAll(pageable).map(courseSessionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CourseSessionDTO> findOne(Long id) {
        log.debug("Request to get CourseSession : {}", id);
        return courseSessionRepository.findById(id).map(courseSessionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CourseSession : {}", id);
        courseSessionRepository.deleteById(id);
    }

    @Override
    public List<CourseSession> findSessionByCourseSection(Long id) {
        return courseSessionRepository.findCourseSessionsByCourseSection(courseSectionRepository.findById(id));
    }

    @Override
    public List<CourseSession> findCourseSessionsByCourseSectionIn(Long id) {
        return courseSessionRepository.findCourseSessionsByCourseSectionIn(
            courseSectionRepository.findCourseSectionsByCourse(courseRepository.findById(id))
        );
    }
}
