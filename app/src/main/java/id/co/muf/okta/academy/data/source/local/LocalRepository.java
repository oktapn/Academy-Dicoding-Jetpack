package id.co.muf.okta.academy.data.source.local;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import java.util.List;

import id.co.muf.okta.academy.data.source.local.entity.CourseEntity;
import id.co.muf.okta.academy.data.source.local.entity.CourseWithModule;
import id.co.muf.okta.academy.data.source.local.entity.ModuleEntity;
import id.co.muf.okta.academy.data.source.local.room.AcademyDao;

public class LocalRepository {
    private final AcademyDao mAcademyDao;

    private LocalRepository(AcademyDao mAcademyDao) {
        this.mAcademyDao = mAcademyDao;
    }

    private static LocalRepository INSTANCE;

    public static LocalRepository getInstance(AcademyDao academyDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalRepository(academyDao);
        }
        return INSTANCE;
    }

    public LiveData<List<CourseEntity>> getAllCourses() {
        return mAcademyDao.getCourses();
    }

    public LiveData<List<CourseEntity>> getBookmarkedCourses() {
        return mAcademyDao.getBookmarkedCourse();
    }

    public LiveData<CourseWithModule> getCourseWithModules(final String courseId) {
        return mAcademyDao.getCourseWithModuleById(courseId);
    }

    public LiveData<List<ModuleEntity>> getAllModulesByCourse(String courseId) {
        return mAcademyDao.getModulesByCourseId(courseId);
    }

    public void insertCourses(List<CourseEntity> courses) {
        mAcademyDao.insertCourses(courses);
    }

    public void insertModules(List<ModuleEntity> modules) {
        mAcademyDao.insertModules(modules);
    }

    public void setCourseBookmark(CourseEntity course, boolean newState) {
        course.setBookmarked(newState);
        mAcademyDao.updateCourse(course);
    }

    public LiveData<ModuleEntity> getModuleWithContent(String moduleId){
        return mAcademyDao.getModuleById(moduleId);
    }

    public void updateContent(String content,String moduleId){
        mAcademyDao.updateModuleByContent(content,moduleId);
    }

    public void setReadModule(final ModuleEntity module) {
        module.setRead(true);
        mAcademyDao.updateModule(module);
    }

    public DataSource.Factory<Integer, CourseEntity> getBookmarkedCoursesPaged() {
        return mAcademyDao.getBookmarkedCourseAsPaged();
    }
}
