package com.charusat.pacelearn.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.charusat.pacelearn.domain.CourseCategory} entity.
 */
public class CourseCategoryDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 10, max = 42)
    private String courseCategoryTitle;

    @NotNull
    @Size(min = 10, max = 42)
    private String logo;

    @NotNull
    private Boolean isParent;

    @NotNull
    private Integer parentId;

    @Size(min = 10, max = 400)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCategoryTitle() {
        return courseCategoryTitle;
    }

    public void setCourseCategoryTitle(String courseCategoryTitle) {
        this.courseCategoryTitle = courseCategoryTitle;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseCategoryDTO)) {
            return false;
        }

        CourseCategoryDTO courseCategoryDTO = (CourseCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseCategoryDTO{" +
            "id=" + getId() +
            ", courseCategoryTitle='" + getCourseCategoryTitle() + "'" +
            ", logo='" + getLogo() + "'" +
            ", isParent='" + getIsParent() + "'" +
            ", parentId=" + getParentId() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
