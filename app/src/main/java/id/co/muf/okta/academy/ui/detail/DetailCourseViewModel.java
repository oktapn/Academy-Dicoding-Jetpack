package id.co.muf.okta.academy.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.data.ModuleEntity;
import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.utils.DataDummy;

public class DetailCourseViewModel extends ViewModel {
    private CourseEntity mCourse;
    private String courseId;
    private AcademyRepository academyRepository;

    public DetailCourseViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    LiveData<CourseEntity> getCourse() {
        return academyRepository.getCourseWithModules(courseId);
    }
    public LiveData<List<ModuleEntity>> getModules() {
        return academyRepository.getAllModulesByCourse(courseId);
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId(){
        return courseId;
    }
}
