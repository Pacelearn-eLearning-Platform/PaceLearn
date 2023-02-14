package com.charusat.pacelearn.web.rest;

import com.charusat.pacelearn.domain.Course;
import com.charusat.pacelearn.domain.User;
import com.charusat.pacelearn.repository.CourseRepository;
import com.charusat.pacelearn.service.CourseQueryService;
import com.charusat.pacelearn.service.CourseService;
import com.charusat.pacelearn.service.criteria.CourseCriteria;
import com.charusat.pacelearn.service.dto.CourseDTO;
import com.charusat.pacelearn.service.mapper.CourseMapper;
import com.charusat.pacelearn.web.rest.errors.BadRequestAlertException;
import io.swagger.v3.core.util.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import javax.net.ssl.SSLSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

/**
 * REST controller for managing {@link com.charusat.pacelearn.domain.Course}.
 */
@RestController
@RequestMapping("/api")
public class CourseResource {

    private final Logger log = LoggerFactory.getLogger(CourseResource.class);

    private static final String ENTITY_NAME = "course";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseService courseService;

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final CourseQueryService courseQueryService;

    public CourseResource(
        CourseService courseService,
        CourseRepository courseRepository,
        CourseMapper courseMapper,
        CourseQueryService courseQueryService
    ) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.courseQueryService = courseQueryService;
    }

    /**
     * {@code POST  /courses} : Create a new course.
     *
     * @param courseDTO the courseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseDTO, or with status {@code 400 (Bad Request)} if the course has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/courses")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) throws URISyntaxException {
        log.debug("REST request to save Course : {}", courseDTO);
        if (courseDTO.getId() != null) {
            throw new BadRequestAlertException("A new course cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity
            .created(new URI("/api/courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /courses/:id} : Updates an existing course.
     *
     * @param id        the id of the courseDTO to save.
     * @param courseDTO the courseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseDTO,
     * or with status {@code 400 (Bad Request)} if the courseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/courses/{id}")
    public ResponseEntity<CourseDTO> updateCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CourseDTO courseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Course : {}, {}", id, courseDTO);
        if (courseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CourseDTO result = courseService.save(courseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /courses/:id} : Partial updates given fields of an existing course, field will ignore if it is null
     *
     * @param id        the id of the courseDTO to save.
     * @param courseDTO the courseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseDTO,
     * or with status {@code 400 (Bad Request)} if the courseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the courseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the courseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/courses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CourseDTO> partialUpdateCourse(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CourseDTO courseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Course partially : {}, {}", id, courseDTO);
        if (courseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, courseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!courseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CourseDTO> result = courseService.partialUpdate(courseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /courses} : get all the courses without authorization.
     *
     //     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/open-courses")
    public ResponseEntity<Map<String,List<Course>>> getAllOpenCourses(
            //        CourseCriteria criteria
            //        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        //        log.debug("REST request to get Courses by criteria: {}", criteria);
        //        List<CourseDTO> list = courseService.findAllByCurrentSemester();
        //        List<CourseDTO> list = courseQueryService.findByCriteria(criteria);
        //        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        //        return ResponseEntity.ok().body(list);
        List<Course> list = courseService.findAllOpen();
        List<Course> listTop4 = list.subList(0,4);
        System.out.println("HAHAHAH--> " + listTop4);
        String customJSONResponse = "courses : "+listTop4;
//        return ResponseEntity.ok().body(listTop4);
        HashMap<String,List<Course>> map = new HashMap<>();
        map.put("courses",listTop4);
        return ResponseEntity.ok().body(map);
    }

    /**
     * {@code GET  /courses} : get all the courses.
     *
//     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courses in body.
     */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(
        //        CourseCriteria criteria
        //        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        //        log.debug("REST request to get Courses by criteria: {}", criteria);
        //        List<CourseDTO> list = courseService.findAllByCurrentSemester();
        //        List<CourseDTO> list = courseQueryService.findByCriteria(criteria);
        //        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        //        return ResponseEntity.ok().body(list);
        List<Course> list = courseService.findAll();
        System.out.println("HAHAHAH--> " + list);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /courses/count} : count all the courses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/courses/count")
    public ResponseEntity<Long> countCourses(CourseCriteria criteria) {
        log.debug("REST request to count Courses by criteria: {}", criteria);
        return ResponseEntity.ok().body(courseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /courses/:id} : get the "id" course.
     *
     * @param id the id of the courseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        Optional<CourseDTO> courseDTO = courseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseDTO);
    }

    /**
     * CUSTOM
     * {@code GET  /courses/enroll} :Get Enrolled courses of Logged in User.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */

    @GetMapping("courses/enrolled")
    public ResponseEntity<List<CourseDTO>> enrolledCourses() throws Exception {
        log.debug("REST request to get a page of Courses");
        List<CourseDTO> list = courseService.getEnrolledCourses();
        return ResponseEntity.ok().body(list);
    }

    /**
     * CUSTOM
     * {@code GET  /courses/category/{categoryID}} :Get Courses by Category ID of Logged in User.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */

    @GetMapping("/courses/category/{categoryId}")
    public ResponseEntity<List<CourseDTO>> getCourseByCategory(@PathVariable Long categoryId) throws Exception {
        log.debug("REST request to get Course by categoryId : {}", categoryId);
        List<CourseDTO> list = courseService.getByCategoryId(categoryId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * CUSTOM
     * {@code GET  /courses/{courseId}/student-count} :Get count of enrolled student of Courses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @GetMapping("/courses/{courseId}/student-count")
    public ResponseEntity<Integer> getStudentCountByCourse(@PathVariable Long courseId) {
        log.debug("REST request to get student enrolled count based on courseId : {}", courseId);
        return courseService.getStudentEnrolledCountByCourse(courseId);
    }

    @GetMapping("/courses/get-overview")
    public ResponseEntity<Map<String, String>> getOverview() {
        log.debug("REST request to get courses overview");
        return courseService.getOverview();
    }

    @GetMapping("/course/{courseId}/get-enrolled-users")
    public ResponseEntity<Set<User>> getEnrolledUsersByCourse(@PathVariable Long courseId) {
        log.debug("REST request to get enrolled user list by Course: {}", courseId);
        return courseService.getEnrolledUsersByCourseId(courseId);
    }

    /**
     * CUSTOM
     * {@code POST  /courses/enroll} : Enroll in a particular course.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @PostMapping(value = "/courses/enroll", consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity enrollInCourse(@RequestBody String courseId) {
        log.debug("REST request to enroll in Course : {}", courseId);
        return courseService.enrollInCourse(courseId);
    }

    /**
     * {@code DELETE  /courses/:id} : delete the "id" course.
     *
     * @param id the id of the courseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        courseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
