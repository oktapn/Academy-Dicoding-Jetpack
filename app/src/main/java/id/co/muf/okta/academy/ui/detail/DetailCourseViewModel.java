package id.co.muf.okta.academy.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.co.muf.okta.academy.data.source.local.entity.CourseEntity;
import id.co.muf.okta.academy.data.source.local.entity.CourseWithModule;
import id.co.muf.okta.academy.data.source.local.entity.ModuleEntity;
import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.vo.Resource;

public class DetailCourseViewModel extends ViewModel {
    private CourseEntity mCourse;
    private AcademyRepository academyRepository;

    private MutableLiveData<String> courseId = new MutableLiveData<>();

    public DetailCourseViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public LiveData<Resource<CourseWithModule>> courseModule = Transformations.switchMap(courseId,
            mCourseId -> academyRepository.getCourseWithModules(mCourseId));

    public void setCourseId(String courseId) {
        this.courseId.setValue(courseId);
    }

    public String getCourseId() {
        if (courseId.getValue() == null) return null;
        return courseId.getValue();
    }

    void setBookmark() {
        if (courseModule.getValue() != null) {
            CourseWithModule courseWithModule = courseModule.getValue().data;

            if (courseWithModule != null) {
                CourseEntity courseEntity = courseWithModule.mCourse;

                // Kode di bawah menggunakan tanda seru (!),
                // karena akan mengganti status dari apakah sudah di bookmark atau tidak menjadi apakah sudah siap dibookmark atau tidak
                final boolean newState = !courseEntity.isBookmarked();
                academyRepository.setCourseBookmark(courseEntity, newState);
            }
        }
    }
}
