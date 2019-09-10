package id.co.muf.okta.academy.ui.academy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.utils.DataDummy;

public class AcademyViewModel extends ViewModel {

    private AcademyRepository academyRepository;

    public AcademyViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    LiveData<List<CourseEntity>> getCourses() {
        return academyRepository.getAllCourses();
    }

}
