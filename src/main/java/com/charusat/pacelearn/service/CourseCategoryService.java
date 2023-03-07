package com.charusat.pacelearn.service;

import com.charusat.pacelearn.domain.AllCategories;
import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.domain.CourseCategory;
import com.charusat.pacelearn.service.dto.CourseCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.charusat.pacelearn.domain.CourseCategory}.
 */
public interface CourseCategoryService {
    /**
     * Save a courseCategory.
     *
     * @param courseCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    CourseCategoryDTO save(CourseCategoryDTO courseCategoryDTO);

    /**
     * Partially updates a courseCategory.
     *
     * @param courseCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CourseCategoryDTO> partialUpdate(CourseCategoryDTO courseCategoryDTO);

    /**
     * Get all the courseCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CourseCategoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" courseCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CourseCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" courseCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<CourseCategory> listParentCategory();

    List<CourseCategory> listByParentId(Long id);

    Map<String, List<Course>> getCoursesBySubCategories();

    Map<String, List<CourseCategory>> getCourseSubCategoriesByParentCategories();

    ArrayList<AllCategories> getCategoriesWithItsSubcategories();

    ResponseEntity<Map<Long, Integer>> getCourseCountBySubCategory(Long parentId);

    ResponseEntity<Map<Long, Integer>> getCourseCountByParentCategory();
}
