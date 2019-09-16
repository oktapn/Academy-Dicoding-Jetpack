package id.co.muf.okta.academy.data.source;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.List;

import id.co.muf.okta.academy.data.source.local.entity.CourseEntity;
import id.co.muf.okta.academy.data.source.local.entity.CourseWithModule;
import id.co.muf.okta.academy.data.source.local.entity.ModuleEntity;
import id.co.muf.okta.academy.vo.Resource;

public interface AcademyDataSource {
    LiveData<Resource<List<CourseEntity>>> getAllCourses();

    LiveData<Resource<CourseWithModule>> getCourseWithModules(String courseId);

    LiveData<Resource<List<ModuleEntity>>> getAllModulesByCourse(String courseId);

    LiveData<Resource<List<CourseEntity>>> getBookmarkedCourses();

    LiveData<Resource<ModuleEntity>> getContent(String moduleId);

    void setCourseBookmark(CourseEntity course, boolean state);

    void setReadModule(ModuleEntity module);

    LiveData<Resource<PagedList<CourseEntity>>> getBookmarkedCoursesPaged();

}
