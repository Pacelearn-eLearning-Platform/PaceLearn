package com.charusat.pacelearn.service;

import com.charusat.pacelearn.service.dto.CourseEnrollmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.charusat.pacelearn.domain.CourseEnrollment}.
 */
public interface CourseEnrollmentService {
    /**
     * Save a courseEnrollment.
     *
     * @param courseEnrollmentDTO the entity to save.
     * @return the persisted entity.
     */
    CourseEnrollmentDTO save(CourseEnrollmentDTO courseEnrollmentDTO);

    /**
     * Partially updates a courseEnrollment.
     *
     * @param courseEnrollmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseEnrollmentDTO> partialUpdate(CourseEnrollmentDTO courseEnrollmentDTO);

    /**
     * Get all the courseEnrollments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseEnrollmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseEnrollment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseEnrollmentDTO> findOne(Long id);

    /**
     * Delete the "id" courseEnrollment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
