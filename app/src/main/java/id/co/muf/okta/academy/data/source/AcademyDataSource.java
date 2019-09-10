package id.co.muf.okta.academy.data.source;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.data.ModuleEntity;

public interface AcademyDataSource {

    LiveData<List<CourseEntity>> getAllCourses();

    LiveData<CourseEntity> getCourseWithModules(String courseId);

    LiveData<List<ModuleEntity>> getAllModulesByCourse(String courseId);

    LiveData<List<CourseEntity>> getBookmarkedCourses();

    LiveData<ModuleEntity> getContent(String courseId, String moduleId);
}
